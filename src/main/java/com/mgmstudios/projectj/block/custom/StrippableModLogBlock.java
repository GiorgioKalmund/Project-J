package com.mgmstudios.projectj.block.custom;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import static com.mgmstudios.projectj.block.ModBlockFamilies.STRIPPABLES;

public class StrippableModLogBlock extends RotatedPillarBlock {

    public StrippableModLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)){
            var baseBlock = state.getBlock();
            Block strippedBlock = STRIPPABLES.get(baseBlock);
            return strippedBlock.defaultBlockState().setValue(AXIS, state.getValue(AXIS));

        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
