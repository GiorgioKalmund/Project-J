package com.mgmstudios.projectj.screen.custom.quest_book;

import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookImage;

import java.util.ArrayList;
import java.util.List;

public class BookPage {

    public static BookPage EMPTY = new BookPage();
    protected List<FormattedCharSequence> textComponents;
    protected List<QuestBookImage> questBookImages;
    protected List<QuestBookImage> secondaryImages;
    protected Component pageMsg;
    protected String shortcut;

    public Component pageMsg() {
        return pageMsg;
    }

    public void setPageMsg(Component msg){
        this.pageMsg = msg;
    }

    public List<FormattedCharSequence> components() {
        return textComponents;
    }

    public void setComponents(List<FormattedCharSequence> components){
        this.textComponents = components;
    }

    public QuestBookImage image(int index) {
        if (index >= questBookImages.size())
            return QuestBookImage.EMPTY;
        return questBookImages.get(index);
    }
    public QuestBookImage image() {
        return image(0);
    }

    public void setImage(QuestBookImage image){
        if (questBookImages.isEmpty())
            addImage(image);
        else
            questBookImages.set(0, image);
    }

    public void addImage(QuestBookImage image){
        questBookImages.add(image);
    }

    public BookPage(){
        clear();
    }

    public BookPage(List<FormattedCharSequence> textComponents, List<QuestBookImage> images, Component pageMsg){
       this(textComponents, images, List.of(), pageMsg);
    }
    public BookPage(List<FormattedCharSequence> textComponents, List<QuestBookImage> images, List<QuestBookImage> secondaryImages, Component pageMsg){
        this(textComponents, images, secondaryImages, pageMsg, null);
    }
    public BookPage(List<FormattedCharSequence> textComponents, List<QuestBookImage> images, List<QuestBookImage> secondaryImages, Component pageMsg, String shortcut){
        this.textComponents = textComponents;
        this.secondaryImages = secondaryImages;
        this.questBookImages = images;
        this.pageMsg = pageMsg;
        this.shortcut = shortcut;
    }

    public static BookPage empty(){
        return new BookPage();
    }

    public void clear(){
        textComponents = new ArrayList<>();
        questBookImages = new ArrayList<>();
        questBookImages.add(QuestBookImage.empty());
        pageMsg = Component.literal("");
    }

    public void clearImages(){
        this.questBookImages.clear();
    }

    public List<QuestBookImage> images() {
        return questBookImages;
    }

    public List<QuestBookImage> secondaryImages() {
        return secondaryImages;
    }

    @Override
    public String toString() {
        return "BookPage{" +
                "textComponents=" + textComponents +
                ", questBookImages=" + questBookImages +
                ", secondaryImages=" + secondaryImages +
                ", pageMsg=" + pageMsg +
                ", shortcut='" + shortcut + '\'' +
                '}';
    }
}
