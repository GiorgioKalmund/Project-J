package com.mgmstudios.projectj.screen.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.SocketWorkbenchBlockEntity;
import com.mgmstudios.projectj.item.custom.socket.SocketGemItem;
import com.mgmstudios.projectj.item.custom.socket.SocketHolder;
import com.mgmstudios.projectj.screen.ModMenuTypes;
import com.mgmstudios.projectj.util.Socket;
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
    }

    private static ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create().withSlot(0, 27, 47, (p_266635_) -> true).withSlot(1, 76, 47, (p_266634_) -> true).withResultSlot(2, 134, 47).build();
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        this.inputSlots.setItem(0, ItemStack.EMPTY);
        ItemStack gemItemStack = this.inputSlots.getItem(1);
        if (!gemItemStack.isEmpty()) {
            gemItemStack.shrink(1);
            this.inputSlots.setItem(1, gemItemStack);
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }
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
        if (item instanceof SocketHolder && gem instanceof SocketGemItem socketGemItem){
            System.out.println("Attempting to craft on itemStack " + itemstack.get(SOCKETS));
            ItemStack resultStack = Socket.addSocket(itemstack, socketGemItem.getSocket());
            if (!resultStack.isEmpty()){
                resultSlots.setItem(0, resultStack);
            } else {
                resultSlots.setItem(0, ItemStack.EMPTY);
            }
        } else {
            resultSlots.setItem(0, ItemStack.EMPTY);
        }
        this.broadcastChanges();
    }
}
