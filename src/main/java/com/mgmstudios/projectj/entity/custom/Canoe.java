package com.mgmstudios.projectj.entity.custom;

import com.mgmstudios.projectj.entity.ModEntities;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class Canoe extends Boat {

    private int length;
    /// max length = 7, min length = 3;

    public Canoe(int length, Level level, Supplier<Item> dropItem) {
        super(ModEntities.CANOE_ENTITY_3.get(), level, dropItem);
        this.length = length;
    }



}
