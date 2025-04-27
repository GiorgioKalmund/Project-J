package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.RotatedPillarBlock.AXIS;

public class HatchetItem extends AxeItem {
    public HatchetItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    protected final static int MINIMUM_CANOE_LENGTH = 3;
    protected final static int MAXIMUM_CANOE_LENGTH = 10;

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Block seatBlock = ModBlocks.STRIPPED_MESQUITE_LOG.get();

        if (clickedState.is(seatBlock)){
            Direction direction = Direction.NORTH;
            if (clickedState.hasProperty(AXIS)){
                Direction.Axis axis = clickedState.getValue(AXIS);
                if (axis.isHorizontal()){
                    direction = axis.getPositive();
                }
            }
            level.setBlockAndUpdate(clickedPos, ModBlocks.HOLLOW_MESQUITE_LOG.get().defaultBlockState().setValue(FACING, direction));
            Player player = context.getPlayer();
            if (player != null){
                player.playNotifySound(SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1f, 1f);
            }
            return InteractionResult.SUCCESS;
        }

        Block controllerBlock = ModBlocks.MESQUITE_BENCH.get();
        Block buildingBlock = ModBlocks.HOLLOW_MESQUITE_LOG.get();

        String extraMessage;
        boolean invalidDirection = false;

        if (clickedState.is(controllerBlock)){
            BlockPos belowPos = clickedPos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.is(seatBlock)){
                if (belowState.hasProperty(AXIS)){
                    Direction[] canoeDirections = belowState.getValue(AXIS).getDirections();
                    //System.out.println("Canoe facing: " + Arrays.toString(canoeDirections));
                    boolean longEnoughCanoe = false;
                    int canoeLength = 1;
                    Direction targetDirection = null;
                    List<BlockPos> buildingBlockPositions = new ArrayList<>();
                    for (Direction direction : canoeDirections){
                        if (direction == Direction.UP || direction == Direction.DOWN){
                            invalidDirection = true;
                            break;
                        }
                        buildingBlockPositions.clear();
                        targetDirection = direction;
                        BlockPos nextPos = belowPos.relative(targetDirection);
                        BlockState nextState = level.getBlockState(nextPos);
                        while (nextState.is(buildingBlock) && (nextState.getValue(FACING) == targetDirection || nextState.getValue(FACING) == targetDirection.getOpposite()) && canoeLength < MAXIMUM_CANOE_LENGTH){
                            buildingBlockPositions.add(nextPos);
                            nextPos = nextPos.relative(targetDirection);
                            nextState = level.getBlockState(nextPos);
                            canoeLength++;
                        }
                        longEnoughCanoe = canoeLength >= MINIMUM_CANOE_LENGTH;
                        if (longEnoughCanoe)
                            break;
                    }
                    buildingBlockPositions.add(belowPos);
                    buildingBlockPositions.add(clickedPos);
                    if (longEnoughCanoe){
                        if (level instanceof  ServerLevel serverLevel){
                            for (BlockPos pos : buildingBlockPositions){
                                serverLevel.destroyBlock(pos, false);
                                serverLevel.levelEvent(2009, pos, 0);
                            }
                            createCanoe(serverLevel, buildingBlockPositions.getFirst(), canoeLength);
                        }
                        return InteractionResult.SUCCESS;
                        //serverPlayer.sendSystemMessage(Component.literal("Valid canoe facing: " + targetDirection + " with length: " + canoeLength));
                    } else {
                        extraMessage = invalidDirection ? "Invalid Seat Base direction" : "Too short";
                    }
                } else {
                    extraMessage = "Invalid Seat Base";
                    System.err.println(belowState.getBlock() + " has no AXIS property");
                }
            } else {
                extraMessage = "Invalid Seat Base";
                System.err.println("Wrong building block at "+belowPos+"! is: " + belowState.getBlock() + " needs: " + seatBlock);
            }
        }  else {
            extraMessage = "Invalid Seat";
            System.err.println("Wrong controller block at "+clickedPos+"! is: " + clickedState.getBlock() + " needs: " + controllerBlock);
        }
        Player player = context.getPlayer();
        /*
        if (player != null)
            player.displayClientMessage(Component.literal("Incorrect Vehicle configuration. " + extraMessage), true);
         */
        return InteractionResult.FAIL;
    }

    public void createCanoe(ServerLevel serverLevel, BlockPos blockPos, int length){
        EntityType entityType = switch (length){
            case 3 -> EntityType.ACACIA_BOAT;
            case 4 -> EntityType.SPRUCE_BOAT;
            case 5 -> EntityType.JUNGLE_BOAT;
            case 6 -> EntityType.BAMBOO_RAFT;
            default -> null;
        };
        if (entityType != null){
            entityType.spawn(serverLevel, blockPos, EntitySpawnReason.CONVERSION);
            System.out.println("Created " + length + " long Canoe!");
        }
        else {
            System.err.println("Somehow attempted to create a Canoe with length: " + length);
        }
    }
}
