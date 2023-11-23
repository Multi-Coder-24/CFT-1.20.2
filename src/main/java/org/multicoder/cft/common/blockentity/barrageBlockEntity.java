package org.multicoder.cft.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;
import org.multicoder.cft.common.init.blockEntityInit;
import org.multicoder.cft.common.init.blockInit;
import org.multicoder.cft.common.utility.randomFireworkMaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class barrageBlockEntity extends BlockEntity
{
    public List<CompoundTag> rockets;
    public byte mode;
    public boolean random;
    public boolean enabled;
    public int delay;
    
    public barrageBlockEntity(BlockPos pos, BlockState state)
    {
        super(blockEntityInit.BARRAGE.get(), pos, state);
        rockets = new ArrayList<>();
        mode = (byte) 0;
        random = false;
        delay = 0;
        enabled = false;
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        ListTag LT = new ListTag();
        rockets.forEach(compound ->
        {
            LT.add(compound);
        });
        tag.put("Rockets",LT);
        tag.putByte("Mode", mode);
        tag.putBoolean("Random", random);
        tag.putInt("Delay", delay);
        tag.putBoolean("Enabled",enabled);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        rockets = new ArrayList<>();
        ListTag LT = tag.getList("Rockets", Tag.TAG_COMPOUND);
        LT.forEach(compound ->{
            CompoundTag T = (CompoundTag) compound;
            rockets.add(T);
        });
        mode = tag.getByte("Mode");
        random = tag.getBoolean("Random");
        delay = tag.getInt("Delay");
        enabled = tag.getBoolean("Enabled");
    }
    public void IncreaseDelay()
    {
        delay += 10;
        setChanged();
    }
    public void DecreaseDelay()
    {
        delay -= 10;
        setChanged();
    }
    public void ModeSwitch()
    {
        if(mode == (byte) 0)
        {
            mode = (byte) 1;
        }
        else if(mode == 1)
        {
            mode = (byte) 2;
        }
        else{
            mode = (byte) 0;
        }
        setChanged();
    }


    public void ChangeRandom()
    {
        random = !random;
        setChanged();
    }
    public void AppendRocket(CompoundTag Rocket)
    {
        rockets.add(Rocket);
    }

    public void Pulse()
    {
        if(!rockets.isEmpty())
        {
            //  Rockets list not empty
            ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
            if(random)
            {
                //  Randomized Mode
                int bound = rockets.size();
                java.util.Random random = new Random();
                int selectedIndex = random.nextInt(bound);
                firework.setTag(rockets.get(selectedIndex));
                rockets.remove(selectedIndex);
            }
            else
            {
                //  Sequential Mode
                firework.setTag(rockets.get(0));
                rockets.remove(0);
            }
            FireworkRocketEntity rocketEntity = new FireworkRocketEntity(level,null,this.worldPosition.getX() + 0.5,this.worldPosition.getY() + 1,this.worldPosition.getZ() + 0.5,firework);
            if(mode == (byte) 1)
            {
                // Timed Firing
                int scheduleDelay = rocketEntity.lifetime + delay;
                level.scheduleTick(worldPosition, blockInit.BARRAGE.get(),scheduleDelay, TickPriority.HIGH);
            }
            level.addFreshEntity(rocketEntity);
            return;
        }
        else
        {
            if(mode == (byte) 2 && enabled)
            {
                //  Random Rockets
                ItemStack rocket = randomFireworkMaker.createRandomFirework();
                FireworkRocketEntity rocketEntity = new FireworkRocketEntity(level,null,this.worldPosition.getX() + 0.5,this.worldPosition.getY() + 1,this.worldPosition.getZ() + 0.5,rocket);
                if(delay == 0){delay = 20;}
                int scheduleDelay = rocketEntity.lifetime + delay;
                level.scheduleTick(worldPosition,blockInit.BARRAGE.get(),scheduleDelay,TickPriority.HIGH);
                level.addFreshEntity(rocketEntity);
                return;
            }
        }
        setChanged();
    }
}
