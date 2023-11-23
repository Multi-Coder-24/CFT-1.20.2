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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class barrageBlockEntity extends BlockEntity
{
    public List<CompoundTag> rockets;
    public byte mode;
    public boolean random;
    public int delay;
    
    public barrageBlockEntity(BlockPos pos, BlockState state)
    {
        super(blockEntityInit.BARRAGE.get(), pos, state);
        rockets = new ArrayList<>();
        mode = (byte) 0;
        random = false;
        delay = 0;
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
        else
        {
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
            ItemStack Firework = new ItemStack(Items.FIREWORK_ROCKET);
            if(random)
            {
                //  Randomized Mode
                int UB = rockets.size();
                java.util.Random R = new Random();
                int Index = R.nextInt(UB);
                Firework.setTag(rockets.get(Index));
                rockets.remove(Index);
            }
            else
            {
                //  Sequential Mode
                Firework.setTag(rockets.get(0));
                rockets.remove(0);
            }
            FireworkRocketEntity Rocket = new FireworkRocketEntity(level,null,this.worldPosition.getX() + 0.5,this.worldPosition.getY() + 1,this.worldPosition.getZ() + 0.5,Firework);
            if(mode == (byte) 1)
            {
                // Timed Firing
                int LT = Rocket.lifetime + delay;
                level.scheduleTick(worldPosition, blockInit.BARRAGE.get(),LT, TickPriority.HIGH);
            }
            level.addFreshEntity(Rocket);
            return;
        }
        System.out.println("Rockets Empty");
        setChanged();
    }
}
