package com.mgmstudios.projectj.screen.custom.quest_book;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.quest_book.components.QuestPageButton;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.*;
import com.mgmstudios.projectj.util.ItemLookup;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen.BookAccess;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.*;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookParser.*;

public class QuestBookScreen extends Screen {

    public static final int PAGE_INDICATOR_TEXT_Y_OFFSET = 16;
    public static final int PAGE_TEXT_X_OFFSET = 36;
    public static final int PAGE_TEXT_Y_OFFSET = 30;
    public static final int BACKGROUND_TEXTURE_WIDTH = 256;
    public static final int BACKGROUND_TEXTURE_HEIGHT = 256;
    public static final BookAccess EMPTY_ACCESS = new BookAccess(List.of());
    public static final ResourceLocation BOOK_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/quest_book.png");
    public static final ResourceLocation IMAGE_BORDER_LOCATION = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/image_border.png");
    public static final int TEXT_WIDTH = 114;
    protected static final int TEXT_HEIGHT = 128;
    public static final int IMAGE_WIDTH = 192;
    public static final int IMAGE_HEIGHT = 192;

    public static final int IMAGE_Y_OFFSET = 36;
    private int currentPage;
    private List<FormattedCharSequence> cachedPageComponents;
    private int cachedPage;
    private QuestPageButton forwardButton;
    private QuestPageButton backButton;
    private QuestPageButton homeButton;
    private final boolean playTurnSound;
    public static final int QUEST_IMAGE_WIDTH = 16;
    public static final int QUEST_BORDER_IMAGE_WIDTH = 48;
    public static final int QUEST_IMAGE_HEIGHT = 16;
    protected QuestBookScreenTemplate screenToShow = QuestBookScreenTemplate.EMPTY;
    protected List<AbstractWidget> temporaryWidgets = new ArrayList<>();
    public LinkedTreeMap<String, Integer> pageShortcutMap;
    private int totalPages = 0;
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
        this.playTurnSound = playTurnSound;
        this.pageShortcutMap = new LinkedTreeMap<>();

    }

    public int currentPage(){
        return currentPage;
    }

    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);


        if (this.cachedPage != this.currentPage) {


            if (screenToShow != null){
                screenToShow.page().clear();
                removeTemporaryWidgets();
            }

            QuestBookParserResult bookParserResult = null;
            if (this.minecraft != null){
                bookParserResult = bookPageFromJson(getJsonPage(currentPage, this.minecraft.getResourceManager()));

                ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "quest_book/pages/shortcuts.json");
                JsonObject jsonObject = QuestBookParser.getJsonObject(resourceLocation, this.minecraft.getResourceManager());
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Integer>>() {}.getType();
                pageShortcutMap = gson.fromJson(jsonObject, type);
                recalculateTotalPages();
            } else {
                System.err.println("Could not get Minecraft because it is null (not open)");
            }

            if (bookParserResult == null){
                screenToShow = new EmptyScreen(this);
                return;
            }

            FormattedText formattedText = bookParserResult.formattedText;
            boolean showPageMsg = bookParserResult.showPageMsg;
            boolean showTitle = bookParserResult.showTitle;
            BookPage bookPage = bookParserResult.bookPage;
            List<Boolean> templateBooleans = bookParserResult.templateBooleans;
            List<Integer> templateIntegers = bookParserResult.templateIntegers;
            List<String> templateStrings = bookParserResult.templateStrings;

            if (bookParserResult.isFalsy()){
                System.err.println("Falsy QuestBookParserResult for page: " + currentPage);
                formattedText = FormattedText.of("§4§l<ERROR READING PAGE CONTENTS>§");
            }

            // :result
            System.out.println(bookParserResult);

            if (bookParserResult.defaultPageMsg || bookParserResult.isFalsy()){
                bookPage.setPageMsg(Component.translatable("book.pageIndicator", new Object[]{this.currentPage + 1, Math.max(this.getNumPages(), 1)}));
            }

            this.cachedPageComponents = this.font.split(formattedText, TEXT_WIDTH);
            bookPage.setComponents(cachedPageComponents);

            List<ContentsPageScreen.ContentsPageEntry> contentsPageEntries = new ArrayList<>();
            for (Object object : bookParserResult.templateObjects){
                if (object instanceof ContentsPageScreen.ContentsPageEntry entry)
                    contentsPageEntries.add(entry);
            }

            if (bookParserResult.templateType == null)
                screenToShow = new TextScreen(this, bookPage, showTitle, showPageMsg, false);
            else{
                screenToShow = switch(bookParserResult.templateType){
                    case EMPTY -> new EmptyScreen(this, showPageMsg);
                    case COVER -> new CoverScreen(this, bookPage, showPageMsg);
                    case TEXT -> new TextScreen(this, bookPage, showTitle, showPageMsg, getOrDefault(templateBooleans, 0, false));
                    case PROCESS -> new ProcessScreen(this, bookPage, showTitle, showPageMsg, getOrDefault(templateIntegers, 0, 0), getOrDefault(templateBooleans, 0, false));
                    case ITEM_SHOWCASE -> new ImageScreen(this, bookPage, showTitle, showPageMsg);
                    case DOUBLE_ITEM_SHOWCASE -> new DoubleImageScreen(this, bookPage, showTitle, showPageMsg, getOrDefault(templateIntegers, 0, 0));
                    case CONTENTS_PAGE -> new ContentsPageScreen(this, bookPage, showPageMsg, getOrDefault(templateIntegers, 0, 0), contentsPageEntries);
                    case CHAPTER_COVER -> new ChapterCoverScreen(this, bookPage, getOrDefault(templateStrings, 0, ""), showPageMsg, getOrDefault(templateBooleans, 0, false));
                    case ITEM_LIST -> new ItemListScreen(this, bookPage, showTitle, showPageMsg, getOrDefault(templateIntegers, 0, 0));
                    case RECIPE_LIST -> new RecipeListScreen(this, bookPage, showTitle, showPageMsg, getOrDefault(templateIntegers, 0, 0), getOrDefault(templateBooleans, 0, false));
                };
            }

            this.homeButton.visible = bookParserResult.showHomeButton;
        }

        this.cachedPage = this.currentPage;

        // Render the chosen screen
        if (screenToShow != null){
            screenToShow.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        Style style = this.getClickedComponentStyleAt(mouseX, mouseY);
        if (style != null) {
            guiGraphics.renderComponentHoverEffect(this.font, style, mouseX, mouseY);
        }
    }

    public static <T> T getOrDefault(List<T> list, int index, T defaultValue){
        if (list.isEmpty() ||index >= list.size() || list.get(index) == null)
            return defaultValue;
        return list.get(index);
    }

    public static void drawCenteredStringWithoutDropShadow(GuiGraphics guiGraphics, Font font, FormattedCharSequence formattedcharsequence, int x, int y, int color) {
        guiGraphics.drawString(font, formattedcharsequence, x - font.width(formattedcharsequence) / 2, y, color, false);
    }

    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        guiGraphics.blit(RenderType::guiTextured, screenToShow.getBackdropImage(), (this.width - IMAGE_WIDTH) / 2, 2, 0.0F, 0.0F, IMAGE_WIDTH, IMAGE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
    }

    public boolean setPage(int pageNum) {
        int i = Mth.clamp(pageNum, 0, 99);
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

    public void addTemporaryWidgets(List<AbstractWidget> widgets){
        for (AbstractWidget w : widgets){
            addTemporaryWidget(w);
        }
    }

    public void addTemporaryWidget(AbstractWidget w){
        temporaryWidgets.add(addRenderableWidget(w));
    }

    public void removeTemporaryWidgets(){
        for (AbstractWidget w : temporaryWidgets){
            removeWidget(w);
        }
    }

    protected void createPageControlButtons() {
        int i = (this.width - 192) / 2;
        this.forwardButton = this.addRenderableWidget(new QuestPageButton(i + 116, 159, true, (button) -> pageForward(), playTurnSound));
        this.backButton = this.addRenderableWidget(new QuestPageButton(i + 43, 159, false, (button) -> pageBack(), playTurnSound));
        this.homeButton = this.addRenderableWidget(new QuestPageButton(i + 32, 16,  (button) -> setPage(1), playTurnSound, true));
        this.updateButtonVisibility();
        recalculateTotalPages();
    }

    private void recalculateTotalPages(){
        if (this.minecraft != null){
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "quest_book/pages/shortcuts.json");
            JsonObject jsonObject = QuestBookParser.getJsonObject(resourceLocation, this.minecraft.getResourceManager());
            if (jsonObject.has(KEY_TOTAL_PAGES))
                totalPages = jsonObject.get(KEY_TOTAL_PAGES).getAsInt();

            System.out.println("TOTAL PAGES: " + totalPages);
        }
        updateButtonVisibility();
    }


    private int getNumPages() {
        return totalPages;
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
            return switch (keyCode) {
                case 266 -> {
                    this.backButton.onPress();
                    yield true;
                }
                case 267 -> {
                    this.forwardButton.onPress();
                    yield true;
                }
                default -> false;
            };
        }
    }

    @Override
    public void setFocused(@org.jetbrains.annotations.Nullable GuiEventListener listener) {
        super.setFocused(listener);
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
                int k =  this.cachedPageComponents.size();
                if (i <= TEXT_WIDTH && j < 9 * k + k) {
                    int l = j / 9;
                    if (l < this.cachedPageComponents.size()) {
                        FormattedCharSequence formattedcharsequence = this.cachedPageComponents.get(l);
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

    public enum QuestBookTemplateType {

        EMPTY, COVER, TEXT, PROCESS,
        DOUBLE_ITEM_SHOWCASE("double-item-showcase"),
        ITEM_SHOWCASE("item-showcase"),
        CONTENTS_PAGE("contents-page"),
        CHAPTER_COVER("chapter-cover"),
        ITEM_LIST("item-list"),
        RECIPE_LIST("recipe-list");

        private final String displayName;

        QuestBookTemplateType(String displayName) {
            this.displayName = displayName;
        }

        QuestBookTemplateType(){
            this.displayName = this.name().toLowerCase();
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }

        public static QuestBookTemplateType fromDisplayName(String displayName) {
            for (QuestBookTemplateType type : QuestBookTemplateType.values()) {
                if (type.displayName.equalsIgnoreCase(displayName)) {
                    return type;
                }
            }
            return null;
        }

        public static boolean stringIsType(String string, QuestBookTemplateType type){
            return QuestBookTemplateType.fromDisplayName(string) == type;
        }

        public static boolean stringIsAnyOfTypes(String string, QuestBookTemplateType... types){
            for (QuestBookTemplateType t : types){
                if (stringIsType(string, t))
                    return true;
            }
            return false;
        }
    }

    public static class QuestBookImage{
        ResourceLocation resourceLocation;
        boolean showBorder;
        protected Type type;
        protected int count;
        private String shorthand;


        public QuestBookImage(ResourceLocation resourceLocation){
            this(resourceLocation, false, Type.ITEM, "");
        }


        public QuestBookImage(ResourceLocation resourceLocation, boolean showBorder){
            this(resourceLocation, 1, showBorder, Type.ITEM);
        }

        public QuestBookImage(ItemLike itemLike, boolean showBorder){
            this(ItemLookup.getResourceLocation(itemLike) ,1 ,showBorder, Type.ITEM);
        }

        public QuestBookImage(ItemLike itemLike){
            this(ItemLookup.getResourceLocation(itemLike) ,1 ,false, Type.ITEM);
        }

        public QuestBookImage(ItemLike itemLike, int count, boolean showBorder){
            this(ItemLookup.getResourceLocation(itemLike), count, showBorder, Type.ITEM);
        }

        public QuestBookImage(ItemLike itemLike, boolean showBorder, Type type){
            this(ItemLookup.getResourceLocation(itemLike) , 1, showBorder, type);
        }

        protected QuestBookImage(ResourceLocation resourceLocation, boolean showBorder, Type type){
            this(resourceLocation, 1, showBorder, type, null);
        }
        protected QuestBookImage(ResourceLocation resourceLocation, boolean showBorder, Type type, String shorthand){
            this(resourceLocation, 1, showBorder, type, shorthand);
        }

        public static HashMap<String, QuestBookImage> SHORTHAND_MAP = new HashMap<>();
        protected QuestBookImage(ResourceLocation resourceLocation, int count, boolean showBorder, Type type, String shorthand){
            this.resourceLocation = resourceLocation;
            this.showBorder = showBorder;
            this.type = type;
            this.shorthand = shorthand;
            if (shorthand != null && !shorthand.isBlank()){
                SHORTHAND_MAP.put(this.shorthand, this);
            }
            this.count = count;
        }

        public static QuestBookImage getShortHandImage(String key){
            return SHORTHAND_MAP.getOrDefault(key, QuestBookImage.empty());
        }

        protected QuestBookImage(ResourceLocation resourceLocation, int count, boolean showBorder, Type type){
            this(resourceLocation, count, showBorder, type, null);
        }

        protected QuestBookImage(){
            this((ResourceLocation) null, false);
        }
        protected QuestBookImage(boolean showBorder){
            this((ResourceLocation) null, showBorder);
        }

        public static QuestBookImage empty(){
            return new QuestBookImage();
        }

        public static QuestBookImage unavailable(){
            return new QuestBookImage(Items.BARRIER, false);
        }

        public void reset(){
            resourceLocation = null;
            showBorder = false;
            shorthand = "";
            count = 1;
            type = Type.ITEM;
        }

        public boolean isEmpty(){
            return resourceLocation == null;
        }

        public ResourceLocation resourceLocation() {
            return resourceLocation;
        }

        public QuestBookImage resourceLocation(ResourceLocation resourceLocation){
            this.resourceLocation = resourceLocation;
            return this;
        }

        public boolean showBorder() {
            return showBorder;
        }

        public QuestBookImage showBorder(boolean showBorder){
            this.showBorder = showBorder;
            return this;
        }

        public Type type(){
            return type;
        }

        public QuestBookImage setType(Type type){
            this.type = type;
            return this;
        }

        public QuestBookImage regular(){
            this.type = Type.REGULAR;
            return this;
        }

        public QuestBookImage sprite(){
            this.type = Type.SPRITE;
            return this;
        }

        public int count(){
            return this.count;
        }

        public QuestBookImage count(int count){
            this.count = count;
            return this;
        }

        public QuestBookImage setShorthand(String shorthand){
            this.shorthand = shorthand;
            return this;
        }

        public boolean hasShortHand(){
            return shorthand != null && !shorthand.isEmpty();
        }

        public String shorthand(){
            return this.shorthand;
        }

        public enum Type {
            ITEM, REGULAR, SPRITE
        }

        public static ResourceLocation questBookStoredImage(String name, String subFolder){
            return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "textures/gui/quest_book/images/" + subFolder + name + ".png");
        }

        public static ResourceLocation questBookStoredImage(String name){
            return questBookStoredImage(name, "");
        }

        public static QuestBookImage PROCESS_IMAGE = new QuestBookImage(questBookStoredImage("process"), false, Type.REGULAR, ":process");
        public static QuestBookImage ADOBE_LIT_PROCESS_IMAGE = new QuestBookImage(questBookStoredImage("adobe_lit_process"), false, Type.REGULAR, ":adobe_lit_process");
        public static QuestBookImage LIT_PROCESS_IMAGE = new QuestBookImage(questBookStoredImage("lit_process"), false, Type.REGULAR, ":lit_process");
        public static QuestBookImage CHAPTER_1_IMAGE = new QuestBookImage(questBookStoredImage("chapter_1", "chapters/"), false, Type.REGULAR, ":chapter_1");
        public static QuestBookImage CHAPTER_2_IMAGE = new QuestBookImage(questBookStoredImage("chapter_2", "chapters/"), false, Type.REGULAR, ":chapter_2");
        public static QuestBookImage ANCIENT_ALTAR_RECIPES_CHAPTER = new QuestBookImage(questBookStoredImage("ancient_altar_recipes", "chapters/"), false, Type.REGULAR, ":ancient_altar_recipes");
        public static QuestBookImage MAGNETS_CHAPTER = new QuestBookImage(questBookStoredImage("magnets_chapter", "chapters/"), false, Type.REGULAR, ":magnets_chapter");
        public static QuestBookImage VOODOO_CHAPTER = new QuestBookImage(questBookStoredImage("voodoo_chapter", "chapters/"), false, Type.REGULAR, ":voodoo_chapter");
        public static QuestBookImage ARMOR_CHAPTER = new QuestBookImage(questBookStoredImage("armor_chapter", "chapters/"), false, Type.REGULAR, ":armor_chapter");
        public static QuestBookImage EMPTY = new QuestBookImage();


        @Override
        public String toString() {
            return "QuestBookImage{" +
                    "resourceLocation=" + resourceLocation +
                    ", showBorder=" + showBorder +
                    ", type=" + type +
                    ", count=" + count +
                    ", shorthand='" + shorthand + '\'' +
                    '}';
        }
    }
}
