package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.recipe.ModRecipeBookCategories;
import com.mgmstudios.projectj.recipe.ModRecipeTypes;
import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarInput;
import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarRecipe;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AncientAltarBlock extends BaseEntityBlock {

    public static final MapCodec<AncientAltarBlock> CODEC = simpleCodec(AncientAltarBlock::new);

    public static final BooleanProperty CRAFTING = BooleanProperty.create("altar_crafting");
    public static final BooleanProperty BLOOD_INSIDE = BooleanProperty.create("altar_blood_inside");
    public static final BooleanProperty PYRITE_INSIDE = BooleanProperty.create("altar_pyrite_inside");


    public static final VoxelShape SHAPE_BASE = Block.box(2, 0.0, 2.0, 14.0, 2.0, 14.0);
    public static final VoxelShape SHAPE_POST = Block.box(7, 2.0, 7, 9.0, 14.0, 9.0);
    public static final VoxelShape SHAPE_TOP = Block.box(1, 10, 1, 15.0, 16.0, 15.0);
    public static final VoxelShape SHAPE = Shapes.or(SHAPE_BASE, SHAPE_POST, SHAPE_TOP);

    public AncientAltarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRAFTING, false).setValue(BLOOD_INSIDE, false).setValue(PYRITE_INSIDE, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AncientAltarBlockEntity(pos, state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()){
            if (level.getBlockEntity(pos) instanceof AncientAltarBlockEntity ancientAltarBlockEntity){
                ancientAltarBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(CRAFTING)){
            if (level.getBlockEntity(pos) instanceof AncientAltarBlockEntity altarEntity) {
                level.setBlockAndUpdate(pos, state.setValue(BLOOD_INSIDE, false).setValue(CRAFTING, false).setValue(PYRITE_INSIDE, false));
                level.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);

                ItemStack resultStack = altarEntity.getCraftingResult();

                Vec3 centerPos = pos.getBottomCenter();
                level.addFreshEntity(new ItemEntity(level, centerPos.x, centerPos.y + 1.5, centerPos.z, resultStack));

                altarEntity.drain(FluidType.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
                altarEntity.clearAllContents();
            }
        }
        super.tick(state, level, pos, random);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stackToInsert, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof AncientAltarBlockEntity altarEntity) || state.getValue(CRAFTING)) {
            return InteractionResult.PASS;
        }

        // Initializes crafting procedure
        if (stackToInsert.is(ModItems.SACRIFICIAL_DAGGER) && !altarEntity.isEmpty() && level instanceof ServerLevel serverLevel){
            List<ItemStack> items = new ArrayList<>();
            ItemStackHandler inventory = altarEntity.getInventory();
            for (int index = 0; index < inventory.getSlots(); index++){
                if (!inventory.getStackInSlot(index).is(Items.AIR)){
                    items.add(inventory.getStackInSlot(index));
                }
            }

            AncientAltarInput input = new AncientAltarInput(
                    altarEntity.getFluidInTank(0),
                    items
                    );
            Optional<RecipeHolder<AncientAltarRecipe>> optional = serverLevel.recipeAccess().getRecipeFor(
                    ModRecipeTypes.ANCIENT_ALTAR_RECIPE_TYPE.get(),
                    input,
                    level
            );

            ItemStack result = optional
                    .map(RecipeHolder::value)
                    .map(e -> e.assemble(input, level.registryAccess()))
                    .orElse(ItemStack.EMPTY);

            if (!result.isEmpty()){
                altarEntity.setCraftingResult(result);
                level.setBlockAndUpdate(pos, state.setValue(CRAFTING, true));
                level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1f, 2f);
                level.scheduleTick(pos, this, 60);
                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.literal("§cInvalid Recipe.§r"), true);
                return InteractionResult.PASS;
            }

        } else if (stackToInsert.is(ModItems.LIQUID_PYRITE_BUCKET)){
            FluidStack pyrite = new FluidStack(ModFluids.FLOWING_PYRITE.get(), FluidType.BUCKET_VOLUME);
            int filled = altarEntity.fill(pyrite, IFluidHandler.FluidAction.EXECUTE);
            if (filled > 0){
                if (!player.isCreative()){
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                //player.displayClientMessage(Component.literal("Filled altar with " + filled + "mb"), true);
                player.playNotifySound(SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1f, 1f);
                level.setBlockAndUpdate(pos, state.setValue(PYRITE_INSIDE, true));
                return InteractionResult.SUCCESS;
            }
        } else if (stackToInsert.is(Items.BUCKET)){
            FluidStack drained = altarEntity.drain(FluidType.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
            if (!drained.isEmpty()){
                if (!player.isCreative()){
                    player.setItemInHand(hand, new ItemStack(ModItems.LIQUID_PYRITE_BUCKET.get()));
                }
                //player.displayClientMessage(Component.literal("Drained altar with " + drained.getAmount() + "mb of " + drained.getFluid()), true);
                player.playNotifySound(SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1f, 1f);
                level.setBlockAndUpdate(pos, state.setValue(PYRITE_INSIDE, false));
                return InteractionResult.SUCCESS;
            }
        } else if (stackToInsert.is(ModItems.CRUDE_SACRIFICE_BOWL) || stackToInsert.is(ModItems.FILLED_CRUDE_SACRIFICE_BOWL)){
            if (state.getValue(BLOOD_INSIDE) && stackToInsert.is(ModItems.CRUDE_SACRIFICE_BOWL)){
                if (stackToInsert.getCount() == 1){
                    player.setItemInHand(hand, new ItemStack(ModItems.FILLED_CRUDE_SACRIFICE_BOWL.get()));
                } else {
                    stackToInsert.shrink(1);
                    player.getInventory().add(new ItemStack(ModItems.FILLED_CRUDE_SACRIFICE_BOWL.get()));
                }
                level.playSound(player, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1f, 1f);
                level.setBlockAndUpdate(pos, state.setValue(BLOOD_INSIDE, false));
            } else if (!state.getValue(BLOOD_INSIDE) && stackToInsert.is(ModItems.FILLED_CRUDE_SACRIFICE_BOWL)){
                ItemStack bowlStack = new ItemStack(ModItems.CRUDE_SACRIFICE_BOWL.get());
                int suitableSlot = player.getInventory().getSlotWithRemainingSpace(bowlStack);
                if (suitableSlot > 0){
                    stackToInsert.shrink(1);
                    ItemStack slotStack = player.getInventory().getItem(suitableSlot);
                    slotStack.grow(1);
                } else {
                    player.setItemInHand(hand, bowlStack);
                }
                level.setBlockAndUpdate(pos, state.setValue(BLOOD_INSIDE, true));
                level.playSound(player, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1f, 1f);
            } else {
                return InteractionResult.PASS;
            }
            return InteractionResult.SUCCESS;
        }


        boolean canInsert = altarEntity.canInsert();
        boolean itemInHand = !stackToInsert.isEmpty();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (canInsert && itemInHand) {
            altarEntity.insertNewItemStack(stackToInsert);
            stackToInsert.shrink(1);
            if (player instanceof ServerPlayer serverPlayer){
                serverPlayer.playNotifySound(SoundEvents.ITEM_PICKUP,SoundSource.BLOCKS, 1f, 2f);
            }
            return InteractionResult.CONSUME;
        } else if (!itemInHand) {
            ItemStack extractedStack = altarEntity.extractLatestItem();
            if (extractedStack.isEmpty()) {
                player.displayClientMessage(Component.literal("§7Inventory empty§r"), true);
                if (player instanceof ServerPlayer serverPlayer){
                    serverPlayer.playNotifySound(SoundEvents.BUNDLE_INSERT_FAIL, SoundSource.BLOCKS, 1f, 2f);
                }
                return InteractionResult.PASS;
            } else {
                //player.displayClientMessage(Component.literal("Extracted " + extractedStack.getDisplayName().getString()), true);
                int suitableSlot = player.getInventory().getSlotWithRemainingSpace(extractedStack);
                if (suitableSlot > 0){
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
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(CRAFTING, false).setValue(BLOOD_INSIDE, false).setValue(PYRITE_INSIDE, false);
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        if (level instanceof ServerLevel serverLevel){
            System.out.println(" -> " + newState);
        }
        super.onBlockStateChange(level, pos, oldState, newState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CRAFTING);
        builder.add(BLOOD_INSIDE);
        builder.add(PYRITE_INSIDE);
    }
}
