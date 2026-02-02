package com.fireball_stick.mixin;

import com.fireball_stick.sticks_click_air.FireballStickClickAir;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LargeFireball.class)
public abstract class LargeFireballMixin {
    @Redirect(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
            )
    )

    private void cancelExplosionFire(Level instance, Entity source, double x, double y, double z, float r, boolean fire, Level.ExplosionInteraction blockInteraction) {
            LargeFireball spawnedFireball = (LargeFireball)(Object)this;
            boolean shouldSpawnFire = !spawnedFireball.getTags().contains("fireball");
                instance.explode(source, x, y, z, r, shouldSpawnFire, blockInteraction);
    }
}
