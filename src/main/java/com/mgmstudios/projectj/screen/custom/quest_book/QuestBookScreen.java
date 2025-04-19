package com.mgmstudios.projectj.screen.custom.quest_book;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.quest_book.pages.CoverScreen;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen.BookAccess;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static com.mgmstudios.projectj.util.ItemLookup.getStack;
import static com.mgmstudios.projectj.util.ItemLookup.resourceLocationFromString;

public class QuestBookScreen extends Screen {

    public static final int PAGE_INDICATOR_TEXT_Y_OFFSET = 16;
    public static final int PAGE_TEXT_X_OFFSET = 36;
    public static final int PAGE_TEXT_Y_OFFSET = 30;
    public static final int BACKGROUND_TEXTURE_WIDTH = 256;
    public static final int BACKGROUND_TEXTURE_HEIGHT = 256;
    public static final BookAccess EMPTY_ACCESS = new BookAccess(List.of());
    public static final ResourceLocation BOOK_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/quest_book.png");
    public static final ResourceLocation IMAGE_BORDER_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/image_border.png");
    protected static final int TEXT_WIDTH = 114;
    protected static final int TEXT_HEIGHT = 128;
    public static final int IMAGE_WIDTH = 192;
    public static final int IMAGE_HEIGHT = 192;

    protected static final int IMAGE_Y_OFFSET = 36;
    private BookAccess bookAccess;
    private int currentPage;
    private List<FormattedCharSequence> cachedPageComponents;
    private int cachedPage;
    private Component pageMsg;
    private PageButton forwardButton;
    private PageButton backButton;
    private final boolean playTurnSound;
    private QuestBookImage questBookImage = new QuestBookImage();

    protected static final int QUEST_IMAGE_WIDTH = 16;
    protected static final int QUEST_BORDER_IMAGE_WIDTH = 48;
    protected static final int QUEST_IMAGE_HEIGHT = 16;

    protected boolean hasTitle = false;
    protected Screen screenToShow = null;
    public QuestBookScreen(BookAccess bookAccess) {
        this(bookAccess, true);
    }

    public QuestBookScreen() {
        this(EMPTY_ACCESS, false);
    }

