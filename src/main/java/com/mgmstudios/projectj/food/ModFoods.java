package com.mgmstudios.projectj.food;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MAIZE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.6F).build();
    public static final FoodProperties MAIZE_MASH = stew(3).alwaysEdible().build();
    public static final FoodProperties CHILI_CON_CARNE  = stew(8).alwaysEdible().build();
    public static final FoodProperties CHILI = new FoodProperties.Builder().nutrition(2).saturationModifier(0.6F).alwaysEdible().build();
    public static final FoodProperties FLESH = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6F).build();

    private static FoodProperties.Builder stew(int nutrition) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(0.6F);
    }
}
