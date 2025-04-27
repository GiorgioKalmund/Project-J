package com.mgmstudios.projectj.entity.client.canoe;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.custom.Canoe;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CanoeRenderer extends AbstractBoatRenderer {

    private final Model waterPatchModel;
    private final ResourceLocation texture;
    private final EntityModel<BoatRenderState> model;
    public CanoeRenderer(EntityRendererProvider.Context context,  ModelLayerLocation modelLayer) {
        super(context);
        this.texture = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/canoe/mock_canoe.png");
        this.waterPatchModel = new Model.Simple(context.bakeLayer(ModelLayers.BOAT_WATER_PATCH), p_359275_ -> RenderType.waterMask());
        this.model = new CanoeModel(context.bakeLayer(modelLayer));
    }

    @Override
    public BoatRenderState createRenderState() {
        return new BoatRenderState();
    }

    @Override
    protected EntityModel<BoatRenderState> model() {
        return this.model;
    }

    @Override
    public void render(BoatRenderState boatRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_376589_) {
        super.render(boatRenderState, poseStack, multiBufferSource, p_376589_);
    }

    @Override
    protected RenderType renderType() {
        return this.model.renderType(this.texture);
    }

    @Override
    protected void renderTypeAdditions(BoatRenderState p_376691_, PoseStack p_376523_, MultiBufferSource p_376756_, int p_376697_) {
        if (!p_376691_.isUnderWater) {
            this.waterPatchModel
                    .renderToBuffer(p_376523_, p_376756_.getBuffer(this.waterPatchModel.renderType(this.texture)), p_376697_, OverlayTexture.NO_OVERLAY);
        }
    }
}
