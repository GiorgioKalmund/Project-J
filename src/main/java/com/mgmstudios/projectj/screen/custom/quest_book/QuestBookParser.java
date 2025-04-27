package com.mgmstudios.projectj.screen.custom.quest_book;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookImage;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.ContentsPageScreen;
import com.mgmstudios.projectj.util.ItemLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookTemplateType.*;

public class QuestBookParser {

    public static final String KEY_TOTAL_PAGES = "total-pages";
    public static final String KEY_ERROR = "error";
    public static final String KEY_EMPTY = "empty";
    public static final String KEY_PAGE_SHORTCUT = "page-shortcut";
    public static final String KEY_SHOW_PAGE_MSG = "show-page-msg";
    public static final String KEY_SHOW_HOME_BUTTON = "show-home-button";
    public static final String KEY_PAGE_MSG = "page-msg";
    public static final String KEY_KEY = "key";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TEMPLATE = "template";
    public static final String KEY_SECONDARY_IMAGES = "secondary-images";
    public static final String KEY_NAME = "name";
    public static final String KEY_SHOW_FUEL = "show-fuel";
    public static final String KEY_SPACING = "spacing";
    public static final String KEY_CHAPTER_TITLE = "chapter-title";
    public static final String KEY_OBJECTS = "objects";
    public static final String KEY_ITEM = "item";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CONNECTED_PAGE = "connected-page";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_COUNT = "count";
    public static final String KEY_SHOW_BORDER = "show-border";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TEXT = "text";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_SHOW_TITLE = "show-title";
    public static final String KEY_ALIGN_CENTER = "align-center";

