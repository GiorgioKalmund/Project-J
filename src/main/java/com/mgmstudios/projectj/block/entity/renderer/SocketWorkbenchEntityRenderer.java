package com.mgmstudios.projectj.block.entity.renderer;

import com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity;
import com.mgmstudios.projectj.block.entity.custom.SocketWorkbenchBlockEntity;
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
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

import static com.mgmstudios.projectj.block.custom.MetateBlock.FACING;

public class SocketWorkbenchEntityRenderer implements BlockEntityRenderer<SocketWorkbenchBlockEntity> {

    public SocketWorkbenchEntityRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(SocketWorkbenchBlockEntity workbench, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        int INVENTORY_SIZE = 3;
        for (int index = 0; index < INVENTORY_SIZE; index ++){
            ItemStack stack = workbench.inventory.getStackInSlot(index);
            Direction direction = workbench.getBlockState().getValue(FACING);
            float xOffset = 0.5F;
            float zOffset = 0.5F;
            float yPos = 0.83f;
            if (index == 0){
                yPos = 0.95F;
            } else if (index == 2){
                yPos = 0.89f;
            }
            float centerSidewaysOffset = 0.025F;
            switch (direction){
                case SOUTH -> {
                    if (index == 0){
                        xOffset += 0.3F;
                    } else if (index == 2){
                        xOffset -= 0.3F;
                        zOffset += 0.1F;
                    } else {
                        xOffset -= centerSidewaysOffset;
                        zOffset -= 0.1F;
                    }
                    zOffset += 0.1F;
                }
                case NORTH -> {
                    if (index == 0){
                        xOffset -= 0.3F;
                    } else if (index == 2){
                        xOffset += 0.3F;
                        zOffset -= 0.1F;
                    } else {
                        xOffset += centerSidewaysOffset;
                        zOffset += 0.1F;
                    }
                    zOffset -= 0.1F;
                }
                case WEST -> {
                    if (index == 0){
                        zOffset += 0.3F;
                    } else if (index == 2){
                        zOffset -= 0.3F;
                        xOffset -= 0.1F;
                    } else {
                        zOffset -= centerSidewaysOffset;
                        xOffset += 0.1F;
                    }
                    xOffset -= 0.1F;
                }
                case EAST -> {
                    if (index == 0){
                        zOffset -= 0.3F;
                    } else if (index == 2){
                        zOffset += 0.3F;
                        xOffset += 0.1F;
                    } else {
                        zOffset += centerSidewaysOffset;
                        xOffset -= 0.1F;
                    }
                    xOffset += 0.1F;
                }
            }

            float rotation = direction == Direction.SOUTH ? 270 : 90;

            poseStack.pushPose();
            poseStack.translate(xOffset, yPos, zOffset);
            float scale = 0.3F;
            poseStack.scale(scale, scale, scale);
            float yRot = 90;

            if (direction == Direction.EAST)
                poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
            if (direction == Direction.WEST)
                poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
            else
                poseStack.mulPose(Axis.YP.rotationDegrees(2 * yRot));
            poseStack.mulPose(Axis.XP.rotationDegrees(rotation));

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(workbench.getLevel(),
                    workbench.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, workbench.getLevel(), 1);
            poseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos){
        int block = level.getBrightness(LightLayer.BLOCK, pos);
        int sky = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(block, sky);
    }
}
