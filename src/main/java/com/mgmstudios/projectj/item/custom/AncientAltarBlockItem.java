package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.AncientAltarBlockEntity;
import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.item.ModItems;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AncientAltarBlockItem extends BlockItem {
    public AncientAltarBlockItem(Properties properties) {
        super(ModBlocks.ANCIENT_ALTAR.get(), properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        boolean result = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof AncientAltarBlockEntity be) {
                System.out.println("Applying map 2");
                be.applyComponentsFromItemStack(stack);
                DataComponentMap map = stack.getComponents();
                be.applyComponentsToFields(map);
            }
        }
        return result;    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, components, tooltipFlag);

        DataComponentMap map = itemStack.getComponents();
        map.forEach(( value) ->{
                    if (value.type().equals(ModDataComponents.SIDE_INVENTORY.get())){
                        ItemContainerContents bowlContents = (ItemContainerContents) value.value();
                        if (bowlContents.getSlots() > 0 && bowlContents.getStackInSlot(0).is(ModItems.FILLED_CRUDE_SACRIFICE_BOWL)){
                            components.add(Component.literal("§4Bloody§r"));
                        }
                    }
                    if (value.type().equals(ModDataComponents.MAIN_INVENTORY.get())){
                        ItemContainerContents containerContents = (ItemContainerContents) value.value();
                        for (int index = 0; index < containerContents.getSlots(); index++){
                            ItemStack stack = containerContents.getStackInSlot(index);
                            components.add(Component.literal("§7" + stack.getCount() + "x " + stack.getItemName().getString() + "§r"));
                        }
                    }
                    if (value.type().equals(ModDataComponents.FLUID_INVENTORY.get())){
                        SimpleFluidContent fluidContents = (SimpleFluidContent) value.value();
                        FluidStack fluidStack = new FluidStack(fluidContents.getFluid(), fluidContents.getAmount());
                        if (!fluidStack.isEmpty()){
                            components.add(Component.literal("§6" + fluidStack.getAmount() + "mb §r").append(Component.translatable(fluidContents.getFluid().getFluidType().getDescriptionId()).withColor(0xFFAA00)));
                        }
                    }
                }
        );
    }
}
