package com.mgmstudios.projectj.entity.client.little_king;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.client.little_man.LittleManModel;
import com.mgmstudios.projectj.entity.client.little_man.LittleManRenderState;
import com.mgmstudios.projectj.entity.client.little_man.LittleManRenderer;
import com.mgmstudios.projectj.entity.custom.LittleKingEntity;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LittleKingRenderer extends MobRenderer<LittleKingEntity, LittleKingRenderState, LittleKingModel> {

    public LittleKingRenderer(EntityRendererProvider.Context context) {
        super(context, new LittleKingModel(context.bakeLayer(LittleKingModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public LittleKingRenderState createRenderState() {
        return new LittleKingRenderState();
    }


    @Override
    public void extractRenderState(LittleKingEntity littleKingEntity, LittleKingRenderState littleKingRenderState, float v) {
        super.extractRenderState(littleKingEntity, littleKingRenderState, v);
        littleKingRenderState.idle.copyFrom(littleKingEntity.idleAnimationState);
    }

    @Override
    public ResourceLocation getTextureLocation(LittleKingRenderState littleKingRenderState) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/little_king/little_king_body.png");
    }
}
