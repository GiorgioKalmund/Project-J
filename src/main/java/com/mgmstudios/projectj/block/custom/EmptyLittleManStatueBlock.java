package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import com.mgmstudios.projectj.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static com.mgmstudios.projectj.block.custom.LittleManStatueBlock.SHAPE_BASE;

public class EmptyLittleManStatueBlock extends HorizontalDirectionalBlock {

    public static final BooleanProperty LITTLE_MAN_WILL_RESET = BooleanProperty.create("little_man_will_reset");
    protected static final MapCodec<EmptyLittleManStatueBlock> CODEC = simpleCodec(EmptyLittleManStatueBlock::new);
    public EmptyLittleManStatueBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LITTLE_MAN_WILL_RESET, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BASE ;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LITTLE_MAN_WILL_RESET, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LITTLE_MAN_WILL_RESET);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LittleManEntity littleMan && littleManWillReset(state)){
            littleMan.remove(Entity.RemovalReason.DISCARDED);
            //level.playSound(null, littleMan.blockPosition(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS);
            level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS);
            level.setBlockAndUpdate(pos, ModBlocks.LITTLE_MAN_STATUE_BLOCK.get().defaultBlockState().setValue(FACING, Direction.fromYRot(littleMan.getYRot())));
            level.levelEvent(2009, pos, 0);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        if (stack.is(ModItems.LITTLE_MAN_VOODOO.get()) && player instanceof ServerPlayer serverPlayer){
            level.setBlockAndUpdate(pos, ModBlocks.LITTLE_MAN_STATUE_BLOCK.get().defaultBlockState().setValue(FACING, player.getDirection().getOpposite()));
            level.levelEvent(2009, pos, 0);
            serverPlayer.playNotifySound(SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1f, 1.5f);

            return InteractionResult.SUCCESS_SERVER;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!littleManWillReset(state)) {
            setLittleManWillReset(level, state, pos, true);
        }
        super.tick(state, level, pos, random);
    }

    private boolean littleManWillReset(BlockState state){
        return state.getValue(LITTLE_MAN_WILL_RESET);
    }


    private void setLittleManWillReset(Level level, BlockState state, BlockPos pos, boolean value){
        level.setBlockAndUpdate(pos, state.setValue(LITTLE_MAN_WILL_RESET, value));
    }

}
