package com.mgmstudios.projectj.block.entity.custom;

import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class TeleportationBlockEntity extends BlockEntity {

    private BlockPos connectedPosition;

    public TeleportationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TELEPORTATION_PAD_BE.get(), pos, state);
    }

    public BlockPos getConnectedPosition() {
        return connectedPosition;
    }

    public void setConnectedPosition(BlockPos connectedPosition) {
        this.connectedPosition = connectedPosition;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (connectedPosition != null) {
            tag.putInt("ConnectedX", connectedPosition.getX());
            tag.putInt("ConnectedY", connectedPosition.getY());
            tag.putInt("ConnectedZ", connectedPosition.getZ());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("ConnectedX")) {
            connectedPosition = new BlockPos(tag.getInt("ConnectedX"), tag.getInt("ConnectedY"), tag.getInt("ConnectedZ"));
        }
    }
}
