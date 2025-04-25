package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.SpacingScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.screen.custom.quest_book.templates.ItemListScreen.renderListStackImage;

public class RecipeListScreen extends SpacingScreen {


    public static int INGREDIENTS_Y_OFFSET = 20;

    public RecipeListScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, int spacing) {
        super(screen, page, showTitle, showPageMsg, false, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight + INGREDIENTS_Y_OFFSET, spacing);
    }

    public RecipeListScreen(Screen screen, BookPage page) {
        super(screen, page, false, true, false, 0);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        renderListStackImage(guiGraphics, page.secondaryImages().getFirst(), page, screen,  mouseX, mouseY, 0, 0);

        int imageCount = page.images().size();
        for (int imageIndex = 0; imageIndex < imageCount; imageIndex++){
            renderListStackImage(guiGraphics,  page, screen, imageCount, spacing, imageIndex, mouseX, mouseY, 0, INGREDIENTS_Y_OFFSET);
        }


    }
}
