package com.mgmstudios.projectj.item;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.neoforged.neoforge.common.Tags;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final ArmorMaterial OLMEC_HEAD_MATERIAL = new ArmorMaterial(
            // The durability multiplier of the armor material.
            // ArmorType have different unit durabilities that the multiplier is applied to:
            // - HELMET: 11
            // - CHESTPLATE: 16
            // - LEGGINGS: 15
            // - BOOTS: 13
            // - BODY: 16
            15,
            // Determines the defense value (or the number of half-armors on the bar).
            // Based on ArmorType.
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 3);
                map.put(ArmorType.HELMET, 8);
                map.put(ArmorType.BODY, 11);
            }),
            // Determines the enchantability of the armor. This represents how good the enchantments on this armor will be.
            // Gold uses 25;
            20,
            // Determines the sound played when equipping this armor.
            // This is wrapped with a Holder.
            SoundEvents.ARMOR_EQUIP_GENERIC,
            // Returns the toughness value of the armor. The toughness value is an additional value included in
            // damage calculation, for more information, refer to the Minecraft Wiki's article on armor mechanics:
            // https://minecraft.wiki/w/Armor#Armor_toughness
            // Only diamond and netherite have values greater than 0 here, so we just return 0.
            3.0F,
            // Returns the knockback resistance value of the armor. While wearing this armor, the player is
            // immune to knockback to some degree. If the player has a total knockback resistance value of 1 or greater
            // from all armor pieces combined, they will not take any knockback at all.
            // Only netherite has values greater than 0 here, so we just return 0.
            0.1F,
            // The tag that determines what items can repair this armor.
            ModTags.Items.JADE,
            // The resource key of the EquipmentClientInfo JSON discussed below
            // Points to assets/examplemod/equipment/copper.json
            ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "olmec_head"))
    );
}
