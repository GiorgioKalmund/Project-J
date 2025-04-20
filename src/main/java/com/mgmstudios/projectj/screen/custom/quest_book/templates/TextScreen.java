package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.IMAGE_WIDTH;
import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.drawCenteredStringWithoutDropShadow;

public class TextScreen extends QuestBookTemplate{

    boolean hasTitle;
    public TextScreen(Screen screen, Component pageMsg, boolean hasTitle) {
        super(screen, pageMsg);
        this.hasTitle = hasTitle;
    }

    @Override
    public void render(GuiGraphics guiGraphics, List<FormattedCharSequence> cachedPageComponents, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, cachedPageComponents, mouseX, mouseY, partialTicks);

        Font font = screen.getFont();
        int width = screen.width;

        int imageWidthCenter = (width - IMAGE_WIDTH) / 2;

        int pageMsLen = font.width(this.pageMsg);
        guiGraphics.drawString(font, this.pageMsg, imageWidthCenter - pageMsLen + 192 - 44, 18, 0, false);

        if (pageMsLen == 0)
            pageMsLen = 59;


        int components = Math.min(14, cachedPageComponents.size());
        for(int component = 0; component < components; ++component) {
            FormattedCharSequence formattedcharsequence = cachedPageComponents.get(component);
            if (hasTitle && 0 == component){
                drawCenteredStringWithoutDropShadow(guiGraphics, font, formattedcharsequence,  imageWidthCenter - pageMsLen + IMAGE_WIDTH - 44, 32 + component * 9, 0);
            } else {
                guiGraphics.drawString(font, formattedcharsequence, imageWidthCenter + 36, 32 + component * 9, 0, false);
            }
        }
    }
}
