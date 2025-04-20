package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookImage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.util.ItemLookup.getStack;

public class ItemShowcaseScreen extends QuestBookTemplate{

    QuestBookImage questBookImage;
    boolean hasTitle;

    public ItemShowcaseScreen(Screen screen, Component pageMsg, QuestBookImage image, boolean hasTitle) {
        super(screen, pageMsg);
        this.questBookImage = image;
        this.hasTitle = hasTitle;
    }

    public ItemShowcaseScreen(Screen screen, Component pageMsg, QuestBookImage image) {
        super(screen, pageMsg);
        this.questBookImage = image;
        this.hasTitle = false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, List<FormattedCharSequence> cachedPageComponents, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, cachedPageComponents, mouseX, mouseY, partialTicks);
        Font font = screen.getFont();
        int width = screen.width;

        int imageWidthCenter = (width - IMAGE_WIDTH) / 2;

        int pageMsLen = font.width(this.pageMsg);
        guiGraphics.drawString(font, this.pageMsg, imageWidthCenter - pageMsLen + 192 - 44, 18, 0, false);

        if (pageMsLen == 0)
            pageMsLen = 59;

        int titleIndex = 0;
        if (hasTitle && !questBookImage.isEmpty()){
            titleIndex = (QUEST_IMAGE_HEIGHT / font.lineHeight) + 2;
        }
        int components = Math.min(14, cachedPageComponents.size());
        for(int component = 0; component < components; ++component) {
            FormattedCharSequence formattedcharsequence = cachedPageComponents.get(component);
            if (hasTitle && titleIndex == component){
                drawCenteredStringWithoutDropShadow(guiGraphics, font, formattedcharsequence,  imageWidthCenter - pageMsLen + IMAGE_WIDTH - 44, 32 + component * 9, 0);
            } else {
                guiGraphics.drawString(font, formattedcharsequence, imageWidthCenter + 36, 32 + component * 9, 0, false);
            }
        }

        int centeredPosition = imageWidthCenter - pageMsLen + IMAGE_WIDTH - 50;
        if (!questBookImage.isEmpty()){
            guiGraphics.renderItem(getStack(questBookImage.resourceLocation()), centeredPosition , IMAGE_Y_OFFSET);
            if (questBookImage.showBorder()){
                float scaleFactor = 1.3F;
                int scaledQuestBookDisplayWidth = (int)(QUEST_BORDER_IMAGE_WIDTH * scaleFactor);
                int scaledQBookDisplayHeight = (int)(QUEST_IMAGE_HEIGHT * scaleFactor);
                guiGraphics.blit(RenderType::guiTextured, IMAGE_BORDER_LOCATION, (width - scaledQuestBookDisplayWidth) / 2 - 5, IMAGE_Y_OFFSET - (scaledQBookDisplayHeight - QUEST_IMAGE_HEIGHT) / 2, 0.0F, 0.0F, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight);
            }
        }
    }
}
