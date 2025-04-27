package com.mgmstudios.projectj.item;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.food.ModConsumables;
import com.mgmstudios.projectj.food.ModFoods;
import com.mgmstudios.projectj.item.custom.CustomInstrumentItem;
import com.mgmstudios.projectj.item.custom.FireStarterItem;
import com.mgmstudios.projectj.item.custom.LittleManVoodoo;
import com.mgmstudios.projectj.item.custom.MagnetItem;
import com.mgmstudios.projectj.item.custom.MagnifyingGlassItem;
import com.mgmstudios.projectj.item.custom.ObsidianArrowItem;
import com.mgmstudios.projectj.item.custom.OlmecHeadItem;
import static com.mgmstudios.projectj.item.custom.OlmecHeadItem.humanoidProperties;
import static com.mgmstudios.projectj.item.custom.OlmecHeadItem.humanoidPropertiesWithCustomAsset;
import com.mgmstudios.projectj.item.custom.PaxelItem;
import com.mgmstudios.projectj.item.custom.QuestBook;
import com.mgmstudios.projectj.item.custom.SacrificialDagger;
import com.mgmstudios.projectj.item.custom.TeleportationKeyItem;
import com.mgmstudios.projectj.item.custom.TrowelItem;
import com.mgmstudios.projectj.item.custom.VoodooCatcherItem;
import com.mgmstudios.projectj.sound.ModSounds;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProjectJ.MOD_ID);

    public static final DeferredItem<Item> RAW_JADE = register("raw_jade", new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> JADE = register("jade", new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> PYRITE_INGOT = register("pyrite_ingot");

    public static final DeferredItem<Item> RAW_PYRITE = register("raw_pyrite");

    public static final DeferredItem<Item> LIQUID_PYRITE_BUCKET = register("liquid_pyrite_bucket", (properties) -> new BucketItem(ModFluids.PYRITE.get(), properties.stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final DeferredItem<Item> MACUAHUITL = registerPaxelItem("macuahuitl", new Item.Properties().fireResistant().rarity(Rarity.EPIC));

    public static final DeferredItem<Item> SERPENTINITE_ROD = register("serpentinite_rod");

    public static final DeferredItem<Item> OBSIDIAN_TOOTH = register("obsidian_tooth");

    public static final DeferredItem<Item> SACRIFICIAL_DAGGER = register("sacrificial_dagger", properties -> new SacrificialDagger(30F, -3.6F, properties.rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> TROWEL = register("trowel", TrowelItem::new);

    public static final DeferredItem<Item> SUN_ARMOR_HELMET = registerCustomArmorItemAndAsset("sun_crown", ModArmorMaterials.SUN_ARMOR_MATERIAL, ArmorType.HELMET, new Item.Properties());

    public static final DeferredItem<Item> MAGNIFYING_GLASS = register("magnifying_glass", MagnifyingGlassItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> FIRE_STARTER = register("fire_starter", FireStarterItem::new, new Item.Properties().stacksTo(1).durability(128));

    public static final DeferredItem<Item> CRUDE_SACRIFICE_BOWL = register("crude_sacrifice_bowl", new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> FILLED_CRUDE_SACRIFICE_BOWL = register("filled_crude_sacrifice_bowl", () -> new Item.Properties().stacksTo(1).food(Foods.RABBIT_STEW).usingConvertsTo(ModItems.CRUDE_SACRIFICE_BOWL.get()));

    public static final DeferredItem<Item> JADE_HELMET = registerCustomArmorItem("jade_helmet", ModArmorMaterials.JADE_MATERIAL, ArmorType.HELMET);

    public static final DeferredItem<Item> JADE_CHESTPLATE = registerCustomArmorItem("jade_chestplate", ModArmorMaterials.JADE_MATERIAL, ArmorType.CHESTPLATE);

    public static final DeferredItem<Item> JADE_LEGGINGS = registerCustomArmorItem("jade_leggings", ModArmorMaterials.JADE_MATERIAL, ArmorType.LEGGINGS);

    public static final DeferredItem<Item> JADE_BOOTS = registerCustomArmorItem("jade_boots", ModArmorMaterials.JADE_MATERIAL, ArmorType.BOOTS);

    public static final DeferredItem<Item> TELEPORTATION_CORE = register("teleportation_core");

    public static final DeferredItem<Item> TELEPORTATION_KEY = register("teleportation_key", TeleportationKeyItem::new , new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> LITTLE_MAN_SPAWN_EGG = register("little_man_spawn_egg", (properties) -> new SpawnEggItem(ModEntities.LITTLE_MAN_ENTITY.get(), properties));

    public static final DeferredItem<Item> LITTLE_MAN_VOODOO = register("little_man_voodoo", LittleManVoodoo::new);

    public static final DeferredItem<Item> VOODOO_CATCHER = register("voodoo_catcher", VoodooCatcherItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> PYRITE_MAGNET = register("pyrite_magnet", (properties) -> new MagnetItem(properties.stacksTo(1), 5, 0.3F));

    public static final DeferredItem<Item> JADE_MAGNET = register("jade_magnet", (properties) -> new MagnetItem(properties.stacksTo(1), 8, 0.5F));

    public static final DeferredItem<Item> MAIZE_SEEDS = register("maize_seeds", (properties) -> new BlockItem(ModBlocks.MAIZE_CROP.get(), properties.useItemDescriptionPrefix()));

    public static final DeferredItem<Item> MAIZE = register("maize", new Item.Properties().food(ModFoods.MAIZE));

    public static final DeferredItem<Item> MAIZE_MASH = register("maize_mash", new Item.Properties().food(ModFoods.MAIZE_MASH).usingConvertsTo(Items.BOWL));

    public static final DeferredItem<Item> CHILI_SEEDS = register("chili_seeds", (properties) -> new BlockItem(ModBlocks.CHILI_BUSH.get(), properties.useItemDescriptionPrefix()));

    public static final DeferredItem<Item> CHILI = register("chili", new Item.Properties().food(ModFoods.CHILI, ModConsumables.CHILI_PEPPER));

    public static final DeferredItem<Item> CHILI_CON_CARNE = register("chili_con_carne", new Item.Properties().food(ModFoods.CHILI_CON_CARNE).usingConvertsTo(Items.BOWL));

    public static final DeferredItem<Item> STONE_MANO = register("stone_mano");

    public static final DeferredItem<Item> LITTLE_KING_SPAWN_EGG = register("little_king_spawn_egg", (properties) -> new SpawnEggItem(ModEntities.LITTLE_KING_ENTITY.get(), properties));

    public static final DeferredItem<Item> LITTLE_KING_VOODOO = register("little_king_voodoo", new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(16));

    public static final DeferredItem<Item> QUETZAL_FEATHER = register("quetzal_feather");

    public static final DeferredItem<Item> QUETZAL_SPAWN_EGG = register("quetzal_spawn_egg", (properties) -> new SpawnEggItem(ModEntities.QUETZAL_ENTITY.get(), properties));

    public static final DeferredItem<Item> QUETZAL_EGG = register("quetzal_egg", EggItem::new, new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> QUEST_BOOK = register("quest_book", (properties) -> new QuestBook("Ancient Codex", "Project J Team", createQuestBookPages(10), properties.stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> CHIMALLI_SHIELD = register("chimalli_shield", ShieldItem::new, new Item.Properties().durability(336));

    public static final DeferredItem<Item> OBSIDIAN_ARROW = register("obsidian_arrow", ObsidianArrowItem::new);

    public static final DeferredItem<Item> DEATH_WHISTLE = register("death_whistle", (properties) -> new CustomInstrumentItem(ModSounds.DEATH_WHISTLE_SOUND.value(), properties.stacksTo(1).rarity(Rarity.EPIC),3.0F));

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

    public static DeferredItem<Item> registerCustomArmorItemAndAsset(String name, ArmorMaterial material, ArmorType armorType, Item.Properties properties){
        return ITEMS.register(name, key ->new Item(humanoidPropertiesWithCustomAsset(material, properties.setId(ResourceKey.create(Registries.ITEM, key)).durability(armorType.getDurability(material.durability())), armorType)));
    }
    public static DeferredItem<Item> registerCustomArmorItemAndAsset(String name, ArmorMaterial material, ArmorType armorType){
        return ITEMS.register(name, key ->new Item(humanoidPropertiesWithCustomAsset(material, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, key)).durability(armorType.getDurability(material.durability())), armorType)));
    }
    public static DeferredItem<Item> registerCustomArmorItem(String name, ArmorMaterial material, ArmorType armorType){
        return ITEMS.register(name, key ->new Item(humanoidProperties(material, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, key)).durability(armorType.getDurability(material.durability())), armorType)));
    }

    public static DeferredItem<Item> registerCustomArmorItem(String name, ArmorMaterial material, ArmorType armorType, ResourceLocation assetLocation){
        return ITEMS.register(name, key ->new Item(humanoidProperties(material, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, key)).durability(armorType.getDurability(material.durability())), armorType)));
    }

    public static DeferredItem<Item> registerSwordItem(String name, ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties properties){
        return ITEMS.register(name, key -> new SwordItem(material, attackDamage, attackSpeed, properties.setId(ResourceKey.create(Registries.ITEM, key))));
    }

    public static DeferredItem<Item> registerBucketItem(String name, FlowingFluid fluid){
        return ITEMS.register(name, key -> new BucketItem(fluid, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET).setId(ResourceKey.create(Registries.ITEM, key))));
    }

    public static DeferredItem<Item> register(String name, Function<Item.Properties, Item> function, Item.Properties properties){
        return ITEMS.register(name, key -> function.apply(properties.setId(ResourceKey.create(Registries.ITEM, key))));
    }

    public static DeferredItem<Item> register(String name, Item.Properties properties){
        return register(name, Item::new, properties);
    }

    private static ResourceKey<Item> createKey(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
    }

    private static <T extends Item> DeferredItem<Item> register(String name) {
        return register(name, Item::new);
    }

    private static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> builder) {
        return baseRegister(name, createKey(name), builder, Item.Properties::new);
    }

    private static <T extends Item> DeferredItem<Item> register(String name, Supplier<Item.Properties> properties) {
        return register(name, Item::new, properties);
    }

    private static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> builder, Supplier<Item.Properties> properties) {
        return baseRegister(name, createKey(name), builder, properties);
    }

    private static <T extends Item> DeferredItem<T> baseRegister(String name, ResourceKey<Item> key, Function<Item.Properties, T> builder, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> builder.apply(properties.get().setId(key)));
    }


    private static java.util.List<net.minecraft.server.network.Filterable<net.minecraft.network.chat.Component>> createQuestBookPages(int pageCount){
        java.util.List<net.minecraft.server.network.Filterable<net.minecraft.network.chat.Component>> pages = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            pages.add(Filterable.passThrough(Component.translatable("quest_book.projectj.page" + i)));
        }
        return pages;
    }
}