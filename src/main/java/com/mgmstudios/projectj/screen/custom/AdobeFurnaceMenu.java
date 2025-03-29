package com.mgmstudios.projectj.screen.custom;

import com.mgmstudios.projectj.recipe.ModRecipeTypes;
import com.mgmstudios.projectj.screen.ModMenuTypes;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.RecipePropertySet;

public class AdobeFurnaceMenu extends AbstractFurnaceMenu {


    public AdobeFurnaceMenu(int containerId, Inventory playerInventory) {
        super(ModMenuTypes.ADOBE_FURNACE_MENU.get(), ModRecipeTypes.ADOBE_FURNACE_TYPE.get(), RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, playerInventory);
    }

    public AdobeFurnaceMenu(int containerId, Inventory playerInventory, Container adobeFurnaceContainer, ContainerData adobeFurnaceData) {
        super(ModMenuTypes.ADOBE_FURNACE_MENU.get(), ModRecipeTypes.ADOBE_FURNACE_TYPE.get(), RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, playerInventory, adobeFurnaceContainer, adobeFurnaceData);
    }
}
