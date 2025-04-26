package com.mgmstudios.projectj.sound;

import java.util.function.Supplier;

import com.mgmstudios.projectj.ProjectJ;
import com.nimbusds.jose.util.Resource;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ProjectJ.MOD_ID);

    public static final Holder<SoundEvent> DEATH_WHISTLE_SOUND = registerSoundEvent("death_whistle_sound");

    private static Holder<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name); 
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
