package com.mgmstudios.projectj.entity.client.quetzal;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.client.little_king.LittleKingRenderState;
import com.mgmstudios.projectj.entity.custom.LittleKingEntity;
import com.mgmstudios.projectj.entity.custom.QuetzalEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

import java.util.Queue;

public class QuetzalRenderer extends MobRenderer<QuetzalEntity, QuetzalRenderState, QuetzalModel> {


    public QuetzalRenderer(EntityRendererProvider.Context context) {
        super(context, new QuetzalModel(context.bakeLayer(QuetzalModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public QuetzalRenderState createRenderState() {
        return new QuetzalRenderState();
    }


    @Override
    public void extractRenderState(QuetzalEntity littleKingEntity, QuetzalRenderState littleKingRenderState, float v) {
        super.extractRenderState(littleKingEntity, littleKingRenderState, v);
        littleKingRenderState.idle.copyFrom(littleKingEntity.idleAnimationState);
    }

    @Override
    public ResourceLocation getTextureLocation(QuetzalRenderState littleKingRenderState) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/quetzal/quetzal_texture.png");
    }
}
