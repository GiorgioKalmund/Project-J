package com.mgmstudios.projectj.screen.custom;

import com.mgmstudios.projectj.ProjectJ;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.renderstate.RenderStateExtensions;

public class AdobeFurnaceScreen extends AbstractContainerScreen<AdobeFurnaceMenu>{

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID,"textures/gui/adobe_furnace/growth_chamber_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID,"textures/gui/arrow_progress.png");
    public AdobeFurnaceScreen(AdobeFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        //TODO Auto-generated constructor stub
    }

     @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        
        

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderType::guiTextured,GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight,256,256);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderType::guiTextured,ARROW_TEXTURE,x + 73, y + 35,0,0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

}
