package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.QuestBookTemplate;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.FormattedCharSequence;

import java.awt.*;
import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.drawCenteredStringWithoutDropShadow;
import static com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent.ComponentPositionUtils.*;

public class TextComponent extends AbstractComponent{

    protected List<FormattedCharSequence> textComponents;
    protected boolean hasTitle;

    public int color = 0;

    @Override
    public void render(GuiGraphics guiGraphics, Screen screen, BookPage page) {
        Font font = screen.getFont();

        if (textComponents != null){
            int components = Math.min(14, textComponents.size());
            for(int component = 0; component < components; ++component) {
                FormattedCharSequence formattedcharsequence = textComponents.get(component);
                if (hasTitle && 0 == component){
                    drawCenteredStringWithoutDropShadow(guiGraphics, font, formattedcharsequence,  textCenteredStartX(screen) + x, 32 + y, color);
                } else {
                    guiGraphics.drawString(font, formattedcharsequence, textStartX(screen) + x, (32 + component * 9) + y, 0, false);
                }
            }
        }
    }

    public TextComponent setTextComponents(List<FormattedCharSequence> textComponents) {
        this.textComponents = textComponents;
        return this;
    }
    public TextComponent hasTitle(boolean hasTitle){
        this.hasTitle = hasTitle;
        return this;
    }

    public TextComponent color(int color){
        this.color = color;
        return this;
    }

    @Override
    public TextComponent setOffset(int x, int y) {
        super.setOffset(x, y);
        return this;
    }

    public static TextComponent TextBuilder(){
        return new TextComponent();
    }
}
