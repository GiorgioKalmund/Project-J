package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class QuestBookScreenTemplate {

    protected Screen screen;
    protected BookPage page;
    public QuestBookScreenTemplate(Screen screen, BookPage page){
        this.screen = screen;
        this.page = page;
    }

    public BookPage page() {
        return page;
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

    }

    public ResourceLocation getBackdropImage(){
        return QuestBookScreen.BOOK_LOCATION;
    }

    public boolean isEmpty(){
        return page == null && screen == null;
    }

    public static QuestBookScreenTemplate EMPTY = new QuestBookScreenTemplate(null, BookPage.EMPTY);
}
