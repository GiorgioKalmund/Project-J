package com.mgmstudios.projectj.block.entity.custom;

import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class AncientAltarBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            super.onContentsChanged(slot);
        }

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

    public void insertNewItemStack(ItemStack stack){
        ItemStack returned = inventory.insertItem(itemsInside, stack.copy(), false);
        //System.out.println("Inserted " + stack + " and got back " + returned);
        itemsInside++;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public boolean canInsert(){
        //System.out.println("Items: " + itemsInside + " Inv: " + inventory.getSlots());
        return itemsInside < inventory.getSlots();
    }

    public void startCrafting(){
        crafting = true;
    }

    public void endCrafting(){
        crafting = false;
    }

    public ItemStack extractLatestItem(){
        if (itemsInside <= 0){
            itemsInside = 0;
            return ItemStack.EMPTY;
        }
        ItemStack extracted = inventory.extractItem(itemsInside - 1, 1, false);
        inventory.setStackInSlot(itemsInside - 1 , ItemStack.EMPTY);
        itemsInside--;
        //System.out.println("extracted: " + extracted + " inside: " + itemsInside);
        return extracted;
    }


    public int itemsInside;
    private float rotation;
    private boolean crafting;
    public AncientAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ANCIENT_ALTAR_BE.get(), pos, blockState);
        itemsInside = 0;
        crafting = false;
    }

    public void clearAllContents(){
        for (int slot = 0; slot < inventory.getSlots(); slot++){
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
        }
        itemsInside = 0;
    }

    public boolean isEmpty(){
        return itemsInside == 0;
    }

    public float getRenderingRotation(){
        rotation += 0.5F;
        rotation %= 360;
        return rotation;
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int slot = 0; slot < inventory.getSlots(); slot++){
            inv.setItem(slot, this.inventory.getStackInSlot(slot));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("itemsInside", itemsInside);
        tag.putBoolean("crafting", crafting);
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

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        itemsInside = tag.getInt("itemsInside");
        crafting = tag.getBoolean("crafting");
    }
}
