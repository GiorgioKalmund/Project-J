package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.TextScreen;
import net.minecraft.client.gui.screens.Screen;

public abstract class SpacingScreen extends TextScreen {

    protected int spacing = 0;
    public SpacingScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, boolean alignCenter, int offsetX, int offsetY, int spacing) {
        super(screen, page, showTitle, showPageMsg, alignCenter, offsetX, offsetY);
        this.spacing = spacing;
    }

    public SpacingScreen(Screen screen, BookPage page, boolean showTitle, boolean showPageMsg, boolean alignCenter, int spacing) {
        super(screen, page, showTitle, showPageMsg, alignCenter);
    }
}
