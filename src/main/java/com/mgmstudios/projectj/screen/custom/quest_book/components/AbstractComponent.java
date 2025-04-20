package com.mgmstudios.projectj.screen.custom.quest_book.components;

import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.QuestBookTemplate;
import com.mojang.blaze3d.buffers.GpuBuffer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.util.function.Function;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.IMAGE_WIDTH;

public abstract class AbstractComponent {
    int x;
    int y;

    public abstract void render(GuiGraphics guiGraphics, Screen screen, BookPage page);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public AbstractComponent setOffset(int x, int y){
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
    }
}
