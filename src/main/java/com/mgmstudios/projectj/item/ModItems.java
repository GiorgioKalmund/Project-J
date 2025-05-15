package com.mgmstudios.projectj.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.food.ModConsumables;
import com.mgmstudios.projectj.food.ModFoods;
import com.mgmstudios.projectj.item.custom.*;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.*;
import static com.mgmstudios.projectj.item.custom.OlmecHeadItem.humanoidProperties;
import static com.mgmstudios.projectj.item.custom.OlmecHeadItem.humanoidPropertiesWithCustomAsset;
import static net.minecraft.core.component.DataComponents.GLIDER;

import com.mgmstudios.projectj.item.custom.armor.AwakenedSunArmorItem;
import com.mgmstudios.projectj.item.custom.armor.JadeArmorItem;
import com.mgmstudios.projectj.item.custom.armor.SunArmorItem;
import com.mgmstudios.projectj.item.custom.socket.SocketGemItem;
import com.mgmstudios.projectj.item.custom.socket.SocketItem;
import com.mgmstudios.projectj.item.custom.socket.SocketShieldItem;
import com.mgmstudios.projectj.sound.ModSounds;

import com.mgmstudios.projectj.util.Socket;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.lang3.function.TriFunction;
import oshi.jna.platform.windows.NtDll;

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

    public static final DeferredItem<Item> SUN_ARMOR_HELMET = registerArmorItem("sun_armor_helmet", ModArmorMaterials.SUN_ARMOR_MATERIAL, ArmorType.HELMET, SunArmorItem::new);

    public static final DeferredItem<Item> SUN_ARMOR_CHESTPLATE = registerArmorItem("sun_armor_chestplate", ModArmorMaterials.SUN_ARMOR_MATERIAL, ArmorType.CHESTPLATE, SunArmorItem::new);

    public static final DeferredItem<Item> SUN_ARMOR_LEGGINGS = registerArmorItem("sun_armor_leggings", ModArmorMaterials.SUN_ARMOR_MATERIAL, ArmorType.LEGGINGS, SunArmorItem::new);

    public static final DeferredItem<Item> SUN_ARMOR_BOOTS = registerArmorItem("sun_armor_boots", ModArmorMaterials.SUN_ARMOR_MATERIAL, ArmorType.BOOTS, SunArmorItem::new);

    public static final DeferredItem<Item> AWAKENED_SUN_ARMOR_HELMET = registerArmorItem("awakened_sun_armor_helmet", ModArmorMaterials.AWAKENED_SUN_ARMOR_MATERIAL, ArmorType.HELMET,(material, armorType, properties) -> new AwakenedSunArmorItem(material, armorType, properties, Socket.empty()) , new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> AWAKENED_SUN_ARMOR_CHESTPLATE = registerArmorItem("awakened_sun_armor_chestplate", ModArmorMaterials.AWAKENED_SUN_ARMOR_MATERIAL, ArmorType.CHESTPLATE, (material, armorType, properties) -> new AwakenedSunArmorItem(material, armorType, properties, Socket.glider(), Socket.empty()) , new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> AWAKENED_SUN_ARMOR_LEGGINGS = registerArmorItem("awakened_sun_armor_leggings", ModArmorMaterials.AWAKENED_SUN_ARMOR_MATERIAL, ArmorType.LEGGINGS, (material, armorType, properties) -> new AwakenedSunArmorItem(material, armorType, properties, Socket.empty()), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> AWAKENED_SUN_ARMOR_BOOTS = registerArmorItem("awakened_sun_armor_boots", ModArmorMaterials.AWAKENED_SUN_ARMOR_MATERIAL, ArmorType.BOOTS,  (material, armorType, properties) -> new AwakenedSunArmorItem(material, armorType, properties, Socket.empty()), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> MAGNIFYING_GLASS = register("magnifying_glass", MagnifyingGlassItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> FIRE_STARTER = register("fire_starter", FireStarterItem::new, new Item.Properties().stacksTo(1).durability(128));

    public static final DeferredItem<Item> CRUDE_SACRIFICE_BOWL = register("crude_sacrifice_bowl", new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> FILLED_CRUDE_SACRIFICE_BOWL = register("filled_crude_sacrifice_bowl", () -> new Item.Properties().stacksTo(1).food(Foods.RABBIT_STEW).usingConvertsTo(ModItems.CRUDE_SACRIFICE_BOWL.get()));

    public static final DeferredItem<Item> JADE_HELMET = registerArmorItem("jade_helmet", ModArmorMaterials.JADE_MATERIAL, ArmorType.HELMET, JadeArmorItem::new);

    public static final DeferredItem<Item> JADE_CHESTPLATE = registerArmorItem("jade_chestplate", ModArmorMaterials.JADE_MATERIAL, ArmorType.CHESTPLATE, JadeArmorItem::new);

    public static final DeferredItem<Item> JADE_LEGGINGS = registerArmorItem("jade_leggings", ModArmorMaterials.JADE_MATERIAL, ArmorType.LEGGINGS, JadeArmorItem::new);

    public static final DeferredItem<Item> JADE_BOOTS = registerArmorItem("jade_boots", ModArmorMaterials.JADE_MATERIAL, ArmorType.BOOTS, JadeArmorItem::new);

    public static final DeferredItem<Item> TELEPORTATION_CORE = register("teleportation_core");

    public static final DeferredItem<Item> TELEPORTATION_KEY = register("teleportation_key", TeleportationKeyItem::new , new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> LITTLE_MAN_SPAWN_EGG = register("little_man_spawn_egg", (properties) -> new SpawnEggItem(ModEntities.LITTLE_MAN_ENTITY.get(), properties));

    public static final DeferredItem<Item> LITTLE_MAN_VOODOO = register("little_man_voodoo", (properties) -> new SocketItem(properties, Socket.emptySockets(2)){
        @Override
        public List<DataComponentType<?>> getExcludedTypes() {
            List<DataComponentType<?>> blackList = new ArrayList<>(super.getExcludedTypes());
            blackList.add(GLIDER);
            return blackList;
        }
    }, new Item.Properties().stacksTo(1));

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

    public static final DeferredItem<Item> QUEST_BOOK = register("quest_book", (properties) -> new QuestBook("Ancient Codex", "Project J Team", List.of(), properties.stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> CHIMALLI_SHIELD = register("chimalli_shield", (properties) -> new SocketShieldItem(properties, Socket.empty()), new Item.Properties().durability(336));

    public static final DeferredItem<Item> OBSIDIAN_ARROW = register("obsidian_arrow", ObsidianArrowItem::new);

    public static final DeferredItem<Item> DEATH_WHISTLE = register("death_whistle", (properties) -> new CustomInstrumentItem(ModSounds.DEATH_WHISTLE_SOUND.value(), properties.stacksTo(1).rarity(Rarity.EPIC),3.0F));

    public static final DeferredItem<Item> HATCHET = register("hatchet", (properties) -> new HatchetItem(ToolMaterial.IRON, 6, -3.1F, properties));

    public static final DeferredItem<Item> RUNNER_SPAWN_EGG = register("runner_spawn_egg", (properties) -> new SpawnEggItem(ModEntities.RUNNER_ENTITY.get(), properties));

    public static final DeferredItem<Item> FLESH = register("flesh", new Item.Properties().food(ModFoods.FLESH));

    public static final DeferredItem<Item> GLIDER_SOCKET = register("glider_socket", (properties) -> new SocketGemItem(properties, Socket.glider()));

    public static final DeferredItem<Item> PACIFYING_SOCKET = register("pacifying_socket", (properties) -> new SocketGemItem(properties, Socket.zombiePacifying()));

    public static final DeferredItem<Item> REMOVE_AI_SOCKET = register("remove_ai_socket", (properties) -> new SocketGemItem(properties, Socket.removeAi()));

    public static final DeferredItem<Item> GIVE_AI_SOCKET = register("give_ai_socket", (properties) -> new SocketGemItem(properties, Socket.giveAi()));

    public static final DeferredItem<Item> EVERYTHING_SOCKET = register("everything_socket", (properties) -> new SocketGemItem(properties.component(LEGENDARY_STATUS, true), Socket.universal()));

    public static final DeferredItem<Item> EMPTY_SOCKET = register("empty_socket", (properties) -> new SocketGemItem(properties.rarity(Rarity.UNCOMMON),  Socket.empty()));

    public static final DeferredItem<Item> SOCKET_TESTER = register("socket_tester", (properties) -> new SocketItem(properties){
        @Override
        public boolean canAddExtraSlots() {
            return true;
        }
    });

    public static final DeferredItem<Item> TOPAZ_GEM = register("topaz_gem");

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

    public static <T extends ArmorItem> DeferredItem<Item> registerArmorItem(String name, ArmorMaterial material, ArmorType armorType, TriFunction<ArmorMaterial, ArmorType, Item.Properties, T> function, Item.Properties properties){
        return ITEMS.register(name, key -> function.apply(material, armorType, properties.setId(ResourceKey.create(Registries.ITEM, key))));
    }
    public static <T extends ArmorItem> DeferredItem<Item> registerArmorItem(String name, ArmorMaterial material, ArmorType armorType, TriFunction<ArmorMaterial, ArmorType, Item.Properties, T> function){
        return registerArmorItem(name, material, armorType,  function, new Item.Properties());
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

    public static ResourceKey<Item> createKey(String name) {
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

    public static <T extends Item> DeferredItem<T> baseRegister(String name, ResourceKey<Item> key, Function<Item.Properties, T> builder, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> builder.apply(properties.get().setId(key)));
    }
}