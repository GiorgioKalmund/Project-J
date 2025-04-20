package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.QuestBookTemplate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Function;

public abstract class AbstractComponent {
    int x;
    int y;

    public abstract void render(GuiGraphics guiGraphics, Screen screen, BookPage page);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public AbstractComponent setOffset(int x, int y){
        setX(x);
        setY(y);
        return this;
    }
}
