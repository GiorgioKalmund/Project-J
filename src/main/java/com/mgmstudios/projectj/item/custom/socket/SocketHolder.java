package com.mgmstudios.projectj.item.custom.socket;

import net.minecraft.core.component.DataComponentType;

import java.util.ArrayList;
import java.util.List;

public interface SocketHolder {
    public List<DataComponentType<?>> getAllowedTypes();
    public List<DataComponentType<?>> getExcludedTypes();
}
