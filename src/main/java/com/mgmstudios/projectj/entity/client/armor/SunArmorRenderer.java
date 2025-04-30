package com.mgmstudios.projectj.entity.client.armor;

import com.mgmstudios.projectj.item.custom.armor.JadeArmorItem;
import com.mgmstudios.projectj.item.custom.armor.SunArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public final class SunArmorRenderer extends GeoArmorRenderer<SunArmorItem> {
    public SunArmorRenderer() {
        super(new SunArmorModel());
    }
}
