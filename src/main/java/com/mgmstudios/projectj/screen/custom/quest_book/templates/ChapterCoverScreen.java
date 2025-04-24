package com.mgmstudios.projectj.screen.custom.quest_book.templates;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.quest_book.BookPage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.components.ImageComponent;
import com.mgmstudios.projectj.screen.custom.quest_book.components.TextComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.*;

public class ChapterCoverScreen extends TextScreen {

    private final int imageHeight;
    private final int imageWidth;
    private final List<FormattedCharSequence> chapterTitle;

    public ChapterCoverScreen(QuestBookScreen screen, BookPage page, String chapterTitle, boolean showPageMsg,boolean alignCenter, int imageHeight, int imageWidth) {
        super(screen, page, showPageMsg, showPageMsg, alignCenter, 0, imageHeight * 2 + screen.getFont().lineHeight + 4);
        this.chapterTitle = screen.getFont().split(FormattedText.of(chapterTitle), TEXT_WIDTH);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public ChapterCoverScreen(QuestBookScreen screen, BookPage page, String chapterTitle, boolean showPageMsg, boolean alignCenter) {
        this(screen, page, chapterTitle, showPageMsg, alignCenter, 32, 32);
    }

    public static final ResourceLocation CHAPTER_COVER_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/quest_book_chapter_cover.png");
    @Override
    public ResourceLocation getBackdropImage() {
        return CHAPTER_COVER_PAGE_LOCATION;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        TextComponent.TextBuilder()
                .alignCenter()
                .setTextComponents(List.of(chapterTitle.getFirst()))
                .render(guiGraphics, screen, page);

        ImageComponent.ImageBuilder()
                .setImage(page.image())
                .size(imageWidth, imageHeight)
                .setAdditionalOffset(-6, imageHeight - 16)
                .setBorderScale(1.3F)
                .render(guiGraphics, screen, page);
    }
}
