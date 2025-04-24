package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class CoverScreen extends TextScreen{

    public CoverScreen(Screen screen) {
        this(screen, BookPage.empty(), false, false);
    }

    public CoverScreen(Screen screen, BookPage bookPage, boolean showPageMsg) {
        this(screen, bookPage, false, showPageMsg);
    }

    public CoverScreen(Screen screen, BookPage page) {
        this(screen, page, false, false);
    }

    public CoverScreen(Screen screen, BookPage page, boolean hasTitle, boolean showPageMsg) {
        super(screen, page, hasTitle, showPageMsg);
    }

    public static final ResourceLocation COVER_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/quest_book_cover.png");

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
}
