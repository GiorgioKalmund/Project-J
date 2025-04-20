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
    protected Component pageMsg;

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
        return questBookImages.get(index);
    }
    public QuestBookImage image() {
        return image(0);
    }

    public void setImage(QuestBookImage image){
        questBookImages.set(0, image);
    }

    public void addImage(QuestBookImage image){
        questBookImages.add(image);
    }

    public BookPage(){
        clear();
    }

    public BookPage(List<FormattedCharSequence> textComponents, List<QuestBookImage> images, Component pageMsg){
        this.textComponents = textComponents;
        this.questBookImages = images;
        this.pageMsg = pageMsg;
    }

    public void clear(){
        textComponents = new ArrayList<>();
        questBookImages = new ArrayList<>();
        questBookImages.add(new QuestBookImage());
        pageMsg = Component.literal("");
    }

}
