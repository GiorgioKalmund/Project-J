package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.entity.projectile.ObsidianArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ObsidianArrowItem extends ArrowItem {
    public ObsidianArrowItem(Properties properties) {
        super(properties);
    }

    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, @Nullable ItemStack weapon) {
        return new ObsidianArrow(owner, level, itemStack.copyWithCount(1), weapon);
    }
    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ObsidianArrow obsidianArrow = new ObsidianArrow(position.x(), position.y(), position.z(), level, itemStack.copyWithCount(1), null);
        obsidianArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return obsidianArrow;
    }
}
