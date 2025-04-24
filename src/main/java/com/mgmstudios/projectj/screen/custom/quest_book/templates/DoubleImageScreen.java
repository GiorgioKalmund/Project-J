package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.ImageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QUEST_IMAGE_HEIGHT;

public class DoubleImageScreen extends TextScreen {

    int spacing = 0;
    boolean flipOrdering = false;

    public DoubleImageScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, int spacing, boolean flipOrdering) {
        super(screen, page, showTitle, showPageMsg, false, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
        this.spacing = spacing;
        this.flipOrdering = flipOrdering;
    }

    public DoubleImageScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, int spacing) {
        super(screen, page, showTitle, showPageMsg, false, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
        this.spacing = spacing;
    }

    public DoubleImageScreen(Screen screen, BookPage page) {
        super(screen, page, false, true, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        ImageComponent.ImageBuilder()
                .setImage(page.image())
                .setBorderScale(1.3F)
                .setAdditionalOffset(flipOrdering ? spacing : -spacing, 0)
                .render(guiGraphics, screen, page);

        ImageComponent.ImageBuilder()
                .setImage(page.image(1).showBorder(page.image().showBorder()))
                .setBorderScale(1.3F)
                .setAdditionalOffset(flipOrdering ? -spacing : spacing, 0)
                .render(guiGraphics, screen, page);
    }
}
