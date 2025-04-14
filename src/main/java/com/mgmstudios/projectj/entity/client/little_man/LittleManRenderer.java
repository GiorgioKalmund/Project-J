package com.mgmstudios.projectj.entity.client.little_man;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
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
    public void extractRenderState(LittleManEntity littleManEntity, LittleManRenderState littleManRenderState, float v) {
        super.extractRenderState(littleManEntity, littleManRenderState, v);
        littleManRenderState.idle.copyFrom(littleManEntity.idleAnimationState);
    }

    @Override
    public ResourceLocation getTextureLocation(LittleManRenderState littleManRenderState) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/little_man/awake.png");
    }
}
