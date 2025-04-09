package com.mgmstudios.projectj.recipe.metate;

import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;

import static com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity.ANCIENT_ALTAR_INVENTORY_SIZE;

public class MetateRecipeSerializer implements RecipeSerializer<MetateRecipe> {
    public static final MapCodec<MetateRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(MetateRecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(MetateRecipe::getResult)
    ).apply(inst, MetateRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MetateRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, MetateRecipe::getInputItem,
                    ItemStack.STREAM_CODEC, MetateRecipe::getResult,
                    MetateRecipe::new
            );


    @Override
    public MapCodec<MetateRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, MetateRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}

