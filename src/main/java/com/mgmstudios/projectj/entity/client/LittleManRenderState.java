package com.mgmstudios.projectj.entity.client;

import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

public class LittleManRenderState extends LivingEntityRenderState {
    public final AnimationState idle = new AnimationState();
}
