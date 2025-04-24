package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.ImageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.util.ItemLookup.getStack;

public class ImageScreen extends TextScreen {


    public ImageScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg) {
        super(screen, page, showTitle, showPageMsg, false, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
    }

    public ImageScreen(Screen screen, BookPage page) {
        super(screen, page, false, true, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        ImageComponent.ImageBuilder()
                .setImage(page.image())
                .setBorderScale(1.3F)
                .render(guiGraphics, screen, page);
    }
}
