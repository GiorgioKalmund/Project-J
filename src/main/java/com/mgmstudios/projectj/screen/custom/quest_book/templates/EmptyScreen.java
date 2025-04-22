package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.joml.Vector2i;

public class EmptyScreen extends QuestBookTemplate{


    boolean showPageMsg;
    public EmptyScreen(Screen screen, boolean showPageMsg) {
        super(screen, BookPage.EMPTY);
        this.showPageMsg = showPageMsg;
    }
    public EmptyScreen(Screen screen) {
        super(screen, BookPage.EMPTY);
        this.showPageMsg = true;
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
    }
}
