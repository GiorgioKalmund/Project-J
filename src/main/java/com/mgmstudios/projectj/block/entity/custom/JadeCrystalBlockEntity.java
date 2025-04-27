package com.mgmstudios.projectj.block.entity.custom;

import java.util.List;
import java.util.Random;

import com.mgmstudios.projectj.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;



public class JadeCrystalBlockEntity extends BlockEntity {

    private static final List<Item> validItems = List.of(Items.DIAMOND, Items.EMERALD, Items.GOLD_INGOT, Items.IRON_INGOT, Items.NETHERITE_INGOT);
    int randomYRotation = (int) (Math.random() * 360);
    int randomXRotation = (int) (Math.random() * 360);

    public JadeCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.JADE_CRYSTAL_BE.get(), pos, state);
        Item item = getRandomItem(pos);
        System.out.println(inventory.getStackInSlot(0));
        inventory.setStackInSlot(0, new ItemStack(item));
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            super.onContentsChanged(slot);
        }

        @Override
        public int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

    public ItemStackHandler getInventory() {
        return inventory;
    }

    private int randomSeed(BlockPos pos){
        int bigNumber = pos.getX() + pos.getY() + pos.getZ();
        Random random = new Random(bigNumber);
        return random.nextInt(0, validItems.size());
    }

    public ItemStack getItemStack() {
        return inventory.getStackInSlot(0);
    }

    public Item getRandomItem(BlockPos pos) {
        return validItems.get(randomSeed(pos));
    }

    public int getRandomYRotation() {
        return randomYRotation;
    }
    public int getRandomXRotation() {
        return randomXRotation;
    }

    public void drops(Level level, BlockPos worldPosition) {
        if (level != null && !level.isClientSide) {
            ItemStack itemStack = inventory.getStackInSlot(0);
            if (!itemStack.isEmpty()) {
                Block.popResource(level, worldPosition, itemStack);
                inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }
}