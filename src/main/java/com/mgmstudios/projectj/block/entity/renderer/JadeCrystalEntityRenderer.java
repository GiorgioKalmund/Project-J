package com.mgmstudios.projectj.block.entity.renderer;


import com.mgmstudios.projectj.block.entity.custom.JadeCrystalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;


public class JadeCrystalEntityRenderer implements BlockEntityRenderer<JadeCrystalBlockEntity> {

    public JadeCrystalEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(JadeCrystalBlockEntity jadeCrystalBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = jadeCrystalBlockEntity.getInventory().getStackInSlot(0);
        if (itemStack.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        //rotate randomly
        poseStack.mulPose(Axis.YP.rotationDegrees(jadeCrystalBlockEntity.getRandomYRotation()));
        poseStack.mulPose(Axis.XP.rotationDegrees(jadeCrystalBlockEntity.getRandomXRotation()));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,
                getLightLevel(jadeCrystalBlockEntity.getLevel(), jadeCrystalBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY,
                poseStack,
                multiBufferSource,
                jadeCrystalBlockEntity.getLevel(),
                1);

        poseStack.popPose();
    }

    private int getLightLevel(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos) {
        //biggest of blocks around pos up, down, left, right, front, back

        int[] blocks = {
                level.getBrightness(LightLayer.BLOCK, pos.above()),
                level.getBrightness(LightLayer.BLOCK, pos.below()),
                level.getBrightness(LightLayer.BLOCK, pos.north()),
                level.getBrightness(LightLayer.BLOCK, pos.south()),
                level.getBrightness(LightLayer.BLOCK, pos.east()),
                level.getBrightness(LightLayer.BLOCK, pos.west())
        };
        int[] skies = {
                level.getBrightness(LightLayer.SKY, pos.above()),
                level.getBrightness(LightLayer.SKY, pos.below()),
                level.getBrightness(LightLayer.SKY, pos.north()),
                level.getBrightness(LightLayer.SKY, pos.south()),
                level.getBrightness(LightLayer.SKY, pos.east()),
                level.getBrightness(LightLayer.SKY, pos.west())
        };

        int blocksMax = 0;
        int skyMax = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] > blocksMax) {
                blocksMax = blocks[i];
            }
            if (skies[i] > skyMax) {
                skyMax = skies[i];
            }
        }
        return LightTexture.pack(blocksMax, skyMax);
    }
}