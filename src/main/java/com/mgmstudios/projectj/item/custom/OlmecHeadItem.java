package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class OlmecHeadItem extends BlockItem {
    private final Holder<MobEffect> effect;
    public OlmecHeadItem(Block block, Properties properties, Holder<MobEffect> effect) {
        super(block, humanoidProperties(ModArmorMaterials.OLMEC_HEAD_MATERIAL, properties, ArmorType.HELMET));
        this.effect = effect;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof ServerPlayer player && !level.isClientSide() && selfIsInHelmetSlot(player)) {
            player.addEffect(new MobEffectInstance(effect, 2 * 20, 2, true, false,true));
        }
    }

    public static Item.Properties humanoidProperties(ArmorMaterial material, Item.Properties properties, ArmorType armorType) {
        return properties.attributes(createAttributes(material, armorType)).enchantable(material.enchantmentValue()).component(DataComponents.EQUIPPABLE, Equippable.builder(armorType.getSlot()).setEquipSound(material.equipSound()).build());
    }

    private static ItemAttributeModifiers createAttributes(ArmorMaterial material, ArmorType armorType) {
        int i = material.defense().getOrDefault(armorType, 0);
        ItemAttributeModifiers.Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(armorType.getSlot());
        ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + armorType.getName());
        itemattributemodifiers$builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
        itemattributemodifiers$builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, material.toughness(), AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
        if (material.knockbackResistance()> 0.0F) {
            itemattributemodifiers$builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, material.knockbackResistance(), AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
        }

        return itemattributemodifiers$builder.build();
    }

    // Maybe there is a better way of checking if the current head item is in the head, but this suffices
    private boolean selfIsInHelmetSlot(Player player){
        return player.getInventory().getArmor(3).getItem() == this;
    }
}


