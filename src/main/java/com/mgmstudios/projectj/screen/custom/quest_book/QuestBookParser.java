package com.mgmstudios.projectj.screen.custom.quest_book;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookImage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QuestBookParser {
    public static File PAGE_DIR = new File("src/main/resources/data/projectj/quest_book/pages/");
    public static File PAGE_BUILD_DIR = new File("resources/main/data/projectj/quest_book/pages/");

    public static void ls(){
        File[] files = PAGE_DIR.listFiles();
        if (files == null){
            System.err.println("Error listing files for: " + PAGE_DIR.getAbsolutePath());
            return;
        }
        System.out.println("Found " + files.length + " files");
        for (File file : files){
            System.out.println("File: " + file.getName());
        }
    }

    public static JsonObject getJsonPage(int pageNumber, ResourceManager resourceManager){
        //Path pagePath = PAGE_DIR.toPath().resolve("page_" + pageNumber + ".json");
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "quest_book/pages/page_" + pageNumber +".json");
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
        errorObject.addProperty("error", true);
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

        // If page could not be loaded from JSON return error page
        if (json.has("error")){
            if (json.get("error").getAsBoolean()){
                result.formattedText = FormattedText.of("§4§l<ERROR LOADING PAGE>§r");
                return result.setFalsy();
            }
        }

        // Return Empty page
        if (json.isEmpty())
            return result.setEmpty();

        if (json.has("empty")){
             if (json.get("empty").getAsBoolean()){
                 return result;
             }
        }

        boolean showPageMsg = true;

        String key = null;
        String message = null;
        if (json.has("show-page-msg")){
            showPageMsg = json.get("show-page-msg").getAsBoolean();

            // Handle Page Message
            if (json.has("page-msg")){
                JsonObject pageMsgElement = json.get("page-msg").getAsJsonObject();

                if (pageMsgElement.has("key")){
                    key = pageMsgElement.get("key").getAsString();
                }
                else if (pageMsgElement.has("message")){
                    message = pageMsgElement.get("message").getAsString();
                }
            }

        }

        String template = null;
        if (json.has("template")){
            template = json.get("template").getAsString();
        }

        // Handle Images
        List<QuestBookImage> qBookImages = new ArrayList<>();
        if (json.has("images")){
            JsonArray images = json.get("images").getAsJsonArray();
            for (int index = 0; index < images.size(); index++){
                JsonObject image = images.get(index).getAsJsonObject();
                QuestBookImage bookImage = QuestBookImage.empty();
                if (image.has("image")){
                    String value = image.get("image").getAsString();
                    if (value.startsWith(":")){
                        // TODO: Properly parse existing predefined images
                        // Get predefined static image
                        bookImage = QuestBookImage.PROCESS_IMAGE;
                    } else {
                        bookImage.resourceLocation(ResourceLocation.tryParse(value));
                    }

                    if (image.has("show-border")){
                        bookImage.showBorder(image.get("show-border").getAsBoolean());
                    }

                    if (image.has("type")){
                        QuestBookImage.Type type = QuestBookImage.Type.ITEM;
                        String typeSting = image.get("type").getAsString();
                        type = switch (typeSting){
                            case "regular" -> QuestBookImage.Type.REGULAR;
                            case "sprite" -> QuestBookImage.Type.SPRITE;
                            default -> QuestBookImage.Type.ITEM;
                        };
                        bookImage.setType(type);
                    }
                }
                qBookImages.add(bookImage);
            }
        }

        bookPage.questBookImages = qBookImages;
        if (message != null){
            bookPage.setPageMsg(Component.literal(message));
        } else if (key != null){
            bookPage.setPageMsg(Component.translatable(key));
        } else {
            result.defaultPageMsg = true;
        }

        FormattedText formattedText = FormattedText.EMPTY;
        boolean showTitle = false;
        if (json.has("text")){
            JsonObject text = json.get("text").getAsJsonObject();
            if (text.has("content")){
                formattedText = FormattedText.of(text.get("content").getAsString());

                if (text.has("show-title")){
                    showTitle = text.get("show-title").getAsBoolean();
                }
            }
        }

        result.bookPage = bookPage;
        result.showPageMsg = showPageMsg;
        result.template = template;
        result.formattedText = formattedText;
        result.hasTitle = showTitle;

        System.out.println("TITLE: " + result.hasTitle);
        return result;
    }

    public static class QuestBookParserResult{

        private boolean isEmpty = false;
        private boolean isFalsy = false;
        public BookPage bookPage = BookPage.EMPTY;
        public boolean showPageMsg = true;
        public boolean defaultPageMsg = false;
        FormattedText formattedText = FormattedText.EMPTY;
        public boolean hasTitle = false;
        public String template = null;

        @Override
        public String toString() {
            return "QuestBookParserResult{" +
                    "bookPage=" + bookPage +
                    ", showPageMsg=" + showPageMsg +
                    ", defaultPageMsg=" + defaultPageMsg +
                    ", formattedText=" + formattedText +
                    ", hasTitle=" + hasTitle +
                    ", template='" + template + '\'' +
                    '}';
        }

        protected QuestBookParserResult setFormattedText(String text){
            return setFormattedText(FormattedText.of(text));
        }
        protected QuestBookParserResult setFormattedText(FormattedText text){
            this.formattedText = formattedText;
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
