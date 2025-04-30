package com.mgmstudios.projectj.entity.client.armor;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.item.custom.armor.SunArmorItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public final class SunArmorModel extends GeoModel<SunArmorItem> {

    @Override
    public ResourceLocation getModelResource(SunArmorItem animatable, @Nullable GeoRenderer<SunArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "geo/sun_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunArmorItem animatable, @Nullable GeoRenderer<SunArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/armor/sun_armor_template.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "animations/armor_animations.json");
    }
}
