package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.item.ModToolMaterials;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;
import java.util.*;

public class TrowelItem extends Item{


    public TrowelItem(Properties properties) {
        super(ModToolMaterials.TROWEL_MATERIAL.applyToolProperties(properties, BlockTags.MINEABLE_WITH_SHOVEL, 1F, -0.5F));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS; // Prevent double execution
        }

        if (context.getPlayer() != null){
            Player player = context.getPlayer();
            BlockHitResult hitResult = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
            BlockPlaceContext blockPlaceContext = new BlockPlaceContext(player, context.getHand(), context.getItemInHand(), hitResult);
            RandomSource random = context.getLevel().getRandom();
            List<ItemStack> blockItems = new ArrayList<>();
            Inventory inv = player.getInventory();
            for (int i = 0; i < 9; i++){
                ItemStack stack = inv.getItem(i);
                if (stack.getItem() instanceof BlockItem){
                    blockItems.add(stack);
                }
            }
            if (blockItems.isEmpty()){
                return InteractionResult.PASS;
            }
            int randomIndex = random.nextInt(0, blockItems.size());
            ItemStack chosenStack = blockItems.get(randomIndex);
            BlockItem item = (BlockItem) chosenStack.getItem();
            InteractionResult interactionresult = place(blockPlaceContext, chosenStack, item.getBlock());
            return !interactionresult.consumesAction() && context.getItemInHand().has(DataComponents.CONSUMABLE) ? super.use(context.getLevel(), context.getPlayer(), context.getHand()) : interactionresult;
        }
        return super.useOn(context);
    }

    public InteractionResult place(BlockPlaceContext context, ItemStack itemStack, Block blockToPlace) {
        if (!blockToPlace.isEnabled(context.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!context.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = this.updatePlacementContext(context);
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = this.getPlacementState(blockplacecontext, blockToPlace);
                if (blockstate == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockplacecontext, blockstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = updateBlockStateFromTag(blockpos, level, itemStack, blockstate1);
                        this.updateCustomBlockEntityTag(blockpos, level, player, itemStack, blockstate1);
                        updateBlockEntityComponents(level, blockpos, itemStack);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemStack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemStack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, context.getPlayer());
                    level.playSound(
                            null,
                            blockpos,
                            getPlaceSound(blockstate1, level, blockpos, context.getPlayer()),
                            SoundSource.BLOCKS,
                            (soundtype.getVolume() + 1.0F) / 2.0F,
                            soundtype.getPitch() * 0.8F
                    );
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(player, blockstate1));
                    itemStack.consume(1, player);
                    return InteractionResult.SUCCESS;
                }
            }
        }
    }

    protected SoundEvent getPlaceSound(BlockState p_state, Level world, BlockPos pos, Player entity) {
        return p_state.getSoundType(world, pos, entity).getPlaceSound();
    }

    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        return updateCustomBlockEntityTag(level, player, pos, stack);
    }

    public static boolean updateCustomBlockEntityTag(Level level, @Nullable Player player, BlockPos pos, ItemStack stack) {
        if (level.isClientSide) {
            return false;
        } else {
            CustomData customdata = (CustomData)stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
            if (!customdata.isEmpty()) {
                BlockEntityType<?> blockentitytype = (BlockEntityType)customdata.parseEntityType(level.registryAccess(), Registries.BLOCK_ENTITY_TYPE);
                if (blockentitytype == null) {
                    return false;
                }

                BlockEntity blockentity = level.getBlockEntity(pos);
                if (blockentity != null) {
                    BlockEntityType<?> blockentitytype1 = blockentity.getType();
                    if (blockentitytype1 != blockentitytype) {
                        return false;
                    }

                    if (blockentitytype1.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
                        return false;
                    }

                    return customdata.loadInto(blockentity, level.registryAccess());
                }
            }

            return false;
        }
    }

    private BlockState updateBlockStateFromTag(BlockPos pos, Level level, ItemStack stack, BlockState state) {
        BlockItemStateProperties blockitemstateproperties = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        if (blockitemstateproperties.isEmpty()) {
            return state;
        } else {
            BlockState blockstate = blockitemstateproperties.apply(state);
            if (blockstate != state) {
                level.setBlock(pos, blockstate, 2);
            }

            return blockstate;
        }
    }

    private static void updateBlockEntityComponents(Level level, BlockPos poa, ItemStack stack) {
        BlockEntity blockentity = level.getBlockEntity(poa);
        if (blockentity != null) {
            blockentity.applyComponentsFromItemStack(stack);
            blockentity.setChanged();
        }
    }

    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 11);
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context, Block block) {
        BlockState blockstate = block.getStateForPlacement(context);
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }

    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        Player player = context.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (state.canSurvive(context.getLevel(), context.getClickedPos())) && context.getLevel().isUnobstructed(state, context.getClickedPos(), collisioncontext);
    }


    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        return context;
    }
}
