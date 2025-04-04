package com.mgmstudios.projectj.item;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.item.custom.*;
import net.minecraft.client.renderer.entity.state.FireworkRocketRenderState;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.mgmstudios.projectj.item.custom.OlmecHeadItem.humanoidProperties;
import static net.minecraft.world.item.Items.registerItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProjectJ.MOD_ID);

    public static final DeferredItem<Item> RAW_JADE = register("raw_jade", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> JADE = ITEMS.registerItem("jade", Item::new, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> PYRITE_INGOT = register("pyrite_ingot", Item::new);

    public static final DeferredItem<Item> RAW_PYRITE = register("raw_pyrite", Item::new);

    public static final DeferredItem<Item> LIQUID_PYRITE_BUCKET = register("liquid_pyrite_bucket", (properties) -> new BucketItem(ModFluids.PYRITE.get(), properties.stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final DeferredItem<Item> MACUAHUITL = registerPaxelItem("macuahuitl", new Item.Properties().fireResistant().rarity(Rarity.EPIC));

    public static final DeferredItem<Item> SERPENTINITE_ROD = register("serpentinite_rod");

    public static final DeferredItem<Item> OBSIDIAN_TOOTH = register("obsidian_tooth");

    public static final DeferredItem<Item> SACRIFICIAL_DAGGER = registerSwordItem("sacrificial_dagger", ModToolMaterials.SACRIFICIAL_DAGGER_MATERIAL, 30F, -3.6F, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> TROWEL = register("trowel", TrowelItem::new);

    public static final DeferredItem<Item> SUN_ARMOR_HELMET = registerCustomArmorItem("sun_crown", ModArmorMaterials.SUN_ARMOR_MATERIAL, ArmorType.HELMET, new Item.Properties());

    public static final DeferredItem<Item> MAGNIFYING_GLASS = register("magnifying_glass", MagnifyingGlassItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> FIRE_STARTER = register("fire_starter", FireStarterItem::new, new Item.Properties().stacksTo(1).durability(128));

    public static final DeferredItem<Item> CRUDE_SACRIFICE_BOWL = register("crude_sacrifice_bowl", Item::new, new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> FILLED_CRUDE_SACRIFICE_BOWL = register("filled_crude_sacrifice_bowl", () -> new Item.Properties().stacksTo(1).food(Foods.RABBIT_STEW).usingConvertsTo(ModItems.CRUDE_SACRIFICE_BOWL.get()));

    public static final DeferredItem<Item> JADE_HELMET = registerCustomArmorItem("jade_helmet", ModArmorMaterials.JADE_MATERIAL, ArmorType.HELMET, new Item.Properties());

    public static final DeferredItem<Item> TELEPORTATION_KEY = register("teleportation_key", TeleportationKeyItem::new , new Item.Properties());

    public static final DeferredItem<Item> LITTLE_MAN_SPAWN_EGG = register("little_man_spawn_egg", (properties) -> new SpawnEggItem(ModEntities.LITTLE_MAN_ENTITY.get(), properties));

    public static final DeferredItem<Item> LITTLE_MAN_VOODOO = register("little_man_voodoo", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(16));

    public static final DeferredItem<Item> VOODOO_CATCHER = register("voodoo_catcher", VoodooCatcherItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> PYRITE_MAGNET = register("pyrite_magnet", (properties) -> new MagnetItem(properties.setId(createKey("pyrite_magnet")), 5, 0.3F));

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
        return ITEMS.register(name, key ->new Item(humanoidProperties(material, properties.setId(ResourceKey.create(Registries.ITEM, key)).durability(armorType.getDurability(material.durability())), armorType)));
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
}