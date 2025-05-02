package com.mgmstudios.projectj.entity.client.armor;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.item.custom.armor.AwakenedSunArmorItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public final class AwakenedSunArmorModel extends GeoModel<AwakenedSunArmorItem> {

    @Override
    public ResourceLocation getModelResource(AwakenedSunArmorItem animatable, @Nullable GeoRenderer<AwakenedSunArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "geo/awakened_sun_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AwakenedSunArmorItem animatable, @Nullable GeoRenderer<AwakenedSunArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/armor/awakened_sun_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AwakenedSunArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "animations/armor_animations.json");
    }
}
