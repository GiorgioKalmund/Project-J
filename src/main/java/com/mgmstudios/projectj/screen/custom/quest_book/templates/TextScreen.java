package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.TextComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.IMAGE_WIDTH;

public class TextScreen extends QuestBookTemplate{

    boolean hasTitle;

    int offsetX, offsetY = 0;

    public TextScreen(Screen screen, BookPage page, boolean hasTitle) {
        super(screen, page);
        this.hasTitle = hasTitle;
    }

    public TextScreen(Screen screen, BookPage page, boolean hasTitle, int offsetX, int offsetY) {
        super(screen, page);
        this.hasTitle = hasTitle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        Font font = screen.getFont();
        int width = screen.width;

        int imageWidthCenter = (width - IMAGE_WIDTH) / 2;

        int pageMsLen = font.width(page.pageMsg());
        guiGraphics.drawString(font, page.pageMsg(), imageWidthCenter - pageMsLen + 192 - 44, 18, 0, false);

        TextComponent.TextBuilder()
                .hasTitle(hasTitle)
                .setOffset(offsetX, offsetY)
                .setTextComponents(page.components())
                .render(guiGraphics, screen, page);
    }
}
