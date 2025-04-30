package com.mgmstudios.projectj.entity.client.armor;

import com.mgmstudios.projectj.item.custom.armor.JadeArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public final class JadeArmorRenderer extends GeoArmorRenderer<JadeArmorItem> {
    public JadeArmorRenderer() {
        super(new JadeArmorModel());
    }
}
