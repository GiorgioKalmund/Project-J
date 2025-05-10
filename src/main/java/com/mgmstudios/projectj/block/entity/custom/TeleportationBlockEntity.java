package com.mgmstudios.projectj.block.entity.custom;

import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TeleportationBlockEntity extends BlockEntity {

    private BlockPos connectedPosition;
    private DyeColor textColor;
    private boolean glowing;
    private String name;

    public TeleportationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TELEPORTATION_PAD_BE.get(), pos, state);
        setName("");
        setTextColor(DyeColor.WHITE);
    }

    public BlockPos getConnectedPosition() {
        return connectedPosition;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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
        tag.putString("name", name);
        tag.putString("color", this.textColor.getName().toUpperCase());
        tag.putBoolean("glowing", glowing);
    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("ConnectedX")) {
           setConnectedPosition(new BlockPos(tag.getInt("ConnectedX"), tag.getInt("ConnectedY"), tag.getInt("ConnectedZ")));
        }
        if (tag.contains("name"))
            setName(tag.getString("name"));
        if (tag.contains("glowing"))
            setGlowing(tag.getBoolean("glowing"));
        if (tag.contains("color")){
            try {
                DyeColor color = DyeColor.valueOf(tag.getString("color").toUpperCase());
                setTextColor(color);
            } catch (Exception e){
                System.err.println(tag.getString("color").toUpperCase() + " is not a valid color!");
            }
        }
    }

    public String getName() {
        return name;
    }

    public DyeColor getTextColor() {
        return textColor;
    }

    public void setTextColor(DyeColor textColor) {
        if (textColor != null){
            this.textColor = textColor;
        }
        setChanged();
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
        setChanged();
    }

    public boolean getGlowing(){
        return glowing;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
    }
}
