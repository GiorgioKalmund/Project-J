package com.mgmstudios.projectj.util;

import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.Optional;

public class ItemLookup {

    public static ItemStack getStack(ResourceLocation resourceLocation) {
        Optional<Holder.Reference<Item>> itemOptional = BuiltInRegistries.ITEM.get(resourceLocation);
        Item item = null;
        if (itemOptional.isPresent()){
            item = itemOptional.get().value();
        }

        if (item == null || item.equals(Items.AIR)) {
            return ItemStack.EMPTY;
        }

        return new ItemStack(item);
    }

    public static ResourceLocation getResourceLocation(ItemLike itemLike) {
        return getResourceLocation(itemLike.asItem());
    }

    public static ResourceLocation getResourceLocation(Item itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike);
    }

    public static ItemStack getStack(String combinedString) {
        return getStack(ResourceLocation.tryParse(combinedString));
    }
}