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
        super(screen, BookPage.empty(), false, true, false);
    }

    public CoverScreen(Screen screen, BookPage page) {
        super(screen, page, false, true, false);
    }

    public CoverScreen(Screen screen, BookPage page, boolean showPageMsg) {
        super(screen, page, false, true, showPageMsg);
    }

    public static final ResourceLocation COVER_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/quest_book_cover.png");

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public ResourceLocation getBackdropImage() {
        return COVER_PAGE_LOCATION;
    }
}
