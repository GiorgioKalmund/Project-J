package com.mgmstudios.projectj.entity.events;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.client.LittleManModel;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LittleManModel.LAYER_LOCATION, LittleManModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LITTLE_MAN_ENTITY.get(), LittleManEntity.createAttributes().build());
    }
}
