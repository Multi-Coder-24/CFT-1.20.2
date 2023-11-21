package org.multicoder.cft.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.multicoder.cft.common.blockentity.BarrageBlockEntity;
import org.multicoder.cft.common.utility.OptionsUtils;

@SuppressWarnings("all")
public class BarrageBlock extends BaseEntityBlock
{

    private VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.375, 0.75), BooleanOp.OR);
        return shape;
    }

    public BarrageBlock()
    {
        super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return makeShape();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return makeShape();
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new BarrageBlockEntity(pos,state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {return RenderShape.MODEL;}

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block p_60512_, BlockPos neighbor, boolean p_60514_)
    {
        if(!level.isClientSide())
        {
            BarrageBlockEntity Entity = (BarrageBlockEntity) level.getBlockEntity(pos);
            if(level.hasNeighborSignal(neighbor))
            {
                int DSig = level.getSignal(neighbor,Direction.DOWN);
                if(DSig > 0 && DSig == 15)
                {
                    System.out.println("Redstone Pulse");
                    Entity.Pulse();
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource)
    {
        System.out.println("Tick");
        BarrageBlockEntity Entity = (BarrageBlockEntity) level.getBlockEntity(pos);
        Entity.Pulse();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide())
        {
            BarrageBlockEntity Entity = (BarrageBlockEntity) level.getBlockEntity(pos);
            ItemStack Held = player.getItemInHand(hand);
            if(Held.getItem().equals(Items.FIREWORK_ROCKET))
            {
                Entity.AppendRocket(Held.getOrCreateTag());
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.rocket_added"));
            }
            if(Held.getItem().equals(Items.REDSTONE_TORCH)){
                Entity.ModeSwitch();
                byte B = Entity.Mode;
                String M = OptionsUtils.OptionToString(B);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.mode_change",M));
            }
            if(Held.getItem().equals(Items.LEVER)){
                Entity.ChangeRandom();
                boolean B = Entity.Random;
                String M = OptionsUtils.OptionToString(B);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.random",M));
            }
            if(Held.getItem().equals(Items.COBBLESTONE))
            {
                Entity.IncreaseDelay();
                String M = String.valueOf(Entity.Delay);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.delay",M));
            }
            if(Held.getItem().equals(Items.DIRT))
            {
                Entity.DecreaseDelay();
                String M = String.valueOf(Entity.Delay);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.delay",M));
            }
        }
        return InteractionResult.CONSUME;
    }
}
