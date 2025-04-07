package com.mgmstudios.projectj.block.entity.renderer;

import com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity;
import com.mgmstudios.projectj.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.joml.Quaternionf;

import java.util.List;

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


    private int getLightLevel(Level level, BlockPos pos){
        int block = level.getBrightness(LightLayer.BLOCK, pos);
        int sky = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(block, sky);
    }
}
