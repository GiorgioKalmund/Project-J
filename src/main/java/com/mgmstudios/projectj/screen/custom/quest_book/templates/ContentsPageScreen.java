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


    public ContentsPageScreen(QuestBookScreen screen, BookPage page, boolean showPageMsg, int spacing, List<ContentsPageEntry> entries) {
        super(screen, page, showPageMsg,false, true);

        int Y_OFFSET = screen.getFont().lineHeight + 4;

        // TODO: Maybe fixed position list? Items are currently aligned differently depending on screen size (fullscreen vs small)
        int nextXPos = AbstractComponent.ComponentPositionUtils.textStartX(screen) + 10;
        int nextYPos = PAGE_TEXT_Y_OFFSET + Y_OFFSET;
        for (ContentsPageEntry e : entries){
            JumpToButton button = new JumpToButton((p) -> {
                if (e.connectedPage != -1)
                    screen.setPage(e.connectedPage);
                else{
                    var pageToGo = screen.pageShortcutMap.getOrDefault(e.connectedShortcut, (screen.currentPage()));
                    if (pageToGo == screen.currentPage())
                        System.err.println(e.connectedShortcut + " is not a valid / connected shortcut!");
                    screen.setPage(pageToGo);
                }
            }, e.displayItem, e.displayText, nextXPos, nextYPos, true, screen);
            nextXPos += spacing;
            if (nextXPos >= BACKGROUND_TEXTURE_WIDTH + spacing / 2){
                nextXPos = AbstractComponent.ComponentPositionUtils.textStartX(screen) + 10;
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
        private String connectedShortcut;

        public ContentsPageEntry(ItemLike displayItem, String displayText, int connectedPage){
            this.displayItem = displayItem;
            this.connectedPage = connectedPage;
            this.displayText = displayText;
            this.connectedShortcut = null;
        }
        public ContentsPageEntry(ItemLike displayItem, String displayText, String connectedShortcut){
            this.displayItem = displayItem;
            this.connectedPage = -1;
            this.displayText = displayText;
            this.connectedShortcut = connectedShortcut;
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
        public void connectedShortcut(String connectedShortcut) {
            this.connectedShortcut = connectedShortcut;
        }

        public String connectedShortcut(){
            return connectedShortcut;
        }

        public static ContentsPageEntry createWidget(ItemLike displayItem, String displayText, int connectedPage){
            return new ContentsPageEntry(displayItem, displayText, connectedPage);
        }
        public static ContentsPageEntry createWidget(ItemLike displayItem, int connectedPage){
            return new ContentsPageEntry(displayItem, "", connectedPage);
        }
    }

}
