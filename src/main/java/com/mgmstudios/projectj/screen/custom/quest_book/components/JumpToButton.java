package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.ContentsPageScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.TEXT_WIDTH;
import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.drawCenteredStringWithoutDropShadow;
import static com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent.ComponentPositionUtils.textCenteredStartX;

public class JumpToButton extends Button {

    private static final ResourceLocation HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "widget/jump_button_highlighted");

    private final boolean playTurnSound;
    private final ItemLike displayItem;
    private final String description;
    private final Screen screen;

    public JumpToButton(OnPress onPress, ItemLike displayItem, String description, int x, int y, boolean playTurnSound, Screen screen) {
        super(x, y, 16, 16, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.displayItem = displayItem;
        this.playTurnSound = playTurnSound;
        this.description = description;
        this.screen = screen;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Font font = screen.getFont();
        if (isHoveredOrFocused())
            guiGraphics.blitSprite(RenderType::guiTextured, HIGHLIGHTED_SPRITE, getX() - 2, getY() - 2, 20, 20);
        guiGraphics.renderItem(new ItemStack(displayItem), getX(), getY());
        List<FormattedCharSequence> descriptionSequence = font.split(FormattedText.of(description), TEXT_WIDTH);
        if (descriptionSequence.getFirst() != null)
            drawCenteredStringWithoutDropShadow(guiGraphics, font, descriptionSequence.getFirst(),  getX() + 8, getY() + 20, 0);
    }

    public void playDownSound(SoundManager handler) {
        if (this.playTurnSound) {
            handler.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        }
    }
}