    public static JsonObject getJsonPage(int pageNumber, ResourceManager resourceManager){
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "quest_book/pages/page_" + pageNumber +".json");
        return getJsonObject(resourceLocation, resourceManager);
    }
    public static JsonObject getJsonObject(ResourceLocation resourceLocation, ResourceManager resourceManager){
        Optional<Resource> resource = resourceManager.getResource(resourceLocation);
        if (resource.isPresent()){
            try (InputStream inputStream = resource.get().open()){
                return getJsonFromInputStream(inputStream);
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
        System.out.println("Error opening page: " + resourceLocation.getPath());
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty(KEY_ERROR, true);
        return errorObject;
    }

    public static JsonObject getJsonFromInputStream(InputStream stream){
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            JsonElement rootElement = JsonParser.parseReader(reader);

            if (rootElement.isJsonObject()) {
                return rootElement.getAsJsonObject();
            } else {
                System.out.println("root element is not of type json");
                return new JsonObject();
            }
        } catch (IOException e) {
            System.out.println("could not open stream: " + stream);
            return new JsonObject();
        }
    }

    public static QuestBookParserResult bookPageFromJson(JsonObject json){
        BookPage bookPage = BookPage.empty();

        QuestBookParserResult result = new QuestBookParserResult();
        List<Boolean> templateBooleans = new ArrayList<>();
        List<Integer> templateIntegers = new ArrayList<>();
        List<String> templateStrings = new ArrayList<>();
        List<Object> templateObjects = new ArrayList<>();

        // If page could not be loaded from JSON return error page
        if (json.has(KEY_ERROR)){
            if (json.get(KEY_ERROR).getAsBoolean()){
                return result.setFalsy();
            }
        }

        if (json.has(KEY_EMPTY)){
             if (json.get(KEY_EMPTY).getAsBoolean()){
                 result.setEmpty();
             }
        }

        boolean showHomeButton = true;
        if (json.has(KEY_SHOW_HOME_BUTTON)){
            showHomeButton = json.get(KEY_SHOW_HOME_BUTTON).getAsBoolean();
        }

        boolean showPageMsg = true;
        String key = null;
        String message = null;
        if (json.has(KEY_SHOW_PAGE_MSG)){
            showPageMsg = json.get(KEY_SHOW_PAGE_MSG).getAsBoolean();
        }

        // Handle Page Message
        if (json.has(KEY_PAGE_MSG)){
            JsonObject pageMsgElement = json.get(KEY_PAGE_MSG).getAsJsonObject();

            if (pageMsgElement.has(KEY_KEY)){
                key = pageMsgElement.get(KEY_KEY).getAsString();
            }
            else if (pageMsgElement.has(KEY_MESSAGE)){
                message = pageMsgElement.get(KEY_MESSAGE).getAsString();
            }
        }

        // Template
        String templateName = null;
        List<QuestBookImage> resultImages = new ArrayList<>();
        if (json.has(KEY_TEMPLATE)){
            JsonObject template = json.get(KEY_TEMPLATE).getAsJsonObject();
            if (template.has(KEY_NAME))
                templateName = template.get(KEY_NAME).getAsString();
            if (templateName != null){
                if (template.has(KEY_SHOW_FUEL) && stringIsType(templateName, PROCESS))
                    templateBooleans.add(template.get(KEY_SHOW_FUEL).getAsBoolean());
                if (template.has(KEY_SPACING) && stringIsAnyOfTypes(templateName, PROCESS, DOUBLE_ITEM_SHOWCASE, CONTENTS_PAGE, ITEM_LIST, RECIPE_LIST))
                    templateIntegers.add(template.get(KEY_SPACING).getAsInt());
                if (template.has(KEY_CHAPTER_TITLE) && stringIsAnyOfTypes(templateName, CHAPTER_COVER))
                    templateStrings.add(template.get(KEY_CHAPTER_TITLE).getAsString());
                if (template.has(KEY_SECONDARY_IMAGES) && stringIsAnyOfTypes(templateName, RECIPE_LIST, PROCESS)){
                    JsonArray secondaryImages = template.get(KEY_SECONDARY_IMAGES).getAsJsonArray();
                    addImagesToList(secondaryImages, resultImages);
                    bookPage.secondaryImages = resultImages;
                }
                if (template.has(KEY_OBJECTS)){
                    JsonArray objectsArray = template.get(KEY_OBJECTS).getAsJsonArray();
                    if (stringIsAnyOfTypes(templateName, CONTENTS_PAGE)){
                        for (JsonElement object : objectsArray){
                            JsonObject o = object.getAsJsonObject();
                            ContentsPageScreen.ContentsPageEntry entry = new ContentsPageScreen.ContentsPageEntry(Items.BARRIER, -1);
                            if (!o.isEmpty()){
                                if (o.has(KEY_ITEM)){
                                    ItemLike item = ItemLookup.getStack(o.get(KEY_ITEM).getAsString()).getItem();
                                    entry.displayItem(item);
                                }
                                if (o.has(KEY_DESCRIPTION)){
                                    entry.displayText(o.get(KEY_DESCRIPTION).getAsString());
                                }
                                if (o.has(KEY_CONNECTED_PAGE)){
                                    try {
                                        entry.connectedPage(o.get(KEY_CONNECTED_PAGE).getAsInt());
                                    } catch (NumberFormatException e){
                                        entry.connectedShortcut(o.get(KEY_CONNECTED_PAGE).getAsString());
                                    }
                                }
                                templateObjects.add(entry);
                            }
                        }
                    }
                }
            }
        }

        // Handle Images
        List<QuestBookImage> qBookImages = new ArrayList<>();
        if (json.has(KEY_IMAGES)){
            JsonArray images = json.get(KEY_IMAGES).getAsJsonArray();
            addImagesToList(images, qBookImages);
        }
        bookPage.questBookImages = qBookImages;

        // Page Msg
        if (message != null){
            bookPage.setPageMsg(Component.literal(message));
        } else if (key != null){
            bookPage.setPageMsg(Component.translatable(key));
        } else {
            result.defaultPageMsg = true;
        }

        // Page Text
        FormattedText formattedText = FormattedText.EMPTY;
        boolean showTitle = false;
        boolean alignCenter;
        if (json.has(KEY_TEXT)){
            JsonObject text = json.get(KEY_TEXT).getAsJsonObject();
            if (text.has(KEY_CONTENT)){
                formattedText = FormattedText.of(text.get(KEY_CONTENT).getAsString());

                if (text.has(KEY_SHOW_TITLE)){
                    showTitle = text.get(KEY_SHOW_TITLE).getAsBoolean();
                }

                if (text.has(KEY_ALIGN_CENTER)){
                    alignCenter = text.get(KEY_ALIGN_CENTER).getAsBoolean();
                    templateBooleans.add(alignCenter);
                }
            }
        }

        result.templateBooleans = templateBooleans;
        result.templateIntegers = templateIntegers;
        result.templateStrings = templateStrings;
        result.templateObjects = templateObjects;
        result.bookPage = bookPage;
        result.showPageMsg = showPageMsg;
        result.showHomeButton = showHomeButton;
        result.templateType = QuestBookScreen.QuestBookTemplateType.fromDisplayName(templateName);
        result.formattedText = formattedText;
        result.showTitle = showTitle;

        return result;
    }

    protected static void addImagesToList(JsonArray imagesArray, List<QuestBookImage> list){
        for (int index = 0; index < imagesArray.size(); index++){
            JsonObject image = imagesArray.get(index).getAsJsonObject();
            QuestBookImage bookImage = QuestBookImage.empty();
            if (image.has(KEY_IMAGE)){
                String value = image.get(KEY_IMAGE).getAsString();
                if (value.startsWith(":")){
                    bookImage = QuestBookImage.getShortHandImage(value);
                } else {
                    bookImage.resourceLocation(ResourceLocation.tryParse(value));
                }

                if (image.has(KEY_SHOW_BORDER))
                    bookImage.showBorder(image.get(KEY_SHOW_BORDER).getAsBoolean());

                if (image.has(KEY_COUNT))
                    bookImage.count(image.get(KEY_COUNT).getAsInt());

                if (image.has(KEY_TYPE)){
                    QuestBookImage.Type type = QuestBookImage.Type.ITEM;
                    String typeSting = image.get(KEY_TYPE).getAsString();
                    type = switch (typeSting){
                        case "regular" -> QuestBookImage.Type.REGULAR;
                        case "sprite" -> QuestBookImage.Type.SPRITE;
                        default -> QuestBookImage.Type.ITEM;
                    };
                    bookImage.setType(type);
                }
            }
            list.add(bookImage);
        }
    }

    public static class QuestBookParserResult{

        private boolean isEmpty = false;
        private boolean isFalsy = false;
        public BookPage bookPage = BookPage.EMPTY;
        public boolean showPageMsg = true;
        public boolean showHomeButton = true;
        public boolean defaultPageMsg = false;
        FormattedText formattedText = FormattedText.EMPTY;
        public boolean showTitle = false;
        public QuestBookScreen.QuestBookTemplateType templateType = null;

        List<Boolean> templateBooleans = new ArrayList<>();
        List<Integer> templateIntegers = new ArrayList<>();
        List<String> templateStrings = new ArrayList<>();
        List<Object> templateObjects = new ArrayList<>();

        @Override
        public String toString() {
            return "QuestBookParserResult{" +
                    "isEmpty=" + isEmpty +
                    ", isFalsy=" + isFalsy +
                    ", bookPage=" + bookPage +
                    ", showPageMsg=" + showPageMsg +
                    ", showHomeButton=" + showHomeButton +
                    ", defaultPageMsg=" + defaultPageMsg +
                    ", formattedText=" + formattedText +
                    ", showTitle=" + showTitle +
                    ", templateType=" + templateType +
                    ", templateBooleans=" + templateBooleans +
                    ", templateIntegers=" + templateIntegers +
                    ", templateStrings=" + templateStrings +
                    ", templateObjects=" + templateObjects +
                    '}';
        }

        protected QuestBookParserResult setFormattedText(String text){
            return setFormattedText(FormattedText.of(text));
        }
        protected QuestBookParserResult setFormattedText(FormattedText text){
            this.formattedText = text;
            return this;
        }

        protected QuestBookParserResult setEmpty(){
            this.isEmpty = true;
            return this;
        }
        protected QuestBookParserResult setFalsy(){
            this.isFalsy = true;
            return this;
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public boolean isFalsy() {
            return isFalsy;
        }
    }
}
