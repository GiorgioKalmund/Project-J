package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class QuestBookTemplate {

    protected Component pageMsg;
    protected Screen screen;
    public QuestBookTemplate(Screen screen, Component pageMsg){
        this.pageMsg = pageMsg;
        this.screen = screen;
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

    }

    public void render(GuiGraphics graphics, List<FormattedCharSequence> components, int mouseX, int mouseY, float partialTicks) {
    }
}
