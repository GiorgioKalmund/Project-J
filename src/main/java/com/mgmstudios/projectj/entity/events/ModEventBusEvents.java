package com.mgmstudios.projectj.entity.events;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.client.canoe.CanoeModel;
import com.mgmstudios.projectj.entity.client.little_king.LittleKingModel;
import com.mgmstudios.projectj.entity.client.little_man.LittleManModel;
import com.mgmstudios.projectj.entity.client.quetzal.QuetzalModel;
import com.mgmstudios.projectj.entity.custom.LittleKingEntity;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import com.mgmstudios.projectj.entity.custom.QuetzalEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LittleManModel.LAYER_LOCATION, LittleManModel::createBodyLayer);
        event.registerLayerDefinition(LittleKingModel.LAYER_LOCATION, LittleKingModel::createBodyLayer);
        event.registerLayerDefinition(QuetzalModel.LAYER_LOCATION, QuetzalModel::createBodyLayer);
        event.registerLayerDefinition(CanoeModel.LAYER_LOCATION, CanoeModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LITTLE_MAN_ENTITY.get(), LittleManEntity.createAttributes().build());
        event.put(ModEntities.LITTLE_KING_ENTITY.get(), LittleKingEntity.createAttributes().build());
        event.put(ModEntities.QUETZAL_ENTITY.get(), QuetzalEntity.createAttributes().build());
    }
}
