package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent;
import com.mgmstudios.projectj.screen.custom.quest_book.components.TextComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.joml.Vector2d;
import org.joml.Vector2i;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.IMAGE_WIDTH;

public class TextScreen extends QuestBookTemplate{

    boolean hasTitle;

    int offsetX, offsetY = 0;

    boolean showPageMsg;

    public TextScreen(Screen screen, BookPage page, boolean hasTitle, boolean showPageMsg) {
        super(screen, page);
        this.hasTitle = hasTitle;
        this.showPageMsg = showPageMsg;
    }

    public TextScreen(Screen screen, BookPage page, boolean hasTitle, boolean showPageMsg, int offsetX, int offsetY) {
        super(screen, page);
        this.hasTitle = hasTitle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.showPageMsg = showPageMsg;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        if (showPageMsg){
            Font font = screen.getFont();
            int pageMsLen = font.width(page.pageMsg());
            Vector2i coords = AbstractComponent.ComponentPositionUtils.getBookTopRightCorner(screen, pageMsLen);
            guiGraphics.drawString(font, page.pageMsg(), coords.x, coords.y, 0, false);
        }

        TextComponent.TextBuilder()
                .hasTitle(hasTitle)
                .setOffset(offsetX, offsetY)
                .setTextComponents(page.components())
                .render(guiGraphics, screen, page);
    }
}
