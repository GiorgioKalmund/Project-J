package com.mgmstudios.projectj.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.WrittenBookContent;

import java.util.ArrayList;

public class QuestBook extends WrittenBookItem {

    static java.util.List<net.minecraft.server.network.Filterable<net.minecraft.network.chat.Component>> PAGES;
    public QuestBook(String title, String author, Properties properties) {
        super(properties.component(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(Filterable.from(FilteredText.fullyFiltered(title)), author, 0, PAGES, true)));
    }

    static {
        PAGES = new ArrayList<>();
        PAGES.add(Filterable.passThrough(Component.translatable("quest_book.projectj.page0")));
        PAGES.add(Filterable.passThrough(Component.translatable("quest_book.projectj.page1")));
        PAGES.add(Filterable.passThrough(Component.translatable("quest_book.projectj.page2")));
        PAGES.add(Filterable.passThrough(Component.translatable("quest_book.projectj.page3")));
    }
}
