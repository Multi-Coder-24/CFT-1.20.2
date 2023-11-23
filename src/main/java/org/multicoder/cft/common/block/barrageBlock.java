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
import org.multicoder.cft.common.blockentity.barrageBlockEntity;
import org.multicoder.cft.common.utility.optionsUtils;

@SuppressWarnings("all")
public class barrageBlock extends BaseEntityBlock
{

    private VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.375, 0.75), BooleanOp.OR);
        return shape;
    }

    public barrageBlock()
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
        return new barrageBlockEntity(pos,state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {return RenderShape.MODEL;}

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block p_60512_, BlockPos neighbor, boolean p_60514_)
    {
        if(!level.isClientSide())
        {
            barrageBlockEntity Entity = (barrageBlockEntity) level.getBlockEntity(pos);
            if(level.hasNeighborSignal(neighbor))
            {
                int DSig = level.getSignal(neighbor,Direction.DOWN);
                if(DSig > 0 && DSig == 15)
                {
                    if(Entity.mode == (byte) 2){Entity.enabled = !Entity.enabled;}
                    Entity.Pulse();
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource)
    {
        barrageBlockEntity Entity = (barrageBlockEntity) level.getBlockEntity(pos);
        Entity.Pulse();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide())
        {
            barrageBlockEntity blockEntity = (barrageBlockEntity) level.getBlockEntity(pos);
            ItemStack mainHand = player.getItemInHand(hand);
            if(mainHand.getItem().equals(Items.FIREWORK_ROCKET))
            {
                blockEntity.AppendRocket(mainHand.getOrCreateTag());
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.rocket_added"));
            }
            if(mainHand.getItem().equals(Items.REDSTONE_TORCH)){
                blockEntity.ModeSwitch();
                byte B = blockEntity.mode;
                String M = optionsUtils.optionToString(B);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.mode_change",M));
            }
            if(mainHand.getItem().equals(Items.LEVER)){
                blockEntity.ChangeRandom();
                boolean B = blockEntity.random;
                String M = optionsUtils.optionToString(B);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.random",M));
            }
            if(mainHand.getItem().equals(Items.COBBLESTONE))
            {
                blockEntity.IncreaseDelay();
                String M = String.valueOf(blockEntity.delay);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.delay",M));
            }
            if(mainHand.getItem().equals(Items.DIRT))
            {
                blockEntity.DecreaseDelay();
                String M = String.valueOf(blockEntity.delay);
                player.sendSystemMessage(Component.translatable("interaction.cft.barrage.delay",M));
            }
        }
        return InteractionResult.CONSUME;
    }
}
