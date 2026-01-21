package com.fireball_stick.entity;

import com.fireball_stick.customFunctions.fireball.CustomFireball;
import com.fireball_stick.customFunctions.tnt.CustomTnt;
import com.fireball_stick.initialization.ModInitialization;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;

public class ModEntities {
    //CUSTOM TNT
    public static final ResourceKey<EntityType<?>> CUSTOM_TNT_KEY =
            key("custom_tnt");

    public static final EntityType<CustomTnt> CUSTOM_TNT =
            register(CUSTOM_TNT_KEY,
                            EntityType
                                    .Builder
                                    .of(CustomTnt::new, MobCategory.MISC)
                                    .sized(0.98F, 0.98F)
                                    .build(CUSTOM_TNT_KEY));

    //CUSTOM FIREBALL
    public static final ResourceKey<EntityType<?>> CUSTOM_FIREBALL_KEY =
            key("custom_fireball");

    public static final EntityType<CustomFireball> CUSTOM_FIREBALL =
            register(CUSTOM_FIREBALL_KEY,
                    EntityType
                            .Builder
                            .of(CustomFireball::new, MobCategory.MISC)
                            .sized(0.98F, 0.98F)
                            .build(CUSTOM_FIREBALL_KEY));

    //HELPER METHODS
    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(
                Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(ModInitialization.MOD_ID, name));
    }

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType<T> entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, entityType);
    }

    //Initializes the entity
    public static void init() {}
}
