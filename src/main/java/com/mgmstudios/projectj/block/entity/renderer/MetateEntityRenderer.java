package com.mgmstudios.projectj.block.entity.renderer;

import com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity;
import com.mgmstudios.projectj.block.entity.custom.MetateBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

import static com.mgmstudios.projectj.block.custom.MetateBlock.FACING;

public class MetateEntityRenderer implements BlockEntityRenderer<MetateBlockEntity> {

    public MetateEntityRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(MetateBlockEntity metateBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = metateBlockEntity.inventory.getStackInSlot(0);
        Direction direction = metateBlockEntity.getBlockState().getValue(FACING);
        float xOffset = 0.5F;
        float zOffset = 0.5F;
        if (direction == Direction.NORTH || direction == Direction.SOUTH)
            xOffset = direction == Direction.NORTH ? 0.4F : 0.6F;
        else
            zOffset = direction == Direction.EAST ? 0.4F : 0.6F;

        float rotation = direction == Direction.SOUTH ? 270 : 90;

        poseStack.pushPose();
        poseStack.translate(xOffset, 0.4f, zOffset);
        poseStack.scale(0.5f, 0.5f, 0.5f);

        if (direction == Direction.EAST)
            poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        if (direction == Direction.WEST)
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
        poseStack.mulPose(Axis.XP.rotationDegrees(rotation));

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(metateBlockEntity.getLevel(),
                metateBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, metateBlockEntity.getLevel(), 1);
        poseStack.popPose();
    }


    private int getLightLevel(Level level, BlockPos pos){
        int block = level.getBrightness(LightLayer.BLOCK, pos);
        int sky = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(block, sky);
    }
}
