package org.multicoder.cft.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.Nullable;
import org.multicoder.cft.common.init.BlockEntityInit;
import org.multicoder.cft.common.init.blockinit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarrageBlockEntity extends BlockEntity
{
    public List<CompoundTag> ROCKETS;
    public byte Mode;
    public boolean Random;
    public int Delay;
    public BarrageBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntityInit.BARRAGE.get(), pos, state);
        ROCKETS = new ArrayList<>();
        Mode = (byte) 0;
        Random = false;
        Delay = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        ListTag LT = new ListTag();
        ROCKETS.forEach(compound ->
        {
            LT.add(compound);
        });
        tag.put("Rockets",LT);
        tag.putByte("Mode",Mode);
        tag.putBoolean("Random",Random);
        tag.putInt("Delay",Delay);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        ROCKETS = new ArrayList<>();
        ListTag LT = tag.getList("Rockets", Tag.TAG_COMPOUND);
        LT.forEach(compound ->{
            CompoundTag T = (CompoundTag) compound;
            ROCKETS.add(T);
        });
        Mode = tag.getByte("Mode");
        Random = tag.getBoolean("Random");
        Delay = tag.getInt("Delay");
    }
    public void IncreaseDelay()
    {
        Delay += 10;
        setChanged();
    }
    public void DecreaseDelay()
    {
        Delay -= 10;
        setChanged();
    }
    public void ModeSwitch()
    {
        if(Mode == (byte) 0)
        {
            Mode = (byte) 1;
        }
        else
        {
            Mode = (byte) 0;
        }
        setChanged();
    }


    public void ChangeRandom()
    {
        Random = !Random;
        setChanged();
    }
    public void AppendRocket(CompoundTag Rocket)
    {
        ROCKETS.add(Rocket);
    }

    public void Pulse()
    {
        System.out.println("Pulse Triggered");
        if(!ROCKETS.isEmpty())
        {
            System.out.println("Has Rockets");
            if(Mode == (byte) 1)
            {
                System.out.println("Timed Mode");
                ItemStack Firework = new ItemStack(Items.FIREWORK_ROCKET);
                if(Random)
                {
                    System.out.println("Random Sequence");
                    int UB = ROCKETS.size();
                    java.util.Random R = new Random();
                    int Index = R.nextInt(UB);
                    Firework.setTag(ROCKETS.get(Index));
                    ROCKETS.remove(Index);
                }
                else
                {
                    System.out.println("Sequential");
                    Firework.setTag(ROCKETS.get(0));
                    ROCKETS.remove(0);
                }
                FireworkRocketEntity Rocket = new FireworkRocketEntity(level,null,this.worldPosition.getX() + 0.5,this.worldPosition.getY() + 1,this.worldPosition.getZ() + 0.5,Firework);
                int LT = Rocket.lifetime + Delay;
                level.scheduleTick(worldPosition,blockinit.BARRAGE.get(),LT, TickPriority.HIGH);
                level.addFreshEntity(Rocket);
            }
            if(Mode == (byte) 0)
            {
                System.out.println("Pulse Mode");
                ItemStack Firework = new ItemStack(Items.FIREWORK_ROCKET);
                if(Random)
                {
                    System.out.println("Random Sequence");
                    int UB = ROCKETS.size();
                    java.util.Random R = new Random();
                    int Index = R.nextInt(UB);
                    Firework.setTag(ROCKETS.get(Index));
                    ROCKETS.remove(Index);
                }
                else
                {
                    System.out.println("Sequential");
                    Firework.setTag(ROCKETS.get(0));
                    ROCKETS.remove(0);
                }
                FireworkRocketEntity Rocket = new FireworkRocketEntity(level,null,this.worldPosition.getX() + 0.5,this.worldPosition.getY() + 1,this.worldPosition.getZ() + 0.5,Firework);
                level.addFreshEntity(Rocket);
            }
            return;
        }
        System.out.println("Rockets Empty");
        setChanged();
    }
}
