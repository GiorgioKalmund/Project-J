package com.mgmstudios.projectj.entity;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.custom.*;
import com.mgmstudios.projectj.entity.projectile.ObsidianArrow;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
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
    public static final Supplier<EntityType<LittleManEntity>> LITTLE_MAN_ENTITY =
            ENTITY_TYPES.registerEntityType("little_man",
                    LittleManEntity::new, MobCategory.MISC,
                    builder -> builder.sized(0.5f, 1.4f)
                    );

    public static final Supplier<EntityType<LittleKingEntity>> LITTLE_KING_ENTITY =
            ENTITY_TYPES.registerEntityType("little_king",
                    LittleKingEntity::new, MobCategory.MISC,
                    builder -> builder.sized(0.5f, 1.2f)
            );

    public static final Supplier<EntityType<QuetzalEntity>> QUETZAL_ENTITY =
            ENTITY_TYPES.registerEntityType("quetzal",
                    QuetzalEntity::new, MobCategory.MISC,
                    builder -> builder.sized(0.5f, 1.2f)
            );

    public static final Supplier<EntityType<ObsidianArrow>> OBSIDIAN_ARROW_ENTITY =
            ENTITY_TYPES.registerEntityType("obsidian_arrow",
                    ObsidianArrow::new, MobCategory.MISC,
                    builder -> builder.noLootTable().sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20)
            );

    public static final Supplier<EntityType<RunnerEntity>> RUNNER_ENTITY=
            ENTITY_TYPES.registerEntityType("runner",
                    RunnerEntity::new, MobCategory.MONSTER,
                    builder -> builder
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .passengerAttachments(2.075F)
                            .ridingOffset(-0.7F)
                            .clientTrackingRange(8)
            );

    public static final Supplier<EntityType<Canoe>> CANOE_ENTITY_3 =
            ENTITY_TYPES.registerEntityType("canoe_entity_3",
                    (type, level) -> new Canoe(3, level, Blocks.STRIPPED_SPRUCE_LOG::asItem), MobCategory.MISC,
                    builder -> builder.noLootTable().sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20)
            );

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
