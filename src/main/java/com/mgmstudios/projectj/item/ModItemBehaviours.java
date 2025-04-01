package com.mgmstudios.projectj.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ModItemBehaviours {
    public static class ModDispenserBehaviours{

        static DispenseItemBehavior dispenseitembehavior = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack execute(BlockSource p_338297_, ItemStack p_338735_) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)p_338735_.getItem();
                BlockPos blockpos = p_338297_.pos().relative(p_338297_.state().getValue(DispenserBlock.FACING));
                Level level = p_338297_.level();
                if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, p_338735_)) {
                    dispensiblecontaineritem.checkExtraContent(null, level, p_338735_, blockpos);
                    return this.consumeWithRemainder(p_338297_, p_338735_, new ItemStack(Items.BUCKET));
                } else {
                    return this.defaultDispenseItemBehavior.dispense(p_338297_, p_338735_);
                }
            }
        };

        public static void bootstrap(){
            DispenserBlock.registerBehavior(ModItems.LIQUID_PYRITE_BUCKET.get(), dispenseitembehavior);
        }
    }
}
