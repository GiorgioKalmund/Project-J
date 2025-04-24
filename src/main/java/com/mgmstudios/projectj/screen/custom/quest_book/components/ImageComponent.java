package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector2i;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent.ComponentPositionUtils.*;
import static com.mgmstudios.projectj.util.ItemLookup.getStack;

public class ImageComponent extends AbstractComponent{

    protected QuestBookScreen.QuestBookImage questBookImage;

    protected float borderScaleFactor = 1F;

    protected Vector2i dimensions = new Vector2i(QUEST_IMAGE_WIDTH, QUEST_IMAGE_HEIGHT);

    @Override
    public void render(GuiGraphics guiGraphics, Screen screen, BookPage page) {
        Font font = screen.getFont();
        int width = screen.width;
        int pageMsLen = font.width(page.pageMsg());

        int centeredPosition = imageWidthCenter(screen) - 65 - 44 + IMAGE_WIDTH;

        if (!questBookImage.isEmpty()){

            if (questBookImage.type().equals(QuestBookImage.Type.ITEM)){
                guiGraphics.renderItem(getStack(questBookImage.resourceLocation()), centeredPosition + x , IMAGE_Y_OFFSET + y);
            } else if (questBookImage.type().equals(QuestBookImage.Type.SPRITE)){
                guiGraphics.blitSprite(RenderType::guiTextured, questBookImage.resourceLocation(), centeredPosition + x, IMAGE_Y_OFFSET + y,  dimensions.x, dimensions.y);
            } else {
                guiGraphics.blit(RenderType::guiTextured, questBookImage.resourceLocation(), centeredPosition + x, IMAGE_Y_OFFSET + y, 0.0F, 0.0F, dimensions.x, dimensions.y, dimensions.x, dimensions.y);
            }

            if (questBookImage.showBorder()){
                // TODO: Properly calculate displacement based on scale and dimensions as it still looks weird sometimes
                int scaledQuestBookDisplayWidth = (int)(QUEST_BORDER_IMAGE_WIDTH * borderScaleFactor * ((float) dimensions.x / QUEST_IMAGE_WIDTH));
                int scaledQBookDisplayHeight = (int)(QUEST_IMAGE_HEIGHT * borderScaleFactor * ((float) dimensions.y / QUEST_IMAGE_HEIGHT));
                guiGraphics.blit(RenderType::guiTextured, IMAGE_BORDER_LOCATION, (width - scaledQuestBookDisplayWidth) / 2 - 5 + x, IMAGE_Y_OFFSET - (scaledQBookDisplayHeight - QUEST_IMAGE_HEIGHT) / 2 + y, 0.0F, 0.0F, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight);
            }
        }
    }

    public static ImageComponent ImageBuilder(){
        return new ImageComponent();
    }

    @Override
    public ImageComponent setAdditionalOffset(int x, int y) {
        super.setAdditionalOffset(x, y);
        return this;
    }

    public ImageComponent setType(QuestBookImage.Type type){
        questBookImage.setType(type);
        return this;
    }

    public ImageComponent regular(){
        this.questBookImage.regular();
        return this;
    }

    public ImageComponent sprite(){
        this.questBookImage.sprite();
        return this;
    }

    public ImageComponent setImage(QuestBookImage questBookImage){
        this.questBookImage = questBookImage;
        return this;
    }

    public ImageComponent size(int width, int height){
        this.dimensions = new Vector2i(width, height);
        return this;
    }

     public ImageComponent showBorder(boolean border){
        this.questBookImage.showBorder(border);
        return this;
    }

    public ImageComponent bordered(){
        return showBorder(true);
    }

    public ImageComponent borderless(){
        return showBorder(false);
    }

    public QuestBookImage image(){
        return this.questBookImage;
    }

    public ImageComponent setBorderScale(float scale){
        this.borderScaleFactor = scale;
        return this;
    }

}
