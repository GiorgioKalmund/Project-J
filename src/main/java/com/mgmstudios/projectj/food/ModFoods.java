package com.mgmstudios.projectj.food;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MAIZE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.6F).build();
    public static final FoodProperties MAIZE_MASH = stew(6).alwaysEdible().build();
    public static final FoodProperties CHILI = new FoodProperties.Builder().nutrition(2).saturationModifier(0.6F).alwaysEdible().build();

    private static FoodProperties.Builder stew(int nutrition) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(0.6F);
    }
}
