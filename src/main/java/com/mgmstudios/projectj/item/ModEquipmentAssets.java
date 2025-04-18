package com.mgmstudios.projectj.item;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class ModEquipmentAssets {
    public static ResourceKey<EquipmentAsset> JADE = createId("jade");

    static ResourceKey<EquipmentAsset> createId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
    }
}
