package com.mgmstudios.projectj.screen.custom.quest_book.pages;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;

public class CoverScreen extends Screen {
    public CoverScreen(Component title) {
        super(title);
    }

    public static final ResourceLocation COVER_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/quest_book_cover.png");
}
