package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.util.ModTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.http.cookie.SM;

import static com.mgmstudios.projectj.block.custom.AdobeFurnaceBlock.TIER1;

public class AdobeChimneyBlock extends Block {

    public static final MapCodec<AdobeChimneyBlock> CODEC = simpleCodec(AdobeChimneyBlock::new);

    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 14, 12);

    public static BooleanProperty SMOKING = BooleanProperty.create("chimney_smoking");

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public AdobeChimneyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SMOKING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SMOKING);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!(oldState.getBlock() instanceof AdobeChimneyBlock))
            return;

        if (level instanceof ServerLevel serverLevel){
            prepareSpawnCondition(state, serverLevel, pos);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level instanceof ServerLevel serverLevel){
            BlockPos belowPos = pos.below();
            BlockState belowBlockState = level.getBlockState(belowPos);
            if (belowBlockState.is(ModBlocks.ADOBE_FURNACE.get())){
                serverLevel.setBlock(belowPos, belowBlockState.setValue(TIER1, false), 3);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public void prepareSpawnCondition(BlockState state, ServerLevel level, BlockPos pos){
        BlockPos belowPos = pos.below();
        BlockState belowBlockState = level.getBlockState(belowPos);

        if (belowBlockState.is(ModBlocks.ADOBE_FURNACE.get())){
            System.out.println("Below is furnace");
            // Event 2009 is the event that normally spawns particles when a wet sponge is converted to a regular sponge in the nether
            level.levelEvent(2009, belowPos, 0);
            level.playSound(null, belowPos, SoundEvents.DECORATED_POT_PLACE, SoundSource.BLOCKS, 1f, 1f);
            level.setBlock(belowPos, belowBlockState.setValue(TIER1, true), 3);

            if(belowBlockState.getValue(AdobeFurnaceBlock.LIT)){
                level.setBlock(pos, state.setValue(SMOKING, true), 3);
                System.out.println("Below furnace is LIT");
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockPos belowPos = pos.below();
        BlockState belowBlockState = level.getBlockState(belowPos);

        if (belowBlockState.is(ModBlocks.ADOBE_FURNACE.get()) && !level.isClientSide){
            if (belowBlockState.getValue(TIER1) && belowBlockState.getBlock() instanceof AdobeFurnaceBlock furnaceBlock){
                furnaceBlock.openContainer(level, belowPos, player);
                return InteractionResult.SUCCESS_SERVER;
            } else {
                System.err.println("Adobe furnace" + belowBlockState + " at position " + belowPos +"has a chimney on top, but is not TIER1!");
            }
        }
        return super.useWithoutItem(state, level, pos, player,hitResult);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockPos belowPos = pos.below();
        BlockState belowBlockState = level.getBlockState(belowPos);
        if (belowBlockState.is(ModBlocks.ADOBE_FURNACE.get())){
            level.setBlock(belowPos, belowBlockState.setValue(TIER1, false), 3);
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (level.getBlockState(pos).getValue(SMOKING)){
            makeParticles(level, pos, false, false);
        }
    }

    public static void makeParticles(Level level, BlockPos pos, boolean isSignalFire, boolean spawnExtraSmoke) {
        RandomSource randomsource = level.getRandom();
        SimpleParticleType simpleparticletype = isSignalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        level.addAlwaysVisibleParticle(
                simpleparticletype,
                true,
                (double)pos.getX() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                (double)pos.getY() + randomsource.nextDouble() + randomsource.nextDouble() + 0.5,
                (double)pos.getZ() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                0.0,
                0.07,
                0.0
        );
        if (spawnExtraSmoke) {
            level.addParticle(
                    ParticleTypes.SMOKE,
                    (double)pos.getX() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                    (double)pos.getY() + 0.4,
                    (double)pos.getZ() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                    0.0,
                    0.005,
                    0.0
            );
        }
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }
}
