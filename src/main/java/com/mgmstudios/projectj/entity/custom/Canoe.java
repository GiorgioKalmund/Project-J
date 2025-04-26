package com.mgmstudios.projectj.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class Canoe extends Boat {

    private int length;
    /// max length = 7, min length = 3;

    public Canoe(EntityType<? extends Boat> entityType, int length, Level level, Supplier<Item> dropItem) {
        super(entityType, level, dropItem);
        this.length = length;
    }



}
