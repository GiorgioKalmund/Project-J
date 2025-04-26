package com.mgmstudios.projectj.entity.projectile;

import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ObsidianArrow extends AbstractArrow  {


    public ObsidianArrow(EntityType<? extends AbstractArrow> p_331098_, Level p_331626_) {
        super(p_331098_, p_331626_);
    }

    public ObsidianArrow(double x, double y, double z, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.OBSIDIAN_ARROW_ENTITY.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public ObsidianArrow(LivingEntity owner, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.OBSIDIAN_ARROW_ENTITY.get() , owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.OBSIDIAN_ARROW.get());
    }
}
