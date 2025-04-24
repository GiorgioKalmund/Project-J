package com.mgmstudios.projectj.entity.client.quetzal;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.custom.QuetzalEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class QuetzalRenderer extends MobRenderer<QuetzalEntity, QuetzalRenderState, QuetzalModel> {


    public QuetzalRenderer(EntityRendererProvider.Context context) {
        super(context, new QuetzalModel(context.bakeLayer(QuetzalModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public QuetzalRenderState createRenderState() {
        return new QuetzalRenderState();
    }

    @Override
    public void extractRenderState(QuetzalEntity quetzalEntity, QuetzalRenderState quetzalRenderState, float v) {
        super.extractRenderState(quetzalEntity, quetzalRenderState, v);
        quetzalRenderState.isResting = quetzalEntity.isResting();
        quetzalRenderState.restAnimationState.copyFrom(quetzalEntity.restAnimationState);
        quetzalRenderState.flyAnimationState.copyFrom(quetzalEntity.flyAnimationState);
    }

    @Override
    public ResourceLocation getTextureLocation(QuetzalRenderState quetzalRenderState) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/quetzal/quetzal_texture.png");
    }
}
