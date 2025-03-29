package com.mgmstudios.projectj.block.entity.client;

import com.mgmstudios.projectj.entity.custom.SittableEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class SittableEntityRenderer extends EntityRenderer<SittableEntity, EntityRenderState> {

    public SittableEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public boolean shouldRender(SittableEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
