package com.mgmstudios.projectj.block.entity.renderer;

import com.mgmstudios.projectj.block.entity.custom.TeleportationBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

import static com.mgmstudios.projectj.block.entity.renderer.AncientAltarEntityRenderer.drawBillboard;

public class TeleportationBlockEntityRenderer implements BlockEntityRenderer<TeleportationBlockEntity> {

    Font font;
    BlockEntityRenderDispatcher dispatcher;

    @Override
    public void render(TeleportationBlockEntity teleportationBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        Vec3 translation = new Vec3(0.5, 0.75, 0.5);
        float scale = 0.025F;
        String name = teleportationBlockEntity.getName();
        int color = teleportationBlockEntity.getTextColor().getTextColor();
        drawBillboard(name, color, false, translation, scale, poseStack, dispatcher, font, multiBufferSource, pPackedLight);
    }

    public TeleportationBlockEntityRenderer(BlockEntityRendererProvider.Context context){
        font = context.getFont();
        dispatcher = context.getBlockEntityRenderDispatcher();
    }
}
