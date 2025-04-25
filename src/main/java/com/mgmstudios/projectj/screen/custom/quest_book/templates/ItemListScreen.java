package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.components.ImageComponent;
import com.mgmstudios.projectj.screen.custom.quest_book.components.SpacingScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.components.StackImageComponent;
import com.mgmstudios.projectj.util.ItemLookup;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;

public class ItemListScreen extends SpacingScreen {

    protected int maxListLength = 16;

    public ItemListScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, int spacing) {
        super(screen, page, showTitle, showPageMsg, false, 0, QUEST_IMAGE_HEIGHT + screen.getFont().lineHeight, spacing);
    }

    public ItemListScreen(Screen screen, BookPage page) {
        super(screen, page, false, true, false, 0);
    }

    public ItemListScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, boolean alignCenter, int offsetX, int offsetY, int spacing) {
        super(screen, page, showTitle, showPageMsg, alignCenter, offsetX, offsetY,spacing);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        int imageCount = Math.min(getMaxListLength(), page.images().size());
        for (int imageIndex = 0; imageIndex < imageCount; imageIndex++){
            renderListStackImage(guiGraphics, page, screen, imageCount, spacing, imageIndex, mouseX, mouseY, 0, 0);
        }
    }

    public int getMaxListLength() {
        return maxListLength;
    }

    public static void renderListStackImage(GuiGraphics guiGraphics, BookPage page, Screen screen, int imageCount, int spacing, int imageIndex,  int mouseX, int mouseY, int xOff, int yOff){
        int xOffset = getListXOffset(imageCount, spacing, imageIndex);
        renderListStackImage(guiGraphics, imageIndex, page, screen, mouseX, mouseY, xOff + xOffset, yOff);
    }

    public static void renderListStackImage(GuiGraphics guiGraphics, QuestBookImage image, BookPage page, Screen screen, int mouseX, int mouseY, int xOff, int yOff){
        ImageComponent imageComponent = StackImageComponent.StackImageBuilder()
                .setImage(image)
                .setBorderScale(1.3F)
                .setAdditionalOffset(xOff, yOff);
        if (imageComponent instanceof StackImageComponent stackImageComponent){
            stackImageComponent.renderToolTip(guiGraphics, screen, ItemLookup.getStack(image.resourceLocation()), mouseX, mouseY);
        }
        imageComponent.render(guiGraphics, screen, page);
    }
    public static void renderListStackImage(GuiGraphics guiGraphics, int imageIndex, BookPage page, Screen screen, int mouseX, int mouseY, int xOff, int yOff){
        QuestBookImage image = page.image(imageIndex);
        renderListStackImage(guiGraphics, image, page, screen, mouseX, mouseY, xOff, yOff);
    }

    public static int getListXOffset(int elementCount, int spacing, int currentIndex){
        int evenOffset = spacing / 2;
        int offset = 0;
        int direction = -1;
        boolean newRound = currentIndex % 2 == 0;
        if (newRound){
            direction = 1;
        }
        offset = spacing * ((elementCount % 2 == 0 ? currentIndex : currentIndex + 1) / 2);
        if (elementCount % 2 == 0){
            offset += evenOffset;
        }

        return offset * direction;
    }


}
