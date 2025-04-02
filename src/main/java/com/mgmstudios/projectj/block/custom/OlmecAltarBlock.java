package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.util.ModTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class OlmecAltarBlock extends HorizontalDirectionalBlock {

    public static final BooleanProperty CRAFTING = BooleanProperty.create("altar_crafting");
    private static final Logger log = LogManager.getLogger(OlmecAltarBlock.class);

    private static final List<Block> HEADS_ALL_BASIC = new LinkedList<>(Arrays.asList(ModBlocks.CONDUIT_OLMEC_HEAD.get(), ModBlocks.DAMAGE_OLMEC_HEAD.get(), ModBlocks.REGENERATION_OLMEC_HEAD.get(), ModBlocks.RESISTANT_OLMEC_HEAD.get()));

    public OlmecAltarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CRAFTING, false).setValue(FACING, Direction.NORTH));
    }
    public static final MapCodec<OlmecAltarBlock> CODEC = simpleCodec(OlmecAltarBlock::new);
    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(CRAFTING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(CRAFTING, false).setValue(FACING, context.getHorizontalDirection().getClockWise());
    }


    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && !state.getValue(CRAFTING)){
            boolean validItem = (itemEntity.getItem().is(ModTags.Items.ALTAR_CRAFTABLE));
            if (validItem){
                BlockPos north2apart = pos.north().north();
                BlockPos south2apart = pos.south().south();
                BlockPos east2apart = pos.east().east();
                BlockPos west2apart = pos.west().west();

                showEnchantingParticles(level, pos);
                if (level instanceof ServerLevel serverLevel && validAltar(serverLevel, pos, HEADS_ALL_BASIC, north2apart, south2apart, east2apart, west2apart)){
                    level.setBlockAndUpdate(pos, state.setValue(CRAFTING, true));
                    level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS);
                    level.scheduleTick(pos, this, 40);
                }
            }

            return;
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(CRAFTING))
            return;

        level.levelEvent(2009, pos, 0);
        ItemEntity itemEntity = null;
        try {
            BlockPos abovePos = pos.above();
            List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(abovePos).inflate(0.5F));
            itemEntity = itemEntities.getFirst();
        } catch (Exception e) {
            log.error(String.valueOf(e.getCause()));
        }

        if (itemEntity != null){
            convertItem(itemEntity.getItem(), Items.NETHERITE_PICKAXE, ModItems.MACUAHUITL.get(), level, itemEntity, pos, SoundEvents.EXPERIENCE_ORB_PICKUP);
            convertItemWithMultiplier(itemEntity.getItem(), ModItems.RAW_JADE.get(), ModItems.JADE.get(), 2, level, itemEntity, pos, SoundEvents.EXPERIENCE_ORB_PICKUP);
        } else {
            System.out.println("No entity above block. AbovePos: " + pos.above());
        }

        if (level.getBlockState(pos).is(ModBlocks.OLMEC_ALTAR)){
            level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(CRAFTING, false));
        }

    }

    private void convertItem(ItemStack stack, Item fromItem, Item toItem, Level level, ItemEntity itemEntity, BlockPos pos, SoundEvent soundEvent){
        convertItemWithModulo(stack, fromItem, toItem, 1, level, itemEntity, pos, soundEvent);
    }

    private boolean validAltar(ServerLevel level, BlockPos altarPos, List<Block> list, BlockPos northPos, BlockPos southPos, BlockPos eastPos, BlockPos westPos){
        Map<BlockPos, Direction> positionDirectionsMap = Map.of(northPos, Direction.SOUTH, southPos, Direction.NORTH, eastPos, Direction.WEST, westPos, Direction.EAST);
        List<Block> copyList = new ArrayList<>(List.copyOf(list));

        for (BlockPos pos : positionDirectionsMap.keySet()){
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() instanceof OlmecHeadBlock olmecHeadBlock && blockState.getValue(OlmecHeadBlock.LIT) && blockState.getValue(FACING) == positionDirectionsMap.get(pos)){
                olmecHeadBlock.spawnBeaconBeam(level, pos, altarPos, 10);
                copyList.remove(level.getBlockState(pos).getBlock());
            }
        }
        return copyList.isEmpty();
    }

    private void convertItemWithModulo(ItemStack stack, Item fromItem, Item toItem, int modulo, Level level, ItemEntity itemEntity, BlockPos pos, SoundEvent soundEvent) {
        if (stack.getItem() == fromItem && stack.getCount() >= modulo) {
            int fullConversions = stack.getCount() / modulo;
            int remainder = stack.getCount() % modulo;

            convert(stack, toItem, fullConversions, remainder, level, itemEntity, pos, soundEvent);
        }
    }

    private void convertItemWithMultiplier(ItemStack stack, Item fromItem, Item toItem, int multiplier, Level level, ItemEntity itemEntity, BlockPos pos, SoundEvent soundEvent) {
        if (stack.getItem() == fromItem) {
            int stackCount = stack.getCount() * multiplier;
            int remainder = 0;
            if (stackCount > 64){
                remainder = stackCount - 64;
                stackCount = 64;
            }

            convert(stack, toItem, stackCount, remainder, level, itemEntity, pos, soundEvent);
        }
    }

    private static void convert(ItemStack stack, Item toItem, int fullConversions, int remainder, Level level, ItemEntity itemEntity, BlockPos pos, SoundEvent soundEvent){
        ItemStack fullConversionStack = new ItemStack(toItem, fullConversions);
        ItemStack remainderStack = new ItemStack(toItem, remainder);

        itemEntity.setItem(fullConversionStack);

        if (stack.isEnchanted()) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            EnchantmentHelper.setEnchantments(fullConversionStack, enchantments);
            EnchantmentHelper.setEnchantments(remainderStack, enchantments);
        }

        if (remainder > 0) {
            ItemEntity remainderEntity = new ItemEntity(
                    level,
                    itemEntity.getX(),
                    itemEntity.getY(),
                    itemEntity.getZ(),
                    remainderStack
            );
            level.addFreshEntity(remainderEntity);

        }

        level.playSound(itemEntity, pos, soundEvent, SoundSource.BLOCKS, 1f, 1f);
    }

    private static void showEnchantingParticles(Level level, BlockPos pos){
        RandomSource randomsource = level.getRandom();

        for (int k1 = 0; k1 < 4; k1++) {
            level
                .addParticle(
                    ParticleTypes.ENCHANT,
                    (double)pos.getX() + randomsource.nextDouble(),
                    (double)pos.getY() + 0.7,
                    (double)pos.getZ() + randomsource.nextDouble(),
                    0.0,
                    0.0,
                    0.0
                );
         }
    }
}
