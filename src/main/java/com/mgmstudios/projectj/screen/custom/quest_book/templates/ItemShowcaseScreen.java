package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.util.ItemLookup.getStack;

public class ItemShowcaseScreen extends TextScreen {


    public ItemShowcaseScreen(Screen screen, BookPage page, boolean hasTitle) {
        super(screen, page, hasTitle, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight);
    }

    public ItemShowcaseScreen(Screen screen, BookPage page) {
        super(screen, page, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        Font font = screen.getFont();
        int width = screen.width;
        int imageWidthCenter = (width - IMAGE_WIDTH) / 2;
        int pageMsLen = font.width(page.pageMsg());

        int centeredPosition = imageWidthCenter - pageMsLen + IMAGE_WIDTH - 50;
        if (!page.pageImage().isEmpty()){
            guiGraphics.renderItem(getStack(page.pageImage().resourceLocation()), centeredPosition , IMAGE_Y_OFFSET);
            if (page.pageImage().showBorder()){
                float scaleFactor = 1.3F;
                int scaledQuestBookDisplayWidth = (int)(QUEST_BORDER_IMAGE_WIDTH * scaleFactor);
                int scaledQBookDisplayHeight = (int)(QUEST_IMAGE_HEIGHT * scaleFactor);
                guiGraphics.blit(RenderType::guiTextured, IMAGE_BORDER_LOCATION, (width - scaledQuestBookDisplayWidth) / 2 - 5, IMAGE_Y_OFFSET - (scaledQBookDisplayHeight - QUEST_IMAGE_HEIGHT) / 2, 0.0F, 0.0F, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight);
            }
        }
    }
}
