package com.mgmstudios.projectj.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class HollowTreeBlock extends HorizontalDirectionalBlock {

    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");

    public static final MapCodec<HollowTreeBlock> CODEC = simpleCodec(HollowTreeBlock::new);

    public static final VoxelShape BOTTOM_LAYER = Block.box(0, 0, 0, 16, 2, 16);
    public static final VoxelShape SIDE_1 = Block.box(0, 0, 0, 2, 16, 16);
    public static final VoxelShape SIDE_2 = Block.box(14, 0, 0, 16, 16, 16);
    public static final VoxelShape SIDE_3 = Block.box(0, 0, 0, 16, 16, 2);
    public static final VoxelShape SIDE_4 = Block.box(0, 0, 14, 16, 16, 16);

    public static final VoxelShape SHAPE_NORTH_SOUTH = Shapes.or(BOTTOM_LAYER, SIDE_1, SIDE_2);
    public static final VoxelShape SHAPE_EAST_WEST = Shapes.or(BOTTOM_LAYER, SIDE_3, SIDE_4);

    public static final VoxelShape SHAPE = Shapes.or(BOTTOM_LAYER);
    public HollowTreeBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(CONNECTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(CONNECTED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape baseShape = switch (state.getValue(FACING)){
            case NORTH, SOUTH-> SHAPE_NORTH_SOUTH;
            case EAST, WEST -> SHAPE_EAST_WEST;
            default -> SHAPE;
        };
        VoxelShape endShape = Shapes.empty();
        if (!state.getValue(CONNECTED)){
            endShape = switch (state.getValue(FACING)){
                case EAST -> SIDE_1;
                case WEST -> SIDE_2;
                case SOUTH -> SIDE_3;
                case NORTH -> SIDE_4;
                default -> Shapes.empty();
            };
        }
        return Shapes.or(baseShape, endShape);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, orientation, movedByPiston);
        Direction iAmFacing = state.getValue(FACING);
        BlockState neighbourBlockState = level.getBlockState(pos.relative(iAmFacing.getOpposite()));
        boolean inBetweenLogs = inBetweenLogs(level, state.getValue(FACING), pos);
        if (neighbourBlockState.getBlock() instanceof HollowTreeBlock && !inBetweenLogs){
            BlockState previouslyConnectedBlockState = level.getBlockState(pos.relative(iAmFacing));
            Block previouslyConnectedBlock = level.getBlockState(pos.relative(iAmFacing)).getBlock();
            if (previouslyConnectedBlock instanceof HollowTreeBlock && neighbourBlockState.getBlock() instanceof HollowTreeBlock){
                Direction neighbourDirection = neighbourBlockState.getValue(FACING);
                Direction previouslyConnectedDirection = previouslyConnectedBlockState.getValue(FACING);
                if (neighbourDirection.equals(previouslyConnectedDirection) && previouslyConnectedBlockState.getValue(CONNECTED)){
                    inBetweenLogs = true;
                }
            }
        }
        level.setBlockAndUpdate(pos, state.setValue(CONNECTED, inBetweenLogs));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction placedirection = context.getHorizontalDirection().getOpposite();
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        return super.getStateForPlacement(context).setValue(FACING, placedirection).setValue(CONNECTED, inBetweenLogs(level, placedirection, blockPos));
    }

    private boolean inBetweenLogs(Level level, Direction placementDirection, BlockPos placedPos){
        Direction[] directions = {placementDirection, placementDirection.getOpposite(), placementDirection.getClockWise(), placementDirection.getCounterClockWise()};
        Map<Direction, BlockPos> connectedDirections = new HashMap<>();
        boolean oppositeLogs = false;
        for (Direction dir : directions){
            BlockPos pos = placedPos.relative(dir);
            if (level.getBlockState(pos).getBlock() instanceof HollowTreeBlock){
                Direction blockFacingDirection = level.getBlockState(pos).getValue(FACING);
                connectedDirections.put(blockFacingDirection, pos);
            }
        }
        for (Direction connectedDir : connectedDirections.keySet()){
            if (connectedDirections.containsKey(connectedDir.getOpposite())){
                // Check that they are facing the correct way
                BlockPos pos = connectedDirections.get(connectedDir);
                if (pos.relative(connectedDir).equals(placedPos)){
                    if (placementDirection.equals(connectedDir) || placementDirection.equals(connectedDir.getOpposite())){
                        oppositeLogs = true;
                        break;
                    } else {
                        System.out.println("Possible to connect, but wrong rotation");
                    }
                }else {
                    System.out.println("Correct direction, but opposite");
                }
            }
        }
        return oppositeLogs;
    }

    @Override
    protected MapCodec<? extends HollowTreeBlock> codec() {
        return CODEC;
    }
}
