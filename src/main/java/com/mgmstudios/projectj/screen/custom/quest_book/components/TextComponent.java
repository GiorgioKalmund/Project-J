package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.QuestBookTemplate;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.IMAGE_WIDTH;
import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.drawCenteredStringWithoutDropShadow;

public class TextComponent extends AbstractComponent{

    protected List<FormattedCharSequence> textComponents;
    protected boolean hasTitle;

    @Override
    public void render(GuiGraphics guiGraphics, Screen screen, BookPage page) {
        System.out.println(hasTitle + "rendering: " + page.components().size() + "with offsets: " + x + " & " + y);
        Font font = screen.getFont();
        int width = screen.width;

        int imageWidthCenter = (width - IMAGE_WIDTH) / 2;

        if (textComponents != null){
            int components = Math.min(14, textComponents.size());
            for(int component = 0; component < components; ++component) {
                FormattedCharSequence formattedcharsequence = textComponents.get(component);
                if (hasTitle && 0 == component){
                    drawCenteredStringWithoutDropShadow(guiGraphics, font, formattedcharsequence,  (imageWidthCenter - 59 + IMAGE_WIDTH - 44 ) + x, 32 + y, 0);
                } else {
                    guiGraphics.drawString(font, formattedcharsequence, imageWidthCenter + 36 + x, (32 + component * 9) + y, 0, false);
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

    @Override
    public TextComponent setOffset(int x, int y) {
        super.setOffset(x, y);
        return this;
    }

    public static TextComponent TextBuilder(){
        return new TextComponent();
    }
}
