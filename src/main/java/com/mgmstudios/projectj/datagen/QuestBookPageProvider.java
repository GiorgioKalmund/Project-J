package com.mgmstudios.projectj.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.ContentsPageScreen;
import com.mgmstudios.projectj.util.ItemLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.mgmstudios.projectj.ProjectJ.MOD_ID;
import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookParser.*;

public class QuestBookPageProvider implements DataProvider {
    private final PackOutput packOutput;
    List<JsonObject> pages = new ArrayList<>();

    public QuestBookPageProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    public void generatePages(){
        Builder.create()
                .setTemplate(QuestBookScreen.QuestBookScreenType.COVER)
                .showPageMessage(true)
                .setPageMessage("§f§lNo. 1§r")
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookScreen.QuestBookScreenType.CONTENTS_PAGE)
                .setTemplateSpacing(30)
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.JADE, "0", 0))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(Items.PUMPKIN, "1", 1))
                .setText("§lTable of Contents§r", true)
                .addImage(new QuestBookScreen.QuestBookImage(ModItems.RAW_JADE, false))
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookScreen.QuestBookScreenType.ITEM_SHOWCASE)
                .setImages(new QuestBookScreen.QuestBookImage(ModItems.LITTLE_KING_SPAWN_EGG, true))
                .setText("§nRandom Title§r\nThis is the description.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookScreen.QuestBookScreenType.PROCESS)
                .defaultTemplateSpacing()
                .setTemplateShowFuel(true)
                .setPageMessage("§oSecret Message!§r")
                .addImage(new QuestBookScreen.QuestBookImage(ModItems.RAW_JADE, false))
                .addImage(QuestBookScreen.QuestBookImage.PROCESS_IMAGE)
                .addImage(new QuestBookScreen.QuestBookImage(ModItems.JADE, false))
                .addImage(QuestBookScreen.QuestBookImage.ADOBE_LIT_PROCESS_IMAGE)
                .setText("Hello World!", false)
                .save(pages);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput){
        Path basePath = packOutput.getOutputFolder()
                .resolve("assets/" + MOD_ID + "/quest_book/pages/");

        generatePages();

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (int index = 0; index < pages.size(); index++){
            Path path = basePath.resolve("page_" + index + ".json");
            JsonObject page = pages.get(index);
            futures.add(DataProvider.saveStable(cachedOutput, page, path));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Quest Book Builder: " + MOD_ID;
    }

    public static class Builder{

        JsonObject json = new JsonObject();

        static Builder create(){
            return new Builder();
        }

        Builder showPageMessage(boolean value){
            json.addProperty(KEY_SHOW_PAGE_MSG, value);
            return this;
        }

        Builder setPageMessage(String message){
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty(KEY_MESSAGE, message);
            json.add(KEY_PAGE_MSG, messageObject);
            return this;
        }

        Builder setPageMessageKey(String key){
            JsonObject keyObject = new JsonObject();
            keyObject.addProperty(KEY_KEY, key);
            json.add(KEY_PAGE_MSG, keyObject);
            return this;
        }

        Builder setEmpty(boolean value){
            json.addProperty(KEY_EMPTY, value);
            return this;
        }

        Builder setTemplate(QuestBookScreen.QuestBookScreenType type){
            JsonObject templateObject = new JsonObject();
            templateObject.addProperty(KEY_NAME, type.getDisplayName());
            json.add(KEY_TEMPLATE, templateObject);
            return this;
        }

        Builder setTemplateSpacing(int spacing){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject != null){
                templateObject.addProperty(KEY_SPACING, spacing);
            }
            return this;
        }

        Builder defaultTemplateSpacing(){
            return setTemplateSpacing(20);
        }
        Builder setTemplateShowFuel(boolean showFuel){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject != null){
                templateObject.addProperty(KEY_SHOW_FUEL, showFuel);
            }
            return this;
        }

        Builder setTemplateContentsPageEntries(List<ContentsPageScreen.ContentsPageEntry> entries){
            for (ContentsPageScreen.ContentsPageEntry entry : entries)
                addTemplateContentsPageEntry(entry);
            return this;
        }

        Builder addTemplateContentsPageEntry(ContentsPageScreen.ContentsPageEntry entry){
            JsonObject entryObject = new JsonObject();

            ResourceLocation resourceLocation = ItemLookup.getResourceLocation(entry.displayItem());
            entryObject.addProperty(KEY_ITEM, resourceLocation.getNamespace() + ":" + resourceLocation.getPath());
            entryObject.addProperty(KEY_DESCRIPTION, entry.displayText());
            entryObject.addProperty(KEY_CONNECTED_PAGE, entry.connectedPage());

            JsonArray objectsArray;
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject.has(KEY_OBJECTS))
                objectsArray = templateObject.getAsJsonArray(KEY_OBJECTS);
            else{
                objectsArray = new JsonArray();
                templateObject.add(KEY_OBJECTS, objectsArray);
            }
            objectsArray.add(entryObject);
            return this;
        }
        Builder addImage(QuestBookScreen.QuestBookImage image){
            JsonObject imageObject = new JsonObject();
            if (image.hasShortHand())
                imageObject.addProperty(KEY_IMAGE, image.shorthand());
            else
                imageObject.addProperty(KEY_IMAGE, image.resourceLocation().getNamespace() + ":" + image.resourceLocation().getPath());
            if (image.showBorder())
                imageObject.addProperty(KEY_SHOW_BORDER, true);
            if (!image.type().equals(QuestBookScreen.QuestBookImage.Type.ITEM))
                imageObject.addProperty(KEY_TYPE, image.type().toString().toLowerCase());

            JsonArray imagesArray;
            if (json.has(KEY_IMAGES))
                imagesArray = json.getAsJsonArray(KEY_IMAGES);
            else{
                imagesArray = new JsonArray();
                json.add(KEY_IMAGES, imagesArray);
            }
            imagesArray.add(imageObject);
            return this;
        }

        Builder setImages(QuestBookScreen.QuestBookImage ... images){
            for (QuestBookScreen.QuestBookImage i : images){
              addImage(i);
            }
            return this;
        }

        Builder setText(String text, boolean showTitle){
            JsonObject textObject = new JsonObject();
            textObject.addProperty(KEY_CONTENT, text);
            textObject.addProperty(KEY_SHOW_TITLE, showTitle);
            json.add(KEY_TEXT, textObject);
            return this;
        }

        void save(List<JsonObject> pages){
            pages.add(this.json);
        }
    }
}
