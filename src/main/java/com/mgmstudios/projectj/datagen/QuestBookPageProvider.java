package com.mgmstudios.projectj.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookImage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookTemplateType;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.ContentsPageScreen;
import com.mgmstudios.projectj.util.ItemLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
                .setTemplate(QuestBookTemplateType.COVER)
                .showPageMessage(true)
                .setPageMessage("§f§lNo. 1§r")
                .save(pages);

        // TODO: Maybe some sort of anchor system so shifting indices around does not mess up connections
        Builder.create()
                .setTemplate(QuestBookTemplateType.CONTENTS_PAGE)
                .setTemplateSpacing(30)
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.JADE, "Basics", 2))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.TELEPORTATION_CORE, "TP", 4))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(Items.WHEAT, "1", 1))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(Items.ACACIA_BOAT, "1", 1))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.VOODOO_CATCHER, "Voodoo", 10))
                .setText("§lTable of Contents§r", true)
                .addImage(new QuestBookImage(ModItems.RAW_JADE))
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.CHAPTER_1_IMAGE)
                .setTemplateChapterTitle("§f§o§lThe Basics§r")
                .setText("§f§oWelcome to Project J. Let's cover some of the mod's basics items and elements!§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .setImages(new QuestBookImage(ModItems.LITTLE_KING_SPAWN_EGG, true))
                .setText("§nRandom Title§r\nThis is the description.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.CHAPTER_2_IMAGE)
                .setTemplateChapterTitle("§f§o§lTeleportation§r")
                .setText("§f§oSwoosh!§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(30)
                .setImages(new QuestBookImage(ModItems.TELEPORTATION_CORE))
                .setImages(new QuestBookImage(ModBlocks.TELEPORTATION_PAD))
                .setImages(new QuestBookImage(ModItems.TELEPORTATION_KEY))
                .setText("Teleportation is cool!", false)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(30)
                .setImages(new QuestBookImage(ModItems.TELEPORTATION_CORE))
                .setImages(new QuestBookImage(ModBlocks.TELEPORTATION_PAD))
                .setImages(new QuestBookImage(ModItems.TELEPORTATION_KEY))
                .setImages(new QuestBookImage(Items.APPLE))
                .setText("Teleportation is cool and Apple!", false)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.DOUBLE_ITEM_SHOWCASE)
                .setTemplateSpacing(20)
                .setImages(new QuestBookImage(ModItems.TELEPORTATION_CORE))
                .setImages(new QuestBookImage(ModBlocks.TELEPORTATION_PAD))
                .setImages(new QuestBookImage(ModItems.TELEPORTATION_KEY))
                .setImages(new QuestBookImage(Items.APPLE))
                .setText("Two", false)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .setTemplateSpacing(30)
                .setTemplateRecipeResult(new QuestBookImage(ModItems.TELEPORTATION_CORE,true))
                .setImages(new QuestBookImage(ModItems.JADE,4, false))
                .setImages(new QuestBookImage(Items.ENDER_PEARL, 2, false))
                .setImages(new QuestBookImage(Items.WIND_CHARGE))
                .setText("§nTeleportation Core§r\n\nThe Teleportation Core opens the door to a whole lot of blocks and items allowing you to travel faster.", true)
                .save(pages);


        Builder.create()
                .setTemplate(QuestBookTemplateType.PROCESS)
                .defaultTemplateSpacing()
                .setTemplateShowFuel(true)
                .setPageMessage("§oSecret Message!§r")
                .addImage(new QuestBookImage(ModItems.RAW_JADE))
                .addImage(QuestBookImage.PROCESS_IMAGE)
                .addImage(new QuestBookImage(ModItems.JADE))
                .addImage(QuestBookImage.ADOBE_LIT_PROCESS_IMAGE)
                .setText("Hello World!")
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

        private JsonObject json = new JsonObject();

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

        Builder setTemplate(QuestBookTemplateType type){
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

        Builder setTemplateRecipeResult(QuestBookImage recipeResult){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            JsonArray recipeResults;
            if (templateObject.has(KEY_RECIPE_RESULT))
                recipeResults = templateObject.getAsJsonArray(KEY_RECIPE_RESULT);
            else{
                recipeResults = new JsonArray();
                templateObject.add(KEY_RECIPE_RESULT, recipeResults);
            }

            addImage(recipeResult, recipeResults);
            return this;
        }

        Builder setTemplateChapterTitle(String chapterTitle){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject != null){
                templateObject.addProperty(KEY_CHAPTER_TITLE, chapterTitle);
            }
            return this;
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
        Builder addImage(QuestBookImage image){
            JsonArray imagesArray;
            if (json.has(KEY_IMAGES))
                imagesArray = json.getAsJsonArray(KEY_IMAGES);
            else{
                imagesArray = new JsonArray();
                json.add(KEY_IMAGES, imagesArray);
            }

            addImage(image, imagesArray);
            return this;
        }

        Builder addImage(QuestBookImage image, JsonArray array){
            JsonObject imageObject = new JsonObject();
            if (image.hasShortHand())
                imageObject.addProperty(KEY_IMAGE, image.shorthand());
            else
                imageObject.addProperty(KEY_IMAGE, image.resourceLocation().getNamespace() + ":" + image.resourceLocation().getPath());
            if (image.count() > 1)
                imageObject.addProperty(KEY_COUNT, image.count());
            if (image.showBorder())
                imageObject.addProperty(KEY_SHOW_BORDER, true);
            if (!image.type().equals(QuestBookImage.Type.ITEM))
                imageObject.addProperty(KEY_TYPE, image.type().toString().toLowerCase());

            array.add(imageObject);
            return this;
        }

        Builder setImages(QuestBookImage ... images){
            for (QuestBookImage i : images){
              addImage(i);
            }
            return this;
        }

        Builder setText(String text, boolean showTitle, boolean alignCenter){
            JsonObject textObject = new JsonObject();
            textObject.addProperty(KEY_CONTENT, text);
            textObject.addProperty(KEY_SHOW_TITLE, showTitle);
            textObject.addProperty(KEY_ALIGN_CENTER, alignCenter);
            json.add(KEY_TEXT, textObject);
            return this;
        }
        Builder setText(String text, boolean showTitle){
            return setText(text, showTitle, false);
        }

        Builder setText(String text){
            return setText(text, false, false);
        }

        void save(List<JsonObject> pages){
            pages.add(this.json);
        }
    }
}
