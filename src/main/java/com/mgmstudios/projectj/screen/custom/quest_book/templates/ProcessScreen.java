package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.components.ImageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QUEST_IMAGE_HEIGHT;
import static com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent.ComponentPositionUtils.textCenteredStartX;

public class ProcessScreen extends TextScreen {

    int spacing = 0;
    boolean showFuel = false;

    public ProcessScreen(Screen screen, BookPage page, boolean hasTitle, boolean showPageMsg, int spacing, boolean showFuel) {
        super(screen, page, hasTitle, showPageMsg, 0, (showFuel ? QUEST_IMAGE_HEIGHT : 0) + QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
        this.spacing = spacing;
        this.showFuel = showFuel;
    }

    public ProcessScreen(Screen screen, BookPage page, boolean hasTitle, boolean showPageMsg, int spacing) {
        super(screen, page, hasTitle, showPageMsg, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
        this.spacing = spacing;
    }

    public ProcessScreen(Screen screen, BookPage page) {
        super(screen, page, false, true);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        ImageComponent.ImageBuilder()
                .setImage(page.image())
                .setOffset(-spacing, 0)
                .render(guiGraphics, screen, page);

        ImageComponent.ImageBuilder()
                .setImage(page.image(1))
                .regular()
                .setDimensions(24, 16)
                .borderless()
                .render(guiGraphics, screen, page);

        ImageComponent.ImageBuilder()
                .setImage(page.image(2))
                .setOffset(spacing + 8, 0)
                .render(guiGraphics, screen, page);

        if (showFuel){
            ImageComponent.ImageBuilder()
                    .setImage(page.image(3))
                    .regular()
                    .setDimensions(10, 10)
                    .setOffset(5, QUEST_IMAGE_HEIGHT)
                    .render(guiGraphics, screen, page);
        }
    }
}
