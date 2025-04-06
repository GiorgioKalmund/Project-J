package com.mgmstudios.projectj.recipe.acientaltar;

import com.mgmstudios.projectj.fluid.ModFluids;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AncientAltarRecipeBuilder implements RecipeBuilder {

    private final HolderGetter<Item> items;
    private final RecipeCategory category;
    private final ItemStack result;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private FluidStack fluid;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public AncientAltarRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemStack result, FluidStack fluid) {
        this.result = result;
        this.category = category;
        this.items = items;
        this.fluid = fluid;
    }

    public AncientAltarRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemStack result) {
        this.result = result;
        this.category = category;
        this.items = items;
        this.fluid = new FluidStack(Fluids.WATER, 1);
    }

    public static AncientAltarRecipeBuilder regular(HolderGetter<Item> items, RecipeCategory category, ItemStack result) {
        return new AncientAltarRecipeBuilder(items, category, result);
    }


    public static AncientAltarRecipeBuilder regular(HolderGetter<Item> items, RecipeCategory category, ItemLike result) {
        return regular(items, category, result, 1);
    }

    public static AncientAltarRecipeBuilder regular(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        return new AncientAltarRecipeBuilder(items, category, result.asItem().getDefaultInstance().copyWithCount(count));
    }

    public static AncientAltarRecipeBuilder regularWithPyrite(HolderGetter<Item> items, RecipeCategory category, ItemStack result) {
        return new AncientAltarRecipeBuilder(items, category, result, new FluidStack(ModFluids.FLOWING_PYRITE.get(), 1000));
    }

    public static AncientAltarRecipeBuilder regularWithPyrite(HolderGetter<Item> items, RecipeCategory category, ItemLike result) {
        return regularWithPyrite(items, category, result, 1);
    }

    public static AncientAltarRecipeBuilder regularWithPyrite(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        return new AncientAltarRecipeBuilder(items, category, result.asItem().getDefaultInstance().copyWithCount(count), new FluidStack(ModFluids.FLOWING_PYRITE.get(), 1000));
    }

    public AncientAltarRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(this.items.getOrThrow(tag)));
    }

    public AncientAltarRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    /**
     * Adds the given ingredient multiple times.
     */
    public AncientAltarRecipeBuilder requires(ItemLike item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.requires(Ingredient.of(item));
        }

        return this;
    }
    public AncientAltarRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public AncientAltarRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredient);
        }
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public AncientAltarRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<net.minecraft.world.item.crafting.Recipe<?>> recipeKey) {
        this.ensureValid(recipeKey);

        // Build the advancement for unlocking the recipe
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", net.minecraft.advancements.critereon.RecipeUnlockedTrigger.unlocked(recipeKey))
                .rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(recipeKey))
                .requirements(net.minecraft.advancements.AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        // Create the recipe instance
        AncientAltarRecipe recipe;
        if (fluid.isEmpty()){
            recipe = new AncientAltarRecipe(ingredients, result);
        } else {
            recipe = new AncientAltarRecipe(fluid, ingredients, result);
        }

        // Save the recipe and its advancement
        output.accept(recipeKey, recipe, advancement.build(recipeKey.location().withPrefix("recipes/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> recipe) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipe.location());
        }
    }
}
