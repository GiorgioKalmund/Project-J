package com.mgmstudios.projectj.entity.client.obsidian_arrow;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.projectile.ObsidianArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class ObsidianArrowRenderer extends ArrowRenderer<ObsidianArrow, ArrowRenderState> {

    public static final ResourceLocation OBSIDIAN_ARROW_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/projectiles/obsidian_arrow.png");

    public ObsidianArrowRenderer(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    protected ResourceLocation getTextureLocation(ArrowRenderState arrowRenderState) {
        return OBSIDIAN_ARROW_LOCATION;
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
