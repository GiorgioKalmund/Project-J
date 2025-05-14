package com.mgmstudios.projectj.screen.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.SocketWorkbenchBlockEntity;
import com.mgmstudios.projectj.item.custom.socket.SocketGemItem;
import com.mgmstudios.projectj.item.custom.socket.SocketHolder;
import com.mgmstudios.projectj.screen.ModMenuTypes;
import com.mgmstudios.projectj.util.Socket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.SOCKETS;


public final class SocketWorkbenchMenu extends ItemCombinerMenu {
    public SocketWorkbenchMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public SocketWorkbenchMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.SOCKET_WORKBENCH_MENU.get(), containerId, inventory, access, createInputSlotDefinitions());
        access.execute((level, pos) -> {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SocketWorkbenchBlockEntity socketBE) {
                ItemStack firstItem = socketBE.getInventory().getStackInSlot(0);
                ItemStack secondItem = socketBE.getInventory().getStackInSlot(1);
                inputSlots.setItem(0, firstItem);
                inputSlots.setItem(1, secondItem);
                resultSlots.setItem(0, socketBE.getInventory().getStackInSlot(2));
            }
        });
    }

    private static ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create().withSlot(0, 27, 47, (p_266635_) -> true).withSlot(1, 76, 47, (p_266634_) -> true).withResultSlot(2, 134, 47).build();
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        ItemStack inputItemStack = this.inputSlots.getItem(0);
        ItemStack gemItemStack = this.inputSlots.getItem(1);
        int itemsAbleToConvert = itemsAbleToConvert();
        gemItemStack.shrink(itemsAbleToConvert);
        inputItemStack.shrink(itemsAbleToConvert);
        System.out.println("Converted " + itemsAbleToConvert + " / " + inputItemStack + " / " + gemItemStack);

        // Anvil Sound
        access.execute((level, blockPos) -> {
            level.levelEvent(1030, blockPos, 0);
        });
    }

    @Override
    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        access.execute((level, pos) -> {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SocketWorkbenchBlockEntity socketBE) {
                socketBE.getInventory().setStackInSlot(0, inputSlots.getItem(0));
                socketBE.getInventory().setStackInSlot(1, inputSlots.getItem(1));
                socketBE.getInventory().setStackInSlot(2, resultSlots.getItem(0));
            }
        });
    }

    @Override
    public void removed(Player player) {

    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return blockState.is(ModBlocks.SOCKET_WORKBENCH);
    }

    @Override
    public void createResult() {
        ItemStack itemstack = this.inputSlots.getItem(0);
        ItemStack gemStack = this.inputSlots.getItem(1);
        Item item = itemstack.getItem();
        Item gem = gemStack.getItem();
        ItemStack resultStack = itemstack.copy();
        resultStack.setCount(itemsAbleToConvert());
        if (item instanceof SocketHolder holder && gem instanceof SocketGemItem socketGemItem){
            for (Socket s : socketGemItem.getSocketList()){
                resultStack = Socket.addSocket(resultStack, s, socketGemItem.additive() || holder.canAddExtraSlots());
            }
            if (!resultStack.isEmpty()){
                resultSlots.setItem(0, resultStack);
                this.broadcastChanges();
            } else {
                resultSlots.setItem(0, ItemStack.EMPTY);
            }
        } else {
            resultSlots.setItem(0, ItemStack.EMPTY);
            this.broadcastChanges();
        }
    }

    public int itemsAbleToConvert(){
        ItemStack itemstack = this.inputSlots.getItem(0);
        ItemStack gemStack = this.inputSlots.getItem(1);
        return Math.min(itemstack.getCount(),gemStack.getCount());
    }
}
