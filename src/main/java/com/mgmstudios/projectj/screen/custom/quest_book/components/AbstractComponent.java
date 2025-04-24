package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.joml.Vector2i;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.IMAGE_HEIGHT;
import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.IMAGE_WIDTH;

public abstract class AbstractComponent {
    int x;
    int y;

    public abstract void render(GuiGraphics guiGraphics, Screen screen, BookPage page);

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public AbstractComponent setAdditionalOffset(int x, int y){
        setX(x);
        setY(y);
        return this;
    }

    public static class ComponentPositionUtils{

        public static Vector2i getBookTopRightCorner(Screen screen, int xPushback){
            return new Vector2i( imageWidthCenter(screen) - xPushback + 192 - 44, 18);
        }
        public static Vector2i getBookTopRightCorner(Screen screen){
            return getBookTopRightCorner(screen, 0);
        }

        public static int textStartX(Screen screen){
            return imageWidthCenter(screen) + 36;
        }

        public static int textCenteredStartX(Screen screen){
            return imageWidthCenter(screen) - 59 + IMAGE_WIDTH - 44;
        }

        protected static int imageWidthCenter(Screen screen){
            int width = screen.width;
            return (width - IMAGE_WIDTH) / 2;
        }

        public static int imageHeightCenter(Screen screen){
            int height = screen.height;
            return (height - IMAGE_HEIGHT) / 2;
        }
    }
}
