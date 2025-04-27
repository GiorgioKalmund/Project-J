package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.network.Filterable;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.Level;

public class QuestBook extends WrittenBookItem {

    public QuestBook(String title, String author, java.util.List<net.minecraft.server.network.Filterable<net.minecraft.network.chat.Component>> pages, Properties properties) {
        super(properties.component(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(Filterable.from(FilteredText.fullyFiltered(title)), author, 0, pages, true)));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()){
            openGUI(stack);
        }
        return InteractionResult.CONSUME;
    }

    public static void openGUI(ItemStack stack) {
        Minecraft.getInstance().setScreen(new QuestBookScreen(BookViewScreen.BookAccess.fromItem(stack)));
    }
}
