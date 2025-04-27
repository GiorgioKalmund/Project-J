package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.level.block.RotatedPillarBlock.AXIS;

public class HatchetItem extends AxeItem {
    public HatchetItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    protected final static int MINIMUM_CANOE_LENGTH = 3;
    protected final static int MAXIMUM_CANOE_LENGTH = 10;

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Block controllerBlock = ModBlocks.MESQUITE_BENCH.get();
        Block buildingBlock = ModBlocks.STRIPPED_MESQUITE_LOG.get();
        Player player = context.getPlayer();

        Level level = context.getLevel();
        if (true){
            BlockPos clickedPos = context.getClickedPos();
            BlockState clickedState = level.getBlockState(clickedPos);
            if (clickedState.is(controllerBlock)){
                BlockPos belowPos = clickedPos.below();
                BlockState belowState = level.getBlockState(belowPos);
                if (belowState.is(buildingBlock)){
                    if (belowState.hasProperty(AXIS)){
                        Direction[] canoeDirections = belowState.getValue(AXIS).getDirections();
                        //serverPlayer.sendSystemMessage(Component.literal("Canoe facing: " + Arrays.toString(canoeDirections)));
                        boolean longEnoughCanoe = false;
                        int canoeLength = 1;
                        Direction targetDirection = null;
                        List<BlockPos> buildingBlockPositions = new ArrayList<>();
                        buildingBlockPositions.add(belowPos);
                        buildingBlockPositions.add(clickedPos);
                        for (Direction direction : canoeDirections){
                            BlockState blockState = level.getBlockState(belowPos);
                            if (blockState.is(buildingBlock)){
                                targetDirection = direction;
                                BlockPos nextPos = belowPos.relative(targetDirection);
                                BlockState nextState = level.getBlockState(nextPos);
                                while (nextState.is(buildingBlock) && canoeLength < MAXIMUM_CANOE_LENGTH){
                                    buildingBlockPositions.add(nextPos);
                                    nextPos = nextPos.relative(targetDirection);
                                    nextState = level.getBlockState(nextPos);
                                    canoeLength++;
                                }
                                longEnoughCanoe = canoeLength >= MINIMUM_CANOE_LENGTH;
                            }
                        }
                        if (longEnoughCanoe && level instanceof  ServerLevel serverLevel){
                            for (BlockPos pos : buildingBlockPositions){
                                serverLevel.destroyBlock(pos, false);
                                serverLevel.levelEvent(2009, pos, 0);
                            }
                            createCanoe(serverLevel, buildingBlockPositions.get(buildingBlockPositions.size() / 2), canoeLength);
                            return InteractionResult.SUCCESS_SERVER;
                            //serverPlayer.sendSystemMessage(Component.literal("Valid canoe facing: " + targetDirection + " with length: " + canoeLength));
                        } else {
                            //serverPlayer.sendSystemMessage(Component.literal("Canoe facing: " + targetDirection + " is too short: " + canoeLength));
                        }
                    } else {
                        System.err.println(belowState.getBlock() + " has no AXIS property");
                    }
                } else {
                    System.err.println("Wrong building block! is: " + belowState.getBlock() + " needs: " + buildingBlock);
                }
            }  else {
                System.err.println("Wrong controller block! is: " + clickedState.getBlock() + " needs: " + controllerBlock);
            }
        }
        return super.useOn(context);
    }

    public void createCanoe(ServerLevel serverLevel, BlockPos blockPos, int length){
        EntityType entityType = switch (length){
            case 3 -> EntityType.ACACIA_BOAT;
            case 4 -> EntityType.SPRUCE_BOAT;
            case 5 -> EntityType.JUNGLE_BOAT;
            case 6 -> EntityType.BAMBOO_RAFT;
            default -> null;
        };
        if (entityType != null)
            entityType.spawn(serverLevel, blockPos, EntitySpawnReason.CONVERSION);
        else {
            System.err.println("Somehow attempted to create a Canoe with length: " + length);
        }
    }
}
