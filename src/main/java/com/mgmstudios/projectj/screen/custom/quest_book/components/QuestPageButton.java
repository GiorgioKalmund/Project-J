package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class QuestPageButton extends Button {


    private static final ResourceLocation PAGE_FORWARD_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "widget/page_forward_highlighted");
    private static final ResourceLocation PAGE_FORWARD_SPRITE = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "widget/page_forward");
    private static final ResourceLocation PAGE_BACKWARD_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "widget/page_backward_highlighted");
    private static final ResourceLocation PAGE_BACKWARD_SPRITE = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "widget/page_backward");
    private static final ResourceLocation HOME_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "widget/home_highlighted");
    private static final ResourceLocation HOME_SPRITE = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "widget/home");
    private final boolean isForward;
    private final boolean isHomeButton;
    private final boolean playTurnSound;

    public QuestPageButton(int x, int y, boolean isForward, OnPress onPress, boolean playTurnSound) {
        super(x, y, 23, 13, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.isForward = isForward;
        this.playTurnSound = playTurnSound;
        this.isHomeButton = false;
    }

    public QuestPageButton(int x, int y, OnPress onPress, boolean playTurnSound, boolean isHomeButton) {
        super(x, y, 23, 13, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.isForward = false;
        this.playTurnSound = playTurnSound;
        this.isHomeButton = isHomeButton;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation resourcelocation;
        if (!isHomeButton){
            if (this.isForward) {
                resourcelocation = this.isHoveredOrFocused() ? PAGE_FORWARD_HIGHLIGHTED_SPRITE : PAGE_FORWARD_SPRITE;
            } else {
                resourcelocation = this.isHoveredOrFocused() ? PAGE_BACKWARD_HIGHLIGHTED_SPRITE : PAGE_BACKWARD_SPRITE;
            }
        } else {
            resourcelocation = this.isHoveredOrFocused() ? HOME_HIGHLIGHTED_SPRITE : HOME_SPRITE;
        }
        guiGraphics.blitSprite(RenderType::guiTextured, resourcelocation, this.getX(), this.getY(), 23, 13);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        setFocused(false);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public void playDownSound(SoundManager handler) {
        if (this.playTurnSound) {
            handler.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        }
    }
}
