package com.mgmstudios.projectj.loot;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.fluid.custom.PyriteFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModLootTables {
    public static final DeferredRegister<LootTable> LOOT_TABLES = DeferredRegister.create(Registries.LOOT_TABLE, ProjectJ.MOD_ID);

    public static final DeferredHolder<LootTable, LootTable> QUETZAL_LAY =
            LOOT_TABLES.register("quetzal_lay", () -> LootTable.EMPTY);

    public static void register(IEventBus bus){
        LOOT_TABLES.register(bus);
    }
}
