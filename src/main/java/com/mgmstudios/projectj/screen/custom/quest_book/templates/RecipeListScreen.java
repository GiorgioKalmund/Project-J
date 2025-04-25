package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent;
import com.mgmstudios.projectj.screen.custom.quest_book.components.SpacingScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.components.StackImageComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.joml.Vector2i;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.screen.custom.quest_book.templates.ItemListScreen.renderListStackImage;

public class RecipeListScreen extends SpacingScreen {


    public static int INGREDIENTS_Y_OFFSET = 20;
    Vector2i recipeBlockRenderPos;

    public RecipeListScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, int spacing) {
        super(screen, page, showTitle, showPageMsg, false, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight + INGREDIENTS_Y_OFFSET, spacing);
         recipeBlockRenderPos = AbstractComponent.ComponentPositionUtils.getBookTopRightCorner(screen);
    }

    public RecipeListScreen(Screen screen, BookPage page) {
        super(screen, page, false, true, false, 0);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        renderListStackImage(guiGraphics, getOrDefault(page.secondaryImages(), 0, QuestBookImage.unavailable()), page, screen,  mouseX, mouseY, 0, 0);
        renderListStackImage(guiGraphics, getOrDefault(page.secondaryImages(), 1, QuestBookImage.unavailable()), page, screen,  mouseX, mouseY, PAGE_TEXT_X_OFFSET + 16, -PAGE_TEXT_Y_OFFSET + 8);

        int imageCount = page.images().size();
        for (int imageIndex = 0; imageIndex < imageCount; imageIndex++){
            renderListStackImage(guiGraphics,  page, screen, imageCount, spacing, imageIndex, mouseX, mouseY, 0, INGREDIENTS_Y_OFFSET);
        }


    }
}
