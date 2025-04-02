package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.item.custom.MagnifyingGlassItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MagnifyingGlassStandBlock extends HorizontalDirectionalBlock {

    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0, 0.0, 3, 16.0, 9.0, 12.0);
    public static final MapCodec<MagnifyingGlassStandBlock> CODEC = simpleCodec(MagnifyingGlassStandBlock::new);
    public static final BooleanProperty MAGNIFYNG_GLASS_INSIDE = BooleanProperty.create("magnifying_block_active");

    private int conversion;
    private final int conversionThreshold;

    public MagnifyingGlassStandBlock(Properties properties) {
        super(properties);
        this.conversion = 0;
        this.conversionThreshold = 5;
        this.registerDefaultState(this.defaultBlockState().setValue(MAGNIFYNG_GLASS_INSIDE, false).setValue(FACING, Direction.NORTH));
    }

    public MagnifyingGlassStandBlock(int conversionThreshold, Properties properties) {
        super(properties);
        this.conversion = 0;
        this.conversionThreshold = conversionThreshold;
        this.registerDefaultState(this.defaultBlockState().setValue(MAGNIFYNG_GLASS_INSIDE, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOTTOM_AABB;
    }

    @Override
    public MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection()).setValue(MAGNIFYNG_GLASS_INSIDE, false);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (state.getValue(MAGNIFYNG_GLASS_INSIDE) && level instanceof ServerLevel serverLevel){
            serverLevel.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.MAGNIFYING_GLASS.get())));
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(MAGNIFYNG_GLASS_INSIDE);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(MAGNIFYNG_GLASS_INSIDE);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        int skyLightLevel = level.getBrightness(LightLayer.SKY, pos);
        if (conversion <= conversionThreshold
                && MagnifyingGlassItem.MAGNIFYING_CONVERTABLES.containsKey(belowState)
                && level.isDay()
                && skyLightLevel > 5
        ){
            conversion++;
            System.out.println(conversion);
            showSeverBurningParticles(level, belowPos);
            if (conversion == conversionThreshold){
                level.playSound(null, pos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS);
                level.setBlockAndUpdate(belowPos, MagnifyingGlassItem.MAGNIFYING_CONVERTABLES.get(belowState));
                conversion = 0;
            }
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer){
            System.out.println("Clicked with " + stack + " in hand: " + hand);
            boolean magnifyingGlassInside = state.getValue(MAGNIFYNG_GLASS_INSIDE);
            if (magnifyingGlassInside && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()){
                serverPlayer.getInventory().add(new ItemStack(ModItems.MAGNIFYING_GLASS.get()));
                serverLevel.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS);
                serverLevel.setBlockAndUpdate(pos, state.setValue(MAGNIFYNG_GLASS_INSIDE, false));
                return InteractionResult.SUCCESS_SERVER;
            } else if (stack.getItem() instanceof MagnifyingGlassItem && !state.getValue(MAGNIFYNG_GLASS_INSIDE)){
                stack.consume(1, serverPlayer);
                serverLevel.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS);
                serverLevel.setBlockAndUpdate(pos, state.setValue(MAGNIFYNG_GLASS_INSIDE, true));
                return InteractionResult.CONSUME;
            }
            return InteractionResult.FAIL;
        } else {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
    }

    public static void showSeverBurningParticles(ServerLevel level, BlockPos pos){
        RandomSource randomsource = level.getRandom();
            level
                .sendParticles(
                        ParticleTypes.FLAME,
                        (double)pos.getX() + randomsource.nextDouble(),
                        (double)pos.getY()+ 1,
                        (double)pos.getZ() + randomsource.nextDouble(),
                        10,
                        0.0,
                        0.01,
                        0.0,
                        0.0
                );
    }
}
