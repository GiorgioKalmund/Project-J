package com.mgmstudios.projectj.recipe.metate;

import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarRecipe;
import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarRecipeBuilder;
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
import net.minecraft.world.item.Items;
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

import static com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity.ANCIENT_ALTAR_INVENTORY_SIZE;

public class MetateRecipeBuilder implements RecipeBuilder {

    private final HolderGetter<Item> items;
    private final RecipeCategory category;
    private final ItemStack result;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private boolean showNotification = true;

    public MetateRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemStack result) {
        this.result = result;
        this.category = category;
        this.items = items;
    }

    public static MetateRecipeBuilder regular(HolderGetter<Item> items, RecipeCategory category, ItemLike input, ItemLike result) {
        return regular(items, category, input, result, 1);
    }

    public static MetateRecipeBuilder regular(HolderGetter<Item> items, RecipeCategory category, ItemLike input, ItemLike result, int count) {
        return new MetateRecipeBuilder(items, category, result.asItem().getDefaultInstance().copyWithCount(count)).requires(input);
    }

    public static MetateRecipeBuilder regular(HolderGetter<Item> items, RecipeCategory category, ItemLike result) {
        return regular(items, category, result, 1);
    }

    public static MetateRecipeBuilder regular(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        return new MetateRecipeBuilder(items, category, result.asItem().getDefaultInstance().copyWithCount(count));
    }

    public MetateRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(this.items.getOrThrow(tag)));
    }

    public MetateRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public MetateRecipeBuilder requires(ItemLike item) {
        return this.requires(Ingredient.of(item), 1);
    }

    public MetateRecipeBuilder requires(Ingredient ingredient, int quantity) {
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
    public MetateRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    public MetateRecipeBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> recipeKey) {
        this.ensureValid(recipeKey);

        // Build the advancement for unlocking the recipe
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", net.minecraft.advancements.critereon.RecipeUnlockedTrigger.unlocked(recipeKey))
                .rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(recipeKey))
                .requirements(net.minecraft.advancements.AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        // Create the recipe instance
        MetateRecipe recipe = new MetateRecipe(ingredients.get(0), result);

        // Save the recipe and its advancement
        output.accept(recipeKey, recipe, advancement.build(recipeKey.location().withPrefix("recipes/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> recipe) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipe.location());
        } else if (ingredients.size() > 1){
            throw new IllegalStateException(recipe.location() + " has too many ingredients.");
        }
    }
}
