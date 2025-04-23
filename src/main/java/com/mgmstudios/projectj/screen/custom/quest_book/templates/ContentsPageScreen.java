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

public class ContentsPageScreen extends EmptyScreen {

    int spacing = 0;
    boolean showPageMsg;
    private List<AbstractWidget> widgets;

    public ContentsPageScreen(QuestBookScreen screen, BookPage page, boolean showPageMsg, int spacing, List<ContentsPageEntry> entries) {
        super(screen, page, showPageMsg);
        this.spacing = spacing;
        this.screen = screen;
        this.showPageMsg = showPageMsg;
        widgets = new ArrayList<>();

        // Maybe fixed position list? Items are currently aligned differently depending on screen size (fullscreen vs small)
        int nextXPos = AbstractComponent.ComponentPositionUtils.textStartX(screen);
        int nextYPos = PAGE_TEXT_Y_OFFSET;
        for (ContentsPageEntry e : entries){
            JumpToButton button = new JumpToButton((p) -> {
                System.out.println("Going to page: " + e.connectedPage);
                screen.setPage(e.connectedPage);
            }, e.displayItem, nextXPos, nextYPos, true);
            nextXPos += 20;
            if (nextXPos >= BACKGROUND_TEXTURE_WIDTH){
                nextXPos %= BACKGROUND_TEXTURE_WIDTH;
                nextXPos = AbstractComponent.ComponentPositionUtils.textStartX(screen);
                nextYPos += 20;
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
        private int connectedPage;

        public ContentsPageEntry(ItemLike displayItem, int connectedPage){
            this.displayItem = displayItem;
            this.connectedPage = connectedPage;
        }

        public static ContentsPageEntry createWidget(ItemLike displayItem, int connectedPage){
            return new ContentsPageEntry(displayItem, connectedPage);
        }
    }

}
