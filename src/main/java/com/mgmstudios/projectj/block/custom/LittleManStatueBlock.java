package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import com.mgmstudios.projectj.entity.goals.NearestAttackableTargetGoalWithExceptions;
import com.mgmstudios.projectj.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LittleManStatueBlock extends HorizontalDirectionalBlock {

    private double range = 20;

    protected static final MapCodec<LittleManStatueBlock> CODEC = simpleCodec(LittleManStatueBlock::new);
    public static final BooleanProperty SUMMONING = BooleanProperty.create("summoning");

    public static final VoxelShape SHAPE_BASE = Block.box(0,0,0, 16, 5, 16);
    public static final VoxelShape SHAPE_BODY = Block.box(4,0,4, 13, 16, 13);
    public static final VoxelShape SHAPE = Shapes.or(SHAPE_BASE, SHAPE_BODY);
    public LittleManStatueBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SUMMONING, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(SUMMONING, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, SUMMONING);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        if (level instanceof ServerLevel && stack.is(ModItems.JADE.get()) && !summoning(state)){
            setSummoning(level, state, pos, true);
            level.scheduleTick(pos, this, 60);
            stack.shrink(1);
            player.playNotifySound(SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1f, 2f);
            return InteractionResult.SUCCESS_SERVER;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (summoning(state)){
            setSummoning(level, state, pos, false);

            LittleManEntity littleMan = new LittleManEntity(ModEntities.LITTLE_MAN_ENTITY.get(), level);
            Vec3 spawnPos = pos.getCenter().add(new Vec3(0, 0, 0));
            littleMan.lookAt(EntityAnchorArgument.Anchor.EYES, state.getValue(FACING).getUnitVec3());
            littleMan.setPos(spawnPos);

            level.addFreshEntity(littleMan);

            level.playSound(null, BlockPos.containing(spawnPos), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS);

            level.setBlockAndUpdate(pos, ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get().defaultBlockState());
        } else {
            List<Zombie> allEntities = level.getEntitiesOfClass(Zombie.class, new AABB(pos).inflate(range));

            for (Zombie zombie : allEntities) {

                NearestAttackableTargetGoal<Player> goal = new NearestAttackableTargetGoal<>(zombie, Player.class, true);

                goal.stop();

                Vec3 vec3 = DefaultRandomPos.getPosAway(zombie, 16, 7, pos.getCenter());
                if(vec3 != null){
                    zombie.getNavigation().moveTo(vec3.x,vec3.y, vec3.z, 1f);
                }
            }
        }
        super.tick(state, level, pos, random);
        level.scheduleTick(pos, this, 1);
    }

    private boolean summoning(BlockState state){
        return state.getValue(SUMMONING);
    }


    private void setSummoning(Level level, BlockState state, BlockPos pos, boolean value){
        level.setBlockAndUpdate(pos, state.setValue(SUMMONING, value));
    }


    private void resetState(Level level, BlockState state, BlockPos pos){
        level.setBlockAndUpdate(pos, state.setValue(SUMMONING, false));
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        if(level instanceof ServerLevel serverLevel)
            serverLevel.scheduleTick(pos, this, 1);
        super.onBlockStateChange(level, pos, oldState, newState);
    }
}