    private QuestBookScreen(BookAccess bookAccess, boolean playTurnSound) {
        super(GameNarrator.NO_TITLE);
        this.cachedPageComponents = Collections.emptyList();
        this.cachedPage = -1;
        this.pageMsg = CommonComponents.EMPTY;
        this.bookAccess = bookAccess;
        this.playTurnSound = playTurnSound;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int imageWidthCenter = (this.width - IMAGE_WIDTH) / 2;

        if (this.cachedPage != this.currentPage) {

            FormattedText formattedText = this.bookAccess.getPage(this.currentPage);
            String formattedString = formattedText.getString();
            if (formattedString.contains("<cover>")){
                screenToShow = new CoverScreen(Component.translatable("CoverScreen"));
                this.pageMsg = Component.literal("");
                formattedText = FormattedText.of("");
                questBookImage.reset();
            } if (formattedString.contains("<empty>")){
                // Maybe show some sort of placeholder blank image screen
                formattedText = FormattedText.of("");
                questBookImage.reset();
            } else if (formattedString.contains("<highlight>")){
                // Could fit it any type of screen how
            }else {
                // Hero Screen
                this.hasTitle = formattedString.contains("<title>");
                if (hasTitle){
                    formattedText = FormattedText.of(formattedString.replace("<title>", ""));
                }

                if (formattedString.startsWith("<image>")){
                    String[] charSequencePieces = formattedString.split("<image>");

                    String imageString = charSequencePieces[1];
                    boolean showBorder = false;
                    if (imageString.contains("[border]")){
                        showBorder = true;
                        imageString = imageString.replace("[border]", "");
                    }

                    int capacity = (QUEST_IMAGE_HEIGHT / font.lineHeight);
                    StringBuilder sb = new StringBuilder(capacity);
                    for (int index = 0; index <= capacity; index++) {
                        sb.append("\n");
                    }
                    String buffer = sb.toString();
                    questBookImage = new QuestBookImage(resourceLocationFromString(imageString), showBorder);
                    formattedText = FormattedText.of(buffer + charSequencePieces[2]);
                } else {
                    questBookImage.reset();
                }
                screenToShow = null;
                this.pageMsg = Component.translatable("book.pageIndicator", new Object[]{this.currentPage + 1, Math.max(this.getNumPages(), 1)});
            }
            this.cachedPageComponents = this.font.split(formattedText, TEXT_WIDTH);
        }

        this.cachedPage = this.currentPage;

        // Render the chosen screen
        if (screenToShow != null && !(screenToShow instanceof CoverScreen)){
            screenToShow.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        int pageMsLen = this.font.width(this.pageMsg);

        guiGraphics.drawString(this.font, this.pageMsg, imageWidthCenter - pageMsLen + 192 - 44, 18, 0, false);
        int components = Math.min(14, this.cachedPageComponents.size());

        int titleIndex = 0;
        if (hasTitle && !questBookImage.isEmpty()){
            titleIndex = (QUEST_IMAGE_HEIGHT / font.lineHeight) + 2;
        }

        if (pageMsLen == 0)
            pageMsLen = 59;
        int centeredPosition = imageWidthCenter - pageMsLen + IMAGE_WIDTH - 50;
        if (!questBookImage.isEmpty()){
            guiGraphics.renderItem(getStack(questBookImage.resourceLocation), centeredPosition , IMAGE_Y_OFFSET);
            if (questBookImage.showBorder){
                float scaleFactor = 1.3F;
                int scaledQuestBookDisplayWidth = (int)(QUEST_BORDER_IMAGE_WIDTH * scaleFactor);
                int scaledQBookDisplayHeight = (int)(QUEST_IMAGE_HEIGHT * scaleFactor);
                guiGraphics.blit(RenderType::guiTextured, IMAGE_BORDER_LOCATION, (this.width - scaledQuestBookDisplayWidth) / 2 - 5, IMAGE_Y_OFFSET - (scaledQBookDisplayHeight - QUEST_IMAGE_HEIGHT) / 2, 0.0F, 0.0F, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight, scaledQuestBookDisplayWidth, scaledQBookDisplayHeight);
            }
        }

        for(int component = 0; component < components; ++component) {
            FormattedCharSequence formattedcharsequence = this.cachedPageComponents.get(component);
            if (hasTitle && titleIndex == component){
                drawCenteredStringWithoutDropShadow(guiGraphics, this.font, formattedcharsequence,  imageWidthCenter - pageMsLen + IMAGE_WIDTH - 44, 32 + component * 9, 0);
            } else {
                guiGraphics.drawString(this.font, formattedcharsequence, imageWidthCenter + 36, 32 + component * 9, 0, false);
            }
        }

        Style style = this.getClickedComponentStyleAt(mouseX, mouseY);
        if (style != null) {
            guiGraphics.renderComponentHoverEffect(this.font, style, mouseX, mouseY);
        }
    }

    public void drawCenteredStringWithoutDropShadow(GuiGraphics guiGraphics, Font font, FormattedCharSequence formattedcharsequence, int x, int y, int color) {
        guiGraphics.drawString(font, formattedcharsequence, x - font.width(formattedcharsequence) / 2, y, color, false);
    }

    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        if (screenToShow != null && screenToShow instanceof CoverScreen){
            guiGraphics.blit(RenderType::guiTextured, CoverScreen.COVER_PAGE_LOCATION, (this.width - 192) / 2, 2, 0.0F, 0.0F, IMAGE_WIDTH, IMAGE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        } else {
            guiGraphics.blit(RenderType::guiTextured, BOOK_LOCATION, (this.width - 192) / 2, 2, 0.0F, 0.0F, IMAGE_WIDTH, IMAGE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        }
    }

    public void setBookAccess(BookAccess bookAccess) {
        this.bookAccess = bookAccess;
        this.currentPage = Mth.clamp(this.currentPage, 0, bookAccess.getPageCount());
        this.updateButtonVisibility();
        this.cachedPage = -1;
    }

    public boolean setPage(int pageNum) {
        int i = Mth.clamp(pageNum, 0, this.bookAccess.getPageCount() - 1);
        if (i != this.currentPage) {
            this.currentPage = i;
            this.updateButtonVisibility();
            this.cachedPage = -1;
            return true;
        } else {
            return false;
        }
    }

    protected boolean forcePage(int pageNum) {
        return this.setPage(pageNum);
    }

    protected void init() {
        this.createMenuControls();
        this.createPageControlButtons();
    }

    protected void createMenuControls() {
        this.addRenderableWidget(Button.builder(Component.translatable("gui.projectj.close"), (p_386214_) -> this.onClose()).bounds(this.width / 2 - 100, 196, 200, 20).build());
    }

    protected void createPageControlButtons() {
        int i = (this.width - 192) / 2;
        int j = 2;
        this.forwardButton = (PageButton)this.addRenderableWidget(new PageButton(i + 116, 159, true, (p_98297_) -> this.pageForward(), this.playTurnSound));
        this.backButton = (PageButton)this.addRenderableWidget(new PageButton(i + 43, 159, false, (p_98287_) -> this.pageBack(), this.playTurnSound));
        this.updateButtonVisibility();
    }

    private int getNumPages() {
        return this.bookAccess.getPageCount();
    }

    protected void pageBack() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }

        this.updateButtonVisibility();
    }

    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            ++this.currentPage;
        }

        this.updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        this.backButton.visible = this.currentPage > 0;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else {
            switch (keyCode) {
                case 266:
                    this.backButton.onPress();
                    return true;
                case 267:
                    this.forwardButton.onPress();
                    return true;
                default:
                    return false;
            }
        }
    }



    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            Style style = this.getClickedComponentStyleAt(mouseX, mouseY);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean handleComponentClicked(Style style) {
        ClickEvent clickevent = style.getClickEvent();
        if (clickevent == null) {
            return false;
        } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String s = clickevent.getValue();

            try {
                int i = Integer.parseInt(s) - 1;
                return this.forcePage(i);
            } catch (Exception var5) {
                return false;
            }
        } else {
            boolean flag = super.handleComponentClicked(style);
            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.closeScreen();
            }

            return flag;
        }
    }

    protected void closeScreen() {
        this.minecraft.setScreen(null);
    }

    @Nullable
    public Style getClickedComponentStyleAt(double mouseX, double mouseY) {
        if (this.cachedPageComponents.isEmpty()) {
            return null;
        } else {
            int i = Mth.floor(mouseX - (double)((this.width - 192) / 2) - (double)36.0F);
            int j = Mth.floor(mouseY - (double)2.0F - (double)30.0F);
            if (i >= 0 && j >= 0) {
                int k = Math.min(14, this.cachedPageComponents.size());
                if (i <= TEXT_WIDTH && j < 9 * k + k) {
                    int l = j / 9;
                    if (l >= 0 && l < this.cachedPageComponents.size()) {
                        FormattedCharSequence formattedcharsequence = (FormattedCharSequence)this.cachedPageComponents.get(l);
                        return this.minecraft.font.getSplitter().componentStyleAtWidth(formattedcharsequence, i);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    protected static class QuestBookImage{
        ResourceLocation resourceLocation;
        boolean showBorder;

        protected QuestBookImage(ResourceLocation resourceLocation, boolean showBorder){
            this.resourceLocation = resourceLocation;
            this.showBorder = showBorder;
        }

        protected QuestBookImage(){
            this( null, false);
        }
        protected QuestBookImage(boolean showBorder){
            this( null, showBorder);
        }

        public void reset(){
            resourceLocation = null;
            showBorder = false;
        }

        public boolean isEmpty(){
            return resourceLocation == null;
        }
    }
}
