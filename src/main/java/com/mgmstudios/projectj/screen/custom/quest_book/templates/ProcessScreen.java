package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.components.ImageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.PAGE_TEXT_Y_OFFSET;
import static com.mgmstudios.projectj.screen.custom.quest_book.templates.ItemListScreen.renderListStackImage;

public class ProcessScreen extends TextScreen {

    int spacing = 0;
    boolean showFuel = false;

    public ProcessScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, int spacing, boolean showFuel) {
        super(screen, page, showTitle, showPageMsg, false, 0, (showFuel ? QUEST_IMAGE_HEIGHT : 0) + QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
        this.spacing = spacing;
        this.showFuel = showFuel;
    }

    public ProcessScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, int spacing) {
        super(screen, page, showTitle, showPageMsg, false, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
        this.spacing = spacing;
    }

    public ProcessScreen(Screen screen, BookPage page) {
        super(screen, page, false, true, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        renderListStackImage(guiGraphics, 0, page, screen, mouseX, mouseY, -spacing, 0);

        ImageComponent.ImageBuilder()
                .setImage(page.image(1))
                .size(24, 16)
                .borderless()
                .render(guiGraphics, screen, page);

        renderListStackImage(guiGraphics, 2, page, screen, mouseX, mouseY, spacing + 8, 0);

        renderListStackImage(guiGraphics, getOrDefault(page.secondaryImages(), 0, QuestBookScreen.QuestBookImage.unavailable()), page, screen,  mouseX, mouseY, PAGE_TEXT_X_OFFSET + 16, -PAGE_TEXT_Y_OFFSET + 8);

        if (showFuel){
            ImageComponent.ImageBuilder()
                    .setImage(page.image(3))
                    .size(13, 13)
                    .setAdditionalOffset(3, QUEST_IMAGE_HEIGHT + 2)
                    .render(guiGraphics, screen, page);
        }
    }
}
