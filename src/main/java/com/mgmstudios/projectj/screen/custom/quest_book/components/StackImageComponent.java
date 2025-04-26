package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2i;

import java.util.List;
import java.util.Optional;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;
import static com.mgmstudios.projectj.util.ItemLookup.getStack;

public class StackImageComponent extends ImageComponent{

    protected Vector2i countOffset = new Vector2i(QUEST_IMAGE_WIDTH / 2 + 2, QUEST_IMAGE_HEIGHT / 2 + 1);

    @Override
    public void render(GuiGraphics guiGraphics, Screen screen, BookPage page) {
        calculateCenterIfNeeded(screen);
        if (questBookImage.count() > 1){
            if (questBookImage.count() > 9)
            {
                countOffset.x -= 5;
            }
            guiGraphics.pose().translate(0, 0, 250);
            guiGraphics.drawString(screen.getFont(), "§6" + questBookImage.count() + "§r", centeredPosition + x + countOffset.x , IMAGE_Y_OFFSET + y +  countOffset.y,0, true);
            guiGraphics.pose().translate(0, 0, -250);
        }
       super.render(guiGraphics, screen, page);
    }

    public StackImageComponent setStackCountOffset(int x, int y){
        this.countOffset = new Vector2i(x, y);
        return this;
    }

    public static StackImageComponent StackImageBuilder(){
        return new StackImageComponent();
    }


    public void renderToolTip(GuiGraphics guiGraphics, Screen screen, ItemStack itemStack, int mouseX, int mouseY){
        calculateCenterIfNeeded(screen);
        if (isHovering(mouseX, mouseY, screen)){
            guiGraphics.renderTooltip(screen.getFont(),itemStack, getX(), getY() + dimensions.y * 2);
        }
    }

    private boolean isHovering(double mouseX, double mouseY, Screen screen) {
        calculateCenterIfNeeded(screen);
        int renderX = getX();
        int renderY = getY();
        return mouseX >= renderX && mouseX < (renderX + dimensions.x)
                && mouseY >= renderY && mouseY < (renderY + dimensions.y);
    }


    @Override
    public int getX() {
        return super.getX() + centeredPosition;
    }

    @Override
    public int getY() {
        return super.getY() + IMAGE_Y_OFFSET;
    }
}
