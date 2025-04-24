package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent;
import com.mgmstudios.projectj.screen.custom.quest_book.components.ImageComponent;
import com.mgmstudios.projectj.screen.custom.quest_book.components.JumpToButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;

public class ContentsPageScreen extends TextScreen {

    private final int Y_OFFSET;

    public ContentsPageScreen(QuestBookScreen screen, BookPage page, boolean showPageMsg, int spacing, List<ContentsPageEntry> entries) {
        super(screen, page, showPageMsg, true);
        this.screen = screen;
        this.showPageMsg = showPageMsg;
        System.out.println("PAGE LOADED WITH SPACING: " + spacing);

        Y_OFFSET = screen.getFont().lineHeight + 4;

        // Maybe fixed position list? Items are currently aligned differently depending on screen size (fullscreen vs small)
        int nextXPos = AbstractComponent.ComponentPositionUtils.textStartX(screen);
        int nextYPos = PAGE_TEXT_Y_OFFSET + Y_OFFSET;
        for (ContentsPageEntry e : entries){
            JumpToButton button = new JumpToButton((p) -> {
                System.out.println("Going to page: " + e.connectedPage);
                screen.setPage(e.connectedPage);
            }, e.displayItem, e.displayText, nextXPos, nextYPos, true, screen);
            nextXPos += spacing;
            if (nextXPos >= BACKGROUND_TEXTURE_WIDTH){
                nextXPos = AbstractComponent.ComponentPositionUtils.textStartX(screen);
                nextYPos += spacing;
            }
            screen.addTemporaryWidget(button);
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    public static class ContentsPageEntry{
        protected ItemLike displayItem;
        protected String displayText;
        private int connectedPage;

        public ContentsPageEntry(ItemLike displayItem, String displayText, int connectedPage){
            this.displayItem = displayItem;
            this.connectedPage = connectedPage;
            this.displayText = displayText;
        }
        public ContentsPageEntry(ItemLike displayItem, int connectedPage){
            this(displayItem, "", connectedPage);
        }

        public ItemLike displayItem() {
            return displayItem;
        }
        public void displayItem(ItemLike itemLike) {
            this.displayItem = itemLike;
        }

        public String displayText(){
            return displayText;
        }

        public void displayText(String displayText){
            this.displayText = displayText;
        }

        public int connectedPage() {
            return connectedPage;
        }

        public void connectedPage(int connectedPage) {
            this.connectedPage = connectedPage;
        }

        public static ContentsPageEntry createWidget(ItemLike displayItem, String displayText, int connectedPage){
            return new ContentsPageEntry(displayItem, displayText, connectedPage);
        }
        public static ContentsPageEntry createWidget(ItemLike displayItem, int connectedPage){
            return new ContentsPageEntry(displayItem, "", connectedPage);
        }
    }

}
