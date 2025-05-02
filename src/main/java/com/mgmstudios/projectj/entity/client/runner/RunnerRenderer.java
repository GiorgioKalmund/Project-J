package com.mgmstudios.projectj.entity.client.runner;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;

public class RunnerRenderer extends ZombieRenderer {
    public RunnerRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.HUSK, ModelLayers.HUSK_BABY, ModelLayers.HUSK_INNER_ARMOR, ModelLayers.HUSK_OUTER_ARMOR, ModelLayers.HUSK_BABY_INNER_ARMOR, ModelLayers.HUSK_BABY_OUTER_ARMOR);
    }

    public static final ResourceLocation LAYER_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/entity/runner/runner.png");

    public ResourceLocation getTextureLocation(ZombieRenderState p_365391_) {
        return LAYER_LOCATION;
    }
}
