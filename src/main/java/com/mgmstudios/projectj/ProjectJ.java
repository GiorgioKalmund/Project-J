package com.mgmstudios.projectj;

import org.slf4j.Logger;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import com.mgmstudios.projectj.block.entity.renderer.AncientAltarEntityRenderer;
import com.mgmstudios.projectj.block.entity.renderer.MetateEntityRenderer;
import com.mgmstudios.projectj.block.entity.renderer.SittableEntityRenderer;
import com.mgmstudios.projectj.client.ProjectJClientExtension;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.client.little_king.LittleKingRenderer;
import com.mgmstudios.projectj.entity.client.little_man.LittleManRenderer;
import com.mgmstudios.projectj.entity.client.obsidian_arrow.ObsidianArrowRenderer;
import com.mgmstudios.projectj.entity.client.quetzal.QuetzalRenderer;
import com.mgmstudios.projectj.fluid.ModFluidTypes;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.item.ModCreativeModeTabs;
import com.mgmstudios.projectj.item.ModItemBehaviours;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.loot.ModLootTables;
import com.mgmstudios.projectj.recipe.ModRecipeBookCategories;
import com.mgmstudios.projectj.recipe.ModRecipeSerializers;
import com.mgmstudios.projectj.recipe.ModRecipeTypes;
import com.mgmstudios.projectj.screen.ModMenuTypes;
import com.mgmstudios.projectj.screen.custom.AdobeFurnaceScreen;
import com.mgmstudios.projectj.sound.ModSounds;
import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ProjectJ.MOD_ID)
public class ProjectJ
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "projectj";
    private static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ProjectJ(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModSounds.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModRecipeBookCategories.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModLootTables.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerScreens);
        modEventBus.addListener(ProjectJClientExtension::registerClientItemExtensions);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModItemBehaviours.ModDispenserBehaviours.bootstrap();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    public void registerScreens(RegisterMenuScreensEvent event){
        event.register(ModMenuTypes.ADOBE_FURNACE_MENU.get(), AdobeFurnaceScreen::new);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.SITTABLE_ENTITY.get(), SittableEntityRenderer::new);
            EntityRenderers.register(ModEntities.LITTLE_MAN_ENTITY.get(), LittleManRenderer::new);
            EntityRenderers.register(ModEntities.LITTLE_KING_ENTITY.get(), LittleKingRenderer::new);
            EntityRenderers.register(ModEntities.QUETZAL_ENTITY.get(), QuetzalRenderer::new);
            EntityRenderers.register(ModEntities.OBSIDIAN_ARROW_ENTITY.get(), ObsidianArrowRenderer::new);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.ANCIENT_ALTAR_BE.get(), AncientAltarEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.METATE_BE.get(), MetateEntityRenderer::new);
        }

        @SubscribeEvent
        private static void registerCapabilities(RegisterCapabilitiesEvent event) {
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                    ModBlockEntities.ANCIENT_ALTAR_BE.get(),
                    (entity, context) -> entity.getCapability(Capabilities.FluidHandler.BLOCK, entity)
            );
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.ANCIENT_ALTAR_BE.get(), (entity, context) -> entity.getInventory());

            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.METATE_BE.get(), (entity, context) -> entity.getInventory());
        }
    }
}
