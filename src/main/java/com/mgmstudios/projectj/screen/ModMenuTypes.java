package com.mgmstudios.projectj.screen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.screen.custom.AdobeFurnaceMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUES = 
        DeferredRegister.create(Registries.MENU, ProjectJ.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<AdobeFurnaceMenu>> ADOBE_FURNACE_MENU = 
        MENUES.register("adobe_furnace_menu", () -> IMenuTypeExtension.create(AdobeFurnaceMenu::new));

    public static void register(IEventBus eventBus){
        MENUES.register(eventBus);
    }
}
