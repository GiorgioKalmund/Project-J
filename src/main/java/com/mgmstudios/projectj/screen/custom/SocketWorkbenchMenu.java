package com.mgmstudios.projectj.screen.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.SocketWorkbenchBlockEntity;
import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.item.custom.socket.SocketGemItem;
import com.mgmstudios.projectj.item.custom.socket.SocketHolder;
import com.mgmstudios.projectj.screen.ModMenuTypes;
import com.mgmstudios.projectj.util.Socket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.SOCKETS;
import static com.mgmstudios.projectj.util.Socket.getSocketList;


public final class SocketWorkbenchMenu extends ItemCombinerMenu {
    private final DataSlot errorCode;

    public SocketWorkbenchMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public SocketWorkbenchMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.SOCKET_WORKBENCH_MENU.get(), containerId, inventory, access, createInputSlotDefinitions());
        this.errorCode = DataSlot.standalone();
        this.addDataSlot(this.errorCode);
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

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return errorCode.get() == 0;
    }

    private static ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create().withSlot(0, 27, 47, (p_266635_) -> true).withSlot(1, 76, 47, (p_266634_) -> true).withResultSlot(2, 134, 47).build();
    }

    public int getErrorCode() {
        return errorCode.get();
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        ItemStack inputItemStack = this.inputSlots.getItem(0);
        ItemStack gemItemStack = this.inputSlots.getItem(1);
        int itemsAbleToConvert = itemsAbleToConvert();
        gemItemStack.shrink(itemsAbleToConvert);
        inputItemStack.shrink(itemsAbleToConvert);
        System.out.println("Converted " + itemsAbleToConvert + " / " + inputItemStack + " / " + gemItemStack);
        errorCode.set(0);

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
        errorCode.set(0);
        ItemStack itemstack = this.inputSlots.getItem(0);
        ItemStack gemStack = this.inputSlots.getItem(1);
        Item item = itemstack.getItem();
        Item gem = gemStack.getItem();
        ItemStack resultStack = itemstack.copy();
        resultStack.setCount(itemsAbleToConvert());
        List<Socket> gems = getSocketList(gemStack);
        if (item instanceof SocketHolder holder && gem instanceof SocketGemItem){
            for (Socket s : gems){
                resultStack = Socket.addSocket(resultStack, s, holder.canAddExtraSlots());
                if (resultStack.isEmpty()) break;
            }
            if (!resultStack.isEmpty()){
                resultSlots.setItem(0, resultStack);
                this.broadcastChanges();
            } else {
                errorCode.set(1);
                resultSlots.setItem(0, ItemStack.EMPTY);
            }
        } else if (item instanceof SocketGemItem && gemStack.is(ModItems.TOPAZ_GEM)){
            resultStack.set(ModDataComponents.Sockets.LEGENDARY_STATUS, true);
            List<Socket> foundSockets = resultStack.getOrDefault(SOCKETS, new ArrayList<>());
            List<Socket> newSockets = new ArrayList<>(foundSockets);
            Optional<Socket> optionalSocket = newSockets.stream().filter(s -> !s.isAdditive()).findFirst();
            if (newSockets.isEmpty() || optionalSocket.isEmpty()){
                errorCode.set(2);
                resultSlots.setItem(0, ItemStack.EMPTY);
            }
            else {
                int replaceIndex = newSockets.indexOf(optionalSocket.get());
                newSockets.set(replaceIndex, optionalSocket.get().copy().setAdditive());
                resultStack.set(SOCKETS, newSockets);
                resultSlots.setItem(0, resultStack);
                this.broadcastChanges();
            }
        } else {
            errorCode.set(3);
            resultSlots.setItem(0, ItemStack.EMPTY);
        }
    }

    public int itemsAbleToConvert(){
        ItemStack itemstack = this.inputSlots.getItem(0);
        ItemStack gemStack = this.inputSlots.getItem(1);
        return Math.min(itemstack.getCount(),gemStack.getCount());
    }
}
