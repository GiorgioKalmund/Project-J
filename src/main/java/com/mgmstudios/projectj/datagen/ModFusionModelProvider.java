package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.util.ItemLookup;
import com.supermartijn642.fusion.api.model.DefaultModelTypes;
import com.supermartijn642.fusion.api.model.ModelInstance;
import com.supermartijn642.fusion.api.model.data.ConnectingModelDataBuilder;
import com.supermartijn642.fusion.api.predicate.DefaultConnectionPredicates;
import com.supermartijn642.fusion.api.provider.FusionModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModFusionModelProvider extends FusionModelProvider {

    public ModFusionModelProvider(PackOutput output) {
        super(ProjectJ.MOD_ID, output);
    }

    @Override
    protected void generate() {
        addModel(ModBlocks.ADOBE_GLASS.get());
    }

    private void addModel(Block block){
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID,  "block/" + block.getDescriptionId().split("\\.")[2] + ".json");
        ResourceLocation resourceLocation2 = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID,  "block/" + block.getDescriptionId().split("\\.")[2]);

        var modelData = ConnectingModelDataBuilder.builder()
                .parent(ModelTemplates.CUBE_ALL.model.get())
                .texture("all", resourceLocation2)
                .connection(DefaultConnectionPredicates.isSameBlock())
                .build();
        var modelInstance = ModelInstance.of(DefaultModelTypes.CONNECTING, modelData);
        this.addModel(resourceLocation, modelInstance);
    }


    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModFusionModelProvider::new);
    }}
