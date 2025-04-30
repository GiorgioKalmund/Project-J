package com.mgmstudios.projectj.entity.client.armor;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.item.custom.armor.JadeArmorItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public final class JadeArmorModel extends GeoModel<JadeArmorItem> {

    @Override
    public ResourceLocation getModelResource(JadeArmorItem animatable, @Nullable GeoRenderer<JadeArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "geo/jade_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JadeArmorItem animatable, @Nullable GeoRenderer<JadeArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/block/jade_block.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JadeArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "animations/armor_animations.json");
    }
}
