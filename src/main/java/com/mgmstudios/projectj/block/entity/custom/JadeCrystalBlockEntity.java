package com.mgmstudios.projectj.block.entity.custom;

import java.util.List;

import com.mgmstudios.projectj.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;



public class JadeCrystalBlockEntity extends BlockEntity {

    List<Item> validItems = List.of(Items.DIAMOND, Items.EMERALD, Items.GOLD_INGOT, Items.IRON_INGOT, Items.NETHERITE_INGOT);
    int randomYRotation = (int) (Math.random() * 360);
    int randomXRotation = (int) (Math.random() * 360);
    Item chosenItem;


    public JadeCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.JADE_CRYSTAL_BE.get(), pos, state);
        chosenItem = getRandomItem();
        inventory.setStackInSlot(0, new ItemStack(chosenItem));
    }

    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

    public ItemStack getItemStack() {
        return inventory.getStackInSlot(0);
    }

    public Item getRandomItem() {
        int randomIndex = (int) (Math.random() * validItems.size());
        return validItems.get(randomIndex);
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
}