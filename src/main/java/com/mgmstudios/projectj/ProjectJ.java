package com.mgmstudios.projectj;

import com.mgmstudios.projectj.block.entity.renderer.*;
import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.entity.client.runner.RunnerRenderer;
import com.mgmstudios.projectj.item.custom.MagnetItem;
import com.mgmstudios.projectj.particle.ModParticles;
import com.mgmstudios.projectj.particle.TeleportationParticles;
import com.mgmstudios.projectj.worldgen.*;

import com.mgmstudios.projectj.worldgen.regions.AdobeDesertRegion;
import com.mgmstudios.projectj.worldgen.regions.SerpentiniteHillsRegion;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FuelValues;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidType;
import org.slf4j.Logger;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import com.mgmstudios.projectj.client.ProjectJClientExtension;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.client.canoe.CanoeModel;
import com.mgmstudios.projectj.entity.client.canoe.CanoeRenderer;
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
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.util.Set;

import static com.mgmstudios.projectj.component.ModDataComponents.MAGNET_ACTIVE;

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

        ModMenuTypes.register(modEventBus);
        ModDataComponents.register(modEventBus);
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
        ModSounds.register(modEventBus);
        ModStructures.register(modEventBus);
        ModStructurePlacements.register(modEventBus);
        ModParticles.register(modEventBus);
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
        event.enqueueWork(() ->
        {
            // Weights are kept intentionally low as we add minimal biomes
            Regions.register(new AdobeDesertRegion(ResourceLocation.fromNamespaceAndPath(MOD_ID, "overworld_1"), 4));
            Regions.register(new SerpentiniteHillsRegion(ResourceLocation.fromNamespaceAndPath(MOD_ID, "overworld_2"), 4));

            // Register our surface rules
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());

            FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(ModFluidTypes.PYRITE_TYPE.get(), (fluidState) -> fluidState.isSource() ? ModBlocks.SERPENTINITE_ROCK.get().defaultBlockState() : ModBlocks.COBBLED_SERPENTINITE.get().defaultBlockState()));
        });
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
            EntityRenderers.register(ModEntities.RUNNER_ENTITY.get(), RunnerRenderer::new);
            EntityRenderers.register(ModEntities.QUETZAL_ENTITY.get(), QuetzalRenderer::new);
            EntityRenderers.register(ModEntities.OBSIDIAN_ARROW_ENTITY.get(), ObsidianArrowRenderer::new);
            EntityRenderers.register(ModEntities.CANOE_ENTITY_3.get(), context -> new CanoeRenderer(context, CanoeModel.LAYER_LOCATION));
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.ANCIENT_ALTAR_BE.get(), AncientAltarEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.TELEPORTATION_PAD_BE.get(), TeleportationBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.METATE_BE.get(), MetateEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.JADE_CRYSTAL_BE.get(), JadeCrystalEntityRenderer::new);
        }



        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(ModParticles.TELEPORTATION_PARTICLES.get(), TeleportationParticles.Provider::new);
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents{
        @SubscribeEvent
        private static void registerCapabilities(RegisterCapabilitiesEvent event) {
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                    ModBlockEntities.ANCIENT_ALTAR_BE.get(),
                    (entity, context) -> entity.getCapability(Capabilities.FluidHandler.BLOCK, entity)
            );
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.ANCIENT_ALTAR_BE.get(), (entity, context) -> entity.getInventory());

            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.METATE_BE.get(), (entity, context) -> entity.getInventory());
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.JADE_CRYSTAL_BE.get(), (entity, context) -> entity.getInventory());
        }

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class GameEvents{
        @SubscribeEvent
        private static void magnetDelayAfterItemToss(ItemTossEvent event) {
            Player player = event.getPlayer();
            Inventory inventory = player.getInventory();
            for(int i = 0; i < inventory.getContainerSize(); ++i) {
                ItemStack itemstack = inventory.getItem(i);
                if (itemstack.getItem() instanceof MagnetItem){
                    boolean magnetActive = itemstack.getOrDefault(MAGNET_ACTIVE, false);
                    if (magnetActive){
                       event.getEntity().setPickUpDelay(20);
                    }
                }
            }
        }

    }
}
