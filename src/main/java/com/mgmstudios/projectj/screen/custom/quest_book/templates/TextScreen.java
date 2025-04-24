package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent;
import com.mgmstudios.projectj.screen.custom.quest_book.components.TextComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.joml.Vector2i;

public class TextScreen extends QuestBookTemplate{

    boolean showTitle;

    int offsetX, offsetY;

    boolean showPageMsg;
    private final boolean alignCenter;


    public TextScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, boolean alignCenter, int offsetX, int offsetY) {
        super(screen, page);
        this.showTitle = showTitle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.showPageMsg = showPageMsg;
        this.alignCenter = alignCenter;
    }

    public TextScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, boolean alignCenter) {
        this(screen, page, showTitle, showPageMsg, alignCenter, 0, 0);
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
                .alignCenter(alignCenter)
                .showTitle(showTitle)
                .setAdditionalOffset(offsetX, offsetY)
                .setTextComponents(page.components())
                .render(guiGraphics, screen, page);
    }
}
