package com.mgmstudios.projectj.block.custom.botany;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.datagen.ModRecipeProvider;
import com.mgmstudios.projectj.item.ModItems;
import com.nimbusds.openid.connect.sdk.assurance.IdentityTrustFramework;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class BotanyPotBlock extends FlowerPotBlock implements BonemealableBlock {

    public static final IntegerProperty AGE;
    public static final VoxelShape SHAPE_BOTTOM = Block.box(2, 0, 2, 14, 10, 14);
    public static final VoxelShape SHAPE_NORTH = Block.box(0, 8, 2, 2, 10, 14);
    public static final VoxelShape SHAPE_SOUTH = Block.box(2, 8, 0, 14, 10, 2);
    public static final VoxelShape SHAPE_EAST = Block.box(14, 8, 2, 16, 10, 14);
    public static final VoxelShape SHAPE_WEST = Block.box(2, 8, 14, 14, 10, 16);
    public static final VoxelShape SHAPE = Shapes.or(SHAPE_BOTTOM, SHAPE_NORTH, SHAPE_SOUTH, SHAPE_EAST, SHAPE_WEST);
    public static HashMap<Supplier<Item>, Supplier<BlockState>> INTERACTIONS = new HashMap<>();
    private final Supplier<Item> itemSupplier;
    private final Supplier<Item> seedSupplier;

    public BotanyPotBlock(Properties properties) {
        this(() -> Items.AIR, () -> Items.AIR, properties);
    }

    public BotanyPotBlock(Supplier<Item> itemSupplier, Supplier<Item> seedSupplier, Properties properties) {
        super(Blocks.AIR, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
        this.itemSupplier = itemSupplier;
        this.seedSupplier = seedSupplier;
        INTERACTIONS.put(seedSupplier, this::defaultBlockState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(AGE, 0);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        dropContents(state, level, player, pos);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    protected void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        int i = state.getValue(AGE);
        if (i < 3 && serverLevel.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(serverLevel, pos, state, randomSource.nextInt(5) == 0)) {
            BlockState blockstate = state.setValue(AGE, i + 1);
            serverLevel.setBlock(pos, blockstate, 2);
            CommonHooks.fireCropGrowPost(serverLevel, pos, state);
            serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
        }

    }

    @Override
    protected boolean isRandomlyTicking(BlockState p_383068_) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected Item getItem(){
        return itemSupplier.get();
    }

    protected Item getSeeds(){
        return seedSupplier.get();
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.is(ModBlocks.BOTANY_POT) && !itemStack.is(Items.AIR)){
            Item item = itemStack.getItem();
            Supplier<BlockState> supplierStateToBe = getBlockStateForItem(item);
            if (supplierStateToBe != null){
                BlockState stateToBe = supplierStateToBe.get();
                if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer){
                    serverPlayer.playNotifySound(SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1f, 1f);
                    serverLevel.setBlockAndUpdate(pos, stateToBe);
                    if (!player.isCreative()){
                        itemStack.shrink(1);
                    }
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        } else if (itemStack.is(ItemTags.SHOVELS)){
            dropContents(state, level, player, pos);
            popResource(level, pos, new ItemStack(getSeeds()));
            level.setBlockAndUpdate(pos, ModBlocks.BOTANY_POT.get().defaultBlockState());
            level.playSound(player, pos.above(), SoundEvents.ROOTED_DIRT_BREAK, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS_SERVER;
        }

        int i = state.getValue(AGE);
        boolean flag = i == 3;
        return (!flag && itemStack.is(Items.BONE_MEAL) ? InteractionResult.PASS : super.useItemOn(itemStack, state, level, pos, player, hand, hitResult));
    }

    private void dropContents(BlockState state, Level level, Player player, BlockPos pos){
        int i = state.getValue(AGE);
        boolean flag = i == 3;
        int j = 1 + level.random.nextInt(2);
        if (i > 1 && !player.isCreative()){
            popResource(level, pos, new ItemStack(getItem(), j + (flag ? 1 : 0)));
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int i = state.getValue(AGE);
        boolean flag = i == 3;
        if (i > 1) {
            int j = 1 + level.random.nextInt(2);
            popResource(level, pos, new ItemStack(getItem(), j + (flag ? 1 : 0)));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockstate = state.setValue(AGE, 1);
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    static {
        AGE = BlockStateProperties.AGE_3;
    }

    public Supplier<BlockState> getBlockStateForItem(Item targetItem) {
        for (Map.Entry<Supplier<Item>, Supplier<BlockState>> entry : INTERACTIONS.entrySet()) {
            if (entry.getKey().get() == targetItem) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_256056_, BlockPos p_57261_, BlockState p_57262_) {
        return p_57262_.getValue(AGE) < 3 && getItem() != Items.AIR;
    }

    @Override
    public boolean isBonemealSuccess(Level p_222558_, RandomSource p_222559_, BlockPos p_222560_, BlockState p_222561_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_222553_, RandomSource p_222554_, BlockPos p_222555_, BlockState p_222556_) {
        int i = Math.min(3, p_222556_.getValue(AGE) + 1);
        p_222553_.setBlock(p_222555_, p_222556_.setValue(AGE, i), 2);
    }
}
