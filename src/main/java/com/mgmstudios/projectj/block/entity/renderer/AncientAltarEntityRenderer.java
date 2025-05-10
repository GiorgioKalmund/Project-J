package com.mgmstudios.projectj.block.entity.renderer;

import com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public class AncientAltarEntityRenderer implements BlockEntityRenderer<AncientAltarBlockEntity> {

    public AncientAltarEntityRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(AncientAltarBlockEntity altarBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        float ownAxisRotation = altarBlockEntity.getRenderingRotation();
        poseStack.pushPose();
        poseStack.translate(0.5F, altarBlockEntity.getRenderingHeight(), 0.5F);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(ownAxisRotation));

        int itemCount = altarBlockEntity.itemsInside;
        float radius = altarBlockEntity.getRenderingRadius();

        for (int index = 0; index < itemCount; index++) {
            poseStack.pushPose();

            float angleOffset = index * (360.0f / itemCount);

            poseStack.mulPose(Axis.YP.rotationDegrees(angleOffset));

            poseStack.translate(0, 0, radius);

            ItemStack itemStack = altarBlockEntity.inventory.getStackInSlot(index);
            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,
                    getLightLevel(altarBlockEntity.getLevel(), altarBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    multiBufferSource,
                    altarBlockEntity.getLevel(),
                    1);

            poseStack.popPose();
        }

        poseStack.popPose();
    }

    public static void drawBillboard(String  text, int color, boolean dropShadow, boolean background, boolean fullBright, Vec3 translation, float scale, PoseStack poseStack, BlockEntityRenderDispatcher dispatcher, Font font, MultiBufferSource multiBufferSource, int pPackedLight){
        drawBillboard(text, color, dropShadow, background, fullBright, translation, new Vec3(scale, scale, scale), poseStack, dispatcher, font, multiBufferSource, pPackedLight);
    }

    public static void drawBillboard(String  text, int color, boolean dropShadow, boolean background, boolean fullBright, Vec3 translation, Vec3 scale, PoseStack poseStack, BlockEntityRenderDispatcher dispatcher, Font font, MultiBufferSource multiBufferSource, int pPackedLight){
        float textWidth = _setupBillboardText(text, translation, scale, poseStack, dispatcher, font);
        int backgroundOpacity = (int)(Minecraft.getInstance().options.getBackgroundOpacity(0.25F) * 255.0F) << 24;
        font.drawInBatch(text, -textWidth / 2f, 0, color, dropShadow, poseStack.last().pose(), multiBufferSource, background ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, background ? backgroundOpacity : 0, fullBright ? LightTexture.pack(15, 15) : pPackedLight);
        poseStack.popPose();
    }

    public static void drawBillboardOutline(String  text, int color, int outlineColor, boolean glowing, Vec3 translation, float scale, PoseStack poseStack, BlockEntityRenderDispatcher dispatcher, Font font, MultiBufferSource multiBufferSource, int pPackedLight){
        drawBillboardOutline(text, color, outlineColor, glowing, translation, new Vec3(scale, scale, scale), poseStack, dispatcher, font, multiBufferSource, pPackedLight);
    }

    public static void drawBillboardOutline(String  text, int color, int outlineColor, boolean glowing, Vec3 translation, Vec3 scale, PoseStack poseStack, BlockEntityRenderDispatcher dispatcher, Font font, MultiBufferSource multiBufferSource, int pPackedLight){
        float textWidth = _setupBillboardText(text, translation, scale, poseStack, dispatcher, font);
        font.drawInBatch8xOutline(FormattedCharSequence.forward(text, Style.EMPTY), -textWidth / 2f, 0, color, outlineColor,  poseStack.last().pose(), multiBufferSource, glowing ? LightTexture.pack(15, 15) : pPackedLight);
        poseStack.popPose();
    }

    private static float _setupBillboardText(String text, Vec3 translation, Vec3 scale, PoseStack poseStack, BlockEntityRenderDispatcher dispatcher, Font font){
        poseStack.pushPose();
        poseStack.translate(translation.x, translation.y, translation.z);
        float yaw   = dispatcher.camera.getYRot();
        float pitch = dispatcher.camera.getXRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees( pitch));
        poseStack.scale((float) -scale.x, (float) -scale.y,(float) scale.z);
        return font.width(text);
    }

    private int getLightLevel(Level level, BlockPos pos){
        int block = level.getBrightness(LightLayer.BLOCK, pos);
        int sky = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(block, sky);
    }
}
