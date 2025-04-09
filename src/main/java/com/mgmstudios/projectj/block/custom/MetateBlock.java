package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.entity.custom.MetateBlockEntity;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.recipe.ModRecipeTypes;
import com.mgmstudios.projectj.recipe.metate.MetateInput;
import com.mgmstudios.projectj.recipe.metate.MetateRecipe;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetateBlock extends BaseEntityBlock {

    public static final MapCodec<MetateBlock> CODEC = simpleCodec(MetateBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE_EAST_WEST = Block.box(2, 0.0, 0, 14.0, 9.0, 16.0);
    protected static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0, 0.0, 2, 16.0, 9.0, 14.0);

    public MetateBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH, SOUTH -> SHAPE_NORTH_SOUTH;
            default -> SHAPE_EAST_WEST;
        };
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if ((level.getBlockEntity(pos) instanceof MetateBlockEntity metate)) {
            metate.drops();
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stackToInsert, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof MetateBlockEntity metate)) {
            return InteractionResult.PASS;
        }

        if (stackToInsert.is(ModItems.STONE_MANO) && level instanceof ServerLevel serverLevel){

            List<ItemStack> items = new ArrayList<>();
            MetateInput input = new MetateInput(metate.inventory.getStackInSlot(0));
            Optional<RecipeHolder<MetateRecipe>> optional = serverLevel.recipeAccess().getRecipeFor(
                    ModRecipeTypes.METATE_RECIPE_TYPE.get(),
                    input,
                    level
            );

            ItemStack result = optional
                    .map(RecipeHolder::value)
                    .map(e -> e.assemble(input, level.registryAccess()))
                    .orElse(ItemStack.EMPTY);

            if (!result.isEmpty()){
                //metate.swapItem(result);
                Vec3 center = pos.getBottomCenter();
                ItemEntity itemEntity = new ItemEntity(serverLevel, center.x, center.y + 0.6, center.z, result);
                serverLevel.addFreshEntity(itemEntity);
                metate.clearAllContents();
                serverLevel.playSound(null, pos, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1f, 2f);
                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.literal("§cInvalid Recipe.§r"), true);
                return InteractionResult.PASS;
            }
        }

        boolean canInsert = metate.isEmpty();
        boolean itemInHand = !stackToInsert.isEmpty();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (canInsert && itemInHand) {
            metate.insertNewItemStack(stackToInsert);
            stackToInsert.shrink(1);
            if (player instanceof ServerPlayer serverPlayer){
                serverPlayer.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            }
            return InteractionResult.CONSUME;
        } else if (!itemInHand) {
            ItemStack extractedStack = metate.extractLatestItem();
            if (extractedStack.isEmpty()) {
                player.displayClientMessage(Component.literal("§7Inventory empty§r"), true);
                if (player instanceof ServerPlayer serverPlayer){
                    serverPlayer.playNotifySound(SoundEvents.BUNDLE_INSERT_FAIL, SoundSource.BLOCKS, 1f, 2f);
                }
                return InteractionResult.PASS;
            } else {
                //player.displayClientMessage(Component.literal("Extracted " + extractedStack.getDisplayName().getString()), true);
                int suitableSlot = player.getInventory().getSlotWithRemainingSpace(extractedStack);
                if (suitableSlot >= 0){
                    ItemStack slotStack = player.getInventory().getItem(suitableSlot);
                    slotStack.grow(1);
                } else {
                    player.setItemInHand(hand, extractedStack);
                }
                if (player instanceof ServerPlayer serverPlayer){
                    serverPlayer.playNotifySound(SoundEvents.ITEM_PICKUP,SoundSource.BLOCKS, 1f, 1f);
                }
            }
        } else {
            if (player instanceof ServerPlayer serverPlayer){
                serverPlayer.playNotifySound(SoundEvents.BUNDLE_INSERT_FAIL, SoundSource.BLOCKS, 1f, 1f);
            }
            player.displayClientMessage(Component.literal("§cInventory full§r"), true);
            return InteractionResult.FAIL;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MetateBlockEntity(pos, state);
    }
}
