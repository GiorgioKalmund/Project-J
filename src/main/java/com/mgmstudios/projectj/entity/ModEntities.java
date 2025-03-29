package com.mgmstudios.projectj.entity;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.custom.SittableEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(ProjectJ.MOD_ID);

    public static final Supplier<EntityType<SittableEntity>> SITTABLE_ENTITY =
            ENTITY_TYPES.registerEntityType("sittable_entity", SittableEntity::new, MobCategory.MISC,
                        builder -> builder.sized(0.5f, 0.5f)
                    );

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
