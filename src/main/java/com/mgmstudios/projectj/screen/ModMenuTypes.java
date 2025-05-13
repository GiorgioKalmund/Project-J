package com.mgmstudios.projectj.screen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.AdobeFurnaceMenu;

import com.mgmstudios.projectj.screen.custom.SocketWorkbenchMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;
import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUES = 
        DeferredRegister.create(Registries.MENU, ProjectJ.MOD_ID);

    public static final Supplier<MenuType<AdobeFurnaceMenu>> ADOBE_FURNACE_MENU = MENUES.register("adobe_furnace_menu",
            () -> new MenuType<>(AdobeFurnaceMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final Supplier<MenuType<SocketWorkbenchMenu>> SOCKET_WORKBENCH_MENU = MENUES.register("socket_workbench_menu",
            () -> new MenuType<>(SocketWorkbenchMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static void register(IEventBus eventBus){
        MENUES.register(eventBus);
    }
}
