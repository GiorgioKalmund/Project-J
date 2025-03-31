package com.mgmstudios.projectj.client;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.fluid.ModFluidTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.joml.Vector4f;

public class ProjectJClientExtension {
    public static final IClientFluidTypeExtensions LIQUID_PYRITE = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "block/liquid_pyrite_still");
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "block/liquid_pyrite_flow");
        }
    };

    public static void registerClientItemExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(LIQUID_PYRITE, ModFluidTypes.PYRITE_TYPE.get());
    }
}
