package com.mgmstudios.projectj.screen.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.AdobeFurnaceBlockEntity;
import com.mgmstudios.projectj.screen.ModMenuTypes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AdobeFurnaceMenu extends AbstractFurnaceMenu {


    public AdobeFurnaceMenu(int containerId, Inventory playerInventory) {
        super(MenuType.SMOKER, RecipeType.SMOKING, RecipePropertySet.SMOKER_INPUT, RecipeBookType.SMOKER, containerId, playerInventory);
    }

    public AdobeFurnaceMenu(int containerId, Inventory playerInventory, Container smokerContainer, ContainerData smokerData) {
        super(MenuType.SMOKER, RecipeType.SMOKING, RecipePropertySet.SMOKER_INPUT, RecipeBookType.SMOKER, containerId, playerInventory, smokerContainer, smokerData);
    }
}
