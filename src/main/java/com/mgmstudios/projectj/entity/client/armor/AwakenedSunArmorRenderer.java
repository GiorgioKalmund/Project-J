package com.mgmstudios.projectj.entity.client.armor;

import com.mgmstudios.projectj.item.custom.armor.AwakenedSunArmorItem;
import com.mgmstudios.projectj.item.custom.armor.SunArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public final class AwakenedSunArmorRenderer extends GeoArmorRenderer<AwakenedSunArmorItem> {
    public AwakenedSunArmorRenderer() {
        super(new AwakenedSunArmorModel());
    }
}
