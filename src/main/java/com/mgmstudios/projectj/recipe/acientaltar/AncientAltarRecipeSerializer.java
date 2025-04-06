package com.mgmstudios.projectj.recipe.acientaltar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.fluids.FluidStack;

import static com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity.ANCIENT_ALTAR_INVENTORY_SIZE;

public class AncientAltarRecipeSerializer implements RecipeSerializer<AncientAltarRecipe> {
    public static final MapCodec<AncientAltarRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            FluidStack.CODEC.fieldOf("fluidStack").forGetter(AncientAltarRecipe::getFluidStack),
            Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, ANCIENT_ALTAR_INVENTORY_SIZE))
                    .fieldOf("ingredients").forGetter(AncientAltarRecipe::getInputItems),
            ItemStack.CODEC.fieldOf("result").forGetter(AncientAltarRecipe::getResult)
    ).apply(inst, AncientAltarRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AncientAltarRecipe> STREAM_CODEC = StreamCodec.composite(
            FluidStack.STREAM_CODEC, AncientAltarRecipe::getFluidStack,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), AncientAltarRecipe::getInputItems,
            ItemStack.STREAM_CODEC, AncientAltarRecipe::getResult,
            AncientAltarRecipe::new
    );


    @Override
    public MapCodec<AncientAltarRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, AncientAltarRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}

