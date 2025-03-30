package com.mgmstudios.projectj.item;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.custom.OlmecHeadItem;
import com.mgmstudios.projectj.item.custom.PaxelItem;
import com.mgmstudios.projectj.item.custom.TrowelItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;

import static com.mgmstudios.projectj.item.custom.OlmecHeadItem.humanoidProperties;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProjectJ.MOD_ID);

    public static final DeferredItem<Item> RAW_JADE = register("raw_jade",
            Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> JADE = ITEMS.registerItem("jade",
            Item::new, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> MACUAHUITL = registerPaxelItem("macuahuitl",
          new Item.Properties()
                  .fireResistant()
                  .rarity(Rarity.EPIC)
    );

    public static final DeferredItem<Item> SERPENTINITE_ROD = ITEMS.registerItem("serpentinite_rod", Item::new);

    public static final DeferredItem<Item> OBSIDIAN_TOOTH = ITEMS.registerItem("obsidian_tooth", Item::new);

    public static final DeferredItem<Item> SACRIFICIAL_DAGGER = registerSwordItem("sacrificial_dagger", ModToolMaterials.SACRIFICIAL_DAGGER_MATERIAL,
            30F, -3.6F, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> TROWEL = ITEMS.registerItem("trowel", TrowelItem::new);

    public static final DeferredItem<Item> SUN_ARMOR_HELMET = registerCustomArmorItem("sun_crown", ModArmorMaterials.SUN_ARMOR_MATERIAL, ArmorType.HELMET, new Item.Properties());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static DeferredItem<Item> registerOlmecHeadItem(DeferredBlock<Block> block, Item.Properties properties, Holder<MobEffect> effect){
        String name = (block.unwrapKey().orElseThrow()).location().getPath();
        Objects.requireNonNull(block);

        return ITEMS.register(name,
                key -> new OlmecHeadItem(
                        block.get(),
                        properties.setId(ResourceKey.create(Registries.ITEM, key)),
                        effect)
        );
    }

    public static DeferredItem<Item> registerPaxelItem(String name, Item.Properties properties){
        return ITEMS.register(name, key -> new PaxelItem(3F, -2.4F, properties.setId(ResourceKey.create(Registries.ITEM, key))));
    }

    public static DeferredItem<Item> registerCustomArmorItem(String name, ArmorMaterial material, ArmorType armorType, Item.Properties properties){
        return ITEMS.register(name, key -> new ArmorItem(material, armorType, properties.setId(ResourceKey.create(Registries.ITEM, key))));
    }

    public static DeferredItem<Item> registerSwordItem(String name, ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties properties){
        return ITEMS.register(name, key -> new SwordItem(material, attackDamage, attackSpeed, properties.setId(ResourceKey.create(Registries.ITEM, key))));
    }

    public static DeferredItem<Item> register(String name, Function<Item.Properties, Item> function, Item.Properties properties){
        return ITEMS.register(name, key -> function.apply(properties.setId(ResourceKey.create(Registries.ITEM, key))));
    }
}