package com.mgmstudios.projectj.item;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.custom.OlmecHeadItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProjectJ.MOD_ID);

    public static final DeferredItem<Item> RAW_JADE = ITEMS.registerItem("raw_jade",
            Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> JADE = ITEMS.registerItem("jade",
            Item::new, new Item.Properties().rarity(Rarity.RARE));

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
}