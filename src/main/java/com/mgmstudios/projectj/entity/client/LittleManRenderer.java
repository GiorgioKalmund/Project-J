package com.mgmstudios.projectj.entity.client;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class LittleManRenderer extends MobRenderer<LittleManEntity, LittleManRenderState, LittleManModel> {

    public LittleManRenderer(EntityRendererProvider.Context context) {
        super(context, new LittleManModel(context.bakeLayer(LittleManModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public LittleManRenderState createRenderState() {
        return new LittleManRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LittleManRenderState littleManRenderState) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/little_man/awake.png");
    }
}
