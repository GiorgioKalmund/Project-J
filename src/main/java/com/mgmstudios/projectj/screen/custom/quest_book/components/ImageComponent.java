package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent.ComponentPositionUtils.*;
import static com.mgmstudios.projectj.util.ItemLookup.getStack;

public class ImageComponent extends AbstractComponent{

    protected QuestBookScreen.QuestBookImage questBookImage;

    protected float borderScaleFactor = 1F;

    @Override
    public void render(GuiGraphics guiGraphics, Screen screen, BookPage page) {
        Font font = screen.getFont();
        int width = screen.width;
        int pageMsLen = font.width(page.pageMsg());

        int centeredPosition = imageWidthCenter(screen) - pageMsLen + IMAGE_WIDTH - 44;

        if (!questBookImage.isEmpty()){
            guiGraphics.renderItem(getStack(questBookImage.resourceLocation()), centeredPosition + x , IMAGE_Y_OFFSET + y);
            if (questBookImage.showBorder()){
                int scaledQuestBookDisplayWidth = (int)(QUEST_BORDER_IMAGE_WIDTH * borderScaleFactor);
                int scaledQBookDisplayHeight = (int)(QUEST_IMAGE_HEIGHT * borderScaleFactor);
                guiGraphics.blit(RenderType::guiTextured, IMAGE_BORDER_LOCATION, (width - scaledQuestBookDisplayWidth) / 2 - 5 + x, IMAGE_Y_OFFSET - (scaledQBookDisplayHeight - QUEST_IMAGE_HEIGHT) / 2 + y, 0.0F, 0.0F, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight);
            }
        }
    }

    public static ImageComponent ImageBuilder(){
        return new ImageComponent();
    }

    @Override
    public ImageComponent setOffset(int x, int y) {
        super.setOffset(x, y);
        return this;
    }

    public ImageComponent setImage(QuestBookImage questBookImage){
        this.questBookImage = questBookImage;
        return this;
    }

    public QuestBookImage image(){
        return this.questBookImage;
    }

    public ImageComponent setBorderScale(float scale){
        this.borderScaleFactor = scale;
        return this;
    }
}
