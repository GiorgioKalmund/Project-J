package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.drawCenteredStringWithoutDropShadow;
import static com.mgmstudios.projectj.screen.custom.quest_book.components.AbstractComponent.ComponentPositionUtils.*;

public class TextComponent extends AbstractComponent{

    protected List<FormattedCharSequence> textComponents;
    protected boolean showTitle;
    protected boolean alignCenter = false;

    public int color = 0;

    @Override
    public void render(GuiGraphics guiGraphics, Screen screen, BookPage page) {
        Font font = screen.getFont();

        if (textComponents != null){
            int components = Math.min(14, textComponents.size());
            for(int component = 0; component < components; ++component) {
                FormattedCharSequence formattedcharsequence = textComponents.get(component);
                if ((showTitle && 0 == component) || alignCenter){
                    drawCenteredStringWithoutDropShadow(guiGraphics, font, formattedcharsequence,  textCenteredStartX(screen) + x, (32 + component * 9) + y, color);
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

    public TextComponent showTitle(boolean showTitle){
        this.showTitle = showTitle;
        return this;
    }

    public TextComponent color(int color){
        this.color = color;
        return this;
    }

    public TextComponent alignCenter(){
        return this.alignCenter(true);
    }

    public TextComponent alignCenter(boolean alignCenter){
        this.alignCenter = alignCenter;
        return this;
    }

    @Override
    public TextComponent setAdditionalOffset(int x, int y) {
        super.setAdditionalOffset(x, y);
        return this;
    }

    public static TextComponent TextBuilder(){
        return new TextComponent();
    }
}
