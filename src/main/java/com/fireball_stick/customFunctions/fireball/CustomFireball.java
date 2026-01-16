package com.fireball_stick.customFunctions.fireball;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.Level;

public class CustomFireball extends LargeFireball {

    public CustomFireball(EntityType<? extends LargeFireball> type, Level level) {
        super(type, level);
    }
}
//TODO:
//Non-entity hit, air: spawns entities instead of an explosion
