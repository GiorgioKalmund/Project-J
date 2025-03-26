package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.mgmstudios.projectj.block.custom.OlmecHeadBlock.FACING;

public class AncientAltarBlock extends Block {

    public AncientAltarBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(CRAFTING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CRAFTING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(CRAFTING, false);
    }

    public static final BooleanProperty CRAFTING = BooleanProperty.create("altar_crafting");

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        System.out.println("Stepped on state: " + state);
        if (entity instanceof ItemEntity itemEntity && level instanceof ServerLevel serverLevel && !state.getValue(CRAFTING)){
            BlockPos north2apart = pos.north().north();
            BlockPos south2apart = pos.south().south();
            BlockPos east2apart = pos.east().east();
            BlockPos west2apart = pos.west().west();
            boolean validItem = (itemEntity.getItem().is(Items.NETHERITE_PICKAXE));
            if (validItem && validAltar(serverLevel, pos, north2apart, south2apart, east2apart, west2apart)){
                level.setBlockAndUpdate(pos, state.setValue(CRAFTING, true));
                level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS);
                level.scheduleTick(pos, this, 40);
            }

            return;
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.levelEvent(2009, pos, 0);
        BlockPos north2apart = pos.north().north();
        BlockPos south2apart = pos.south().south();
        BlockPos east2apart = pos.east().east();
        BlockPos west2apart = pos.west().west();

        BlockPos abovePos = pos.above();
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(abovePos));
        ItemEntity itemEntity = null;

        try {
            itemEntity = itemEntities.removeFirst();
            if (!validAltar(level, pos, north2apart, south2apart, east2apart, west2apart))
                throw new Exception("Altar disassembled before crafting is finished.");

            convertItem(itemEntity.getItem(), Items.NETHERITE_PICKAXE, ModItems.PAXEL.get(),level, itemEntity, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, north2apart, south2apart, east2apart, west2apart);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            level.playSound(null, pos, SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS);
        }

        level.setBlockAndUpdate(pos, state.setValue(CRAFTING, false));
    }

    private void convertItem(ItemStack stack, Item fromItem, Item toItem, Level level, ItemEntity itemEntity, BlockPos pos, SoundEvent soundEvent, BlockPos ... headPositions){
        convertItemWithModulo(stack, fromItem, toItem, 1, level, itemEntity, pos, soundEvent, headPositions);
    }

    private boolean validAltar(ServerLevel level, BlockPos altarPos, BlockPos northPos, BlockPos southPos, BlockPos eastPos, BlockPos westPos){
        List<Block> list = new LinkedList<>(Arrays.asList(ModBlocks.CONDUIT_OLMEC_HEAD.get(), ModBlocks.DAMAGE_OLMEC_HEAD.get(), ModBlocks.REGENERATION_OLMEC_HEAD.get(), ModBlocks.RESISTANT_OLMEC_HEAD.get()));
        Map<BlockPos, Direction> positionDirectionsMap = Map.of(northPos, Direction.SOUTH, southPos, Direction.NORTH, eastPos, Direction.WEST, westPos, Direction.EAST);

        for (BlockPos pos : positionDirectionsMap.keySet()){
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() instanceof OlmecHeadBlock olmecHeadBlock && blockState.getValue(OlmecHeadBlock.LIT) && blockState.getValue(FACING) == positionDirectionsMap.get(pos)){
                olmecHeadBlock.spawnBeaconBeam(level, pos, altarPos, 10);
                list.remove(level.getBlockState(pos).getBlock());
            }
        }

        return list.isEmpty();
    }

    private void convertItemWithModulo(ItemStack stack, Item fromItem, Item toItem, int modulo, Level level, ItemEntity itemEntity, BlockPos pos, SoundEvent soundEvent, BlockPos ... headPositions) {
        if (stack.getItem() == fromItem && stack.getCount() >= modulo) {
            int fullConversions = stack.getCount() / modulo;
            int remainder = stack.getCount() % modulo;

            itemEntity.setItem(new ItemStack(toItem, fullConversions));

            if (remainder > 0) {
                ItemEntity remainderEntity = new ItemEntity(
                        level,
                        itemEntity.getX(),
                        itemEntity.getY(),
                        itemEntity.getZ(),
                        new ItemStack(fromItem, remainder)
                );
                level.addFreshEntity(remainderEntity);

            }

            level.playSound(itemEntity, pos, soundEvent, SoundSource.BLOCKS, 1f, 1f);
        }
    }
}
