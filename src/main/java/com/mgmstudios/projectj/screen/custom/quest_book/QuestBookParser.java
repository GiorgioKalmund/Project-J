package com.mgmstudios.projectj.screen.custom.quest_book;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) {
        System.out.println(bookPageFromJson(getJsonPage(0)));
    }

    public static JsonObject getJsonPage(int pageNumber){
        Path pagePath = PAGE_DIR.toPath().resolve("page_" + pageNumber + ".json");
        return getJsonFromFile(pagePath);
    }

    public static JsonObject getJsonFromFile(Path path){
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonElement rootElement = JsonParser.parseReader(reader);

            if (rootElement.isJsonObject()) {
                return rootElement.getAsJsonObject();
            } else {
                System.out.println("root element is not of type json");
                return new JsonObject();
            }
        } catch (IOException e) {
            System.out.println("could not open file: " + path);
            return new JsonObject();
        }
    }

    public static QuestBookParserResult bookPageFromJson(JsonObject json){
        BookPage bookPage = BookPage.EMPTY;

        QuestBookParserResult result = new QuestBookParserResult();

        // Return Empty page
        if (json.isEmpty() || json.has("empty")){
             if (json.get("empty").getAsBoolean()){
                 return result;
             }
        }

        if (json.has("empty")){
            if (json.get("empty").getAsBoolean()){
                return result;
            }
        }


        List<QuestBookScreen.QuestBookImage> questBookImages = new ArrayList<>();
        boolean showPageMsg = true;

        String key = null;
        String message = null;
        if (json.has("show-page-msg")){
            showPageMsg = json.get("show-page-msg").getAsBoolean();
            System.out.println("Show msg: " + showPageMsg);

            // Handle Page Message
            if (json.has("page-msg")){
                JsonObject pageMsgElement = json.get("page-msg").getAsJsonObject();
                System.out.println("MSG: " + pageMsgElement.toString());

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
        if (json.has("images")){
            JsonArray images = json.get("images").getAsJsonArray();
            for (int index = 0; index < images.size(); index++){
                JsonObject image = images.get(index).getAsJsonObject();
                QuestBookScreen.QuestBookImage bookImage = QuestBookScreen.QuestBookImage.EMPTY;
                if (image.has("image")){
                    String value = image.get("image").getAsString();
                    if (value.startsWith(":")){
                        // Get predefined static image
                        bookImage = QuestBookScreen.QuestBookImage.LIT_PROCESS_IMAGE;
                    } else {
                        bookImage.resourceLocation(ResourceLocation.tryParse(value));
                    }

                    if (image.has("show-border")){
                        bookImage.showBorder(image.get("show-border").getAsBoolean());
                    }

                    if (image.has("type")){
                        QuestBookScreen.QuestBookImage.Type type = QuestBookScreen.QuestBookImage.Type.ITEM;
                        String typeSting = image.get("type").getAsString();
                        type = switch (typeSting){
                            case "regular" -> QuestBookScreen.QuestBookImage.Type.REGULAR;
                            case "sprite" -> QuestBookScreen.QuestBookImage.Type.SPRITE;
                            default -> QuestBookScreen.QuestBookImage.Type.ITEM;
                        };
                        bookImage.setType(type);
                    }
                }
                questBookImages.add(bookImage);
            }
        }

        bookPage.questBookImages = questBookImages;
        if (message != null){
            System.out.println("Found custom message");
            bookPage.setPageMsg(Component.literal(message));
        } else if (key != null){
            System.out.println("Found custom key");
            bookPage.setPageMsg(Component.translatable(key));
        } else {
            result.defaultPageMsg = true;
        }

        FormattedText formattedText = FormattedText.EMPTY;
        boolean hasTitle = false;
        if (json.has("text")){
            JsonObject text = json.get("text").getAsJsonObject();
            if (text.has("content")){
                formattedText = FormattedText.of(text.get("content").getAsString());

                if (text.has("has-title")){
                    hasTitle = text.get("has-title").getAsBoolean();
                }
            }
        }

        result.bookPage = bookPage;
        result.showPageMsg = showPageMsg;
        result.template = template;
        result.formattedText = formattedText;
        result.hasTitle = hasTitle;

        return result;
    }

    public static class QuestBookParserResult{
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
    }
}
