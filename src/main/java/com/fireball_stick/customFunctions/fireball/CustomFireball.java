package com.fireball_stick.customFunctions.fireball;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class CustomFireball extends Fireball {

    public CustomFireball(EntityType<? extends CustomFireball> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onHit(final @NonNull HitResult hitResult) {
        super.onHit(hitResult);
        if(level() instanceof ServerLevel serverLevel) {
            level().explode(
                    this,
                    getX(),
                    getY(),
                    getZ(),
                    50F,
                    Level.ExplosionInteraction.MOB
            );
            discard();
            serverLevel.sendParticles(ParticleTypes.COPPER_FIRE_FLAME, getX(), getY(), getZ(), 700, 3, 3, 3, 1);
            System.out.println("Works!");
        }
    }
}

//Currently unused. No clue how to create an entity renderer for the fireball
