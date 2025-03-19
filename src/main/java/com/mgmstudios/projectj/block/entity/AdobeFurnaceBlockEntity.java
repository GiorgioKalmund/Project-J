package com.mgmstudios.projectj.block.entity;

import com.mgmstudios.projectj.screen.custom.AdobeFurnaceMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AdobeFurnaceBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        };
    };
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 200;

    public AdobeFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ADOBE_FURNACE_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> AdobeFurnaceBlockEntity.this.progress;
                    case 1 -> AdobeFurnaceBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0:
                        AdobeFurnaceBlockEntity.this.progress = value;
                        break;
                    case 1:
                        AdobeFurnaceBlockEntity.this.maxProgress = value;
                        break;
                }

            }

            @Override
            public int getCount() {
                // return number of slots
                return 2;
            }
        };
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        level.setBlock(blockPos.above(), Blocks.ACACIA_DOOR.defaultBlockState(), 3);
        
    }

    @Override
    public AbstractContainerMenu createMenu(int u, Inventory inventory, Player player) {

        return new AdobeFurnaceMenu(u, inventory, this,this.data);
    }

    @Override
    public Component getDisplayName() {

        return Component.translatable("block.projectj.adobe_furnace");
    }

    // Made by Kaupenjoe
    public void dropContents() {
        SimpleContainer container = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            container.setItem(i, inventory.getStackInSlot(i));
        }
    }

    // Save and load the stats
    @Override
    protected void saveAdditional(CompoundTag tag, Provider registries) {
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("adobe_furnace.progress", progress);
        tag.putInt("adobe_furnace.max_progess", maxProgress);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, Provider registries) {

        super.loadAdditional(tag, registries);

        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("adobe_furnace.progress");
        maxProgress = tag.getInt("adobe_furnace.max_progress");
    }

    @Override
    public CompoundTag getUpdateTag(Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    
}
