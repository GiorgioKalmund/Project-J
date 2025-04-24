package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.item.ModEquipmentAssets;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.mgmstudios.projectj.ProjectJ.MOD_ID;

public class ModEquipmentInfoProvider implements DataProvider {

    private final PackOutput.PathProvider path;

    public ModEquipmentInfoProvider(PackOutput output) {
        this.path = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private void add(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> registrar) {
        // https://docs.neoforged.net/docs/items/armor/
        registrar.accept(
                ModEquipmentAssets.JADE,
                EquipmentClientInfo.builder()
                        // For humanoid head, chest, and feet
                        .addHumanoidLayers(ResourceLocation.fromNamespaceAndPath(MOD_ID, "jade_classic"))
                        .build()
        );
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<ResourceLocation, EquipmentClientInfo> map = new HashMap<>();
        this.add((name, info) -> {
            System.out.println("Adding " + name + " -> " + name.location() + " -> " + info);
            if (map.putIfAbsent(name.location(), info) != null) {
                throw new IllegalStateException("Tried to register equipment client info twice for id: " + name);
            }
        });
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.path, map);
    }

    @Override
    public String getName() {
        return "Equipment Client Infos: " + MOD_ID;
    }
}
