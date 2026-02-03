package com.fireball_stick.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class CustomTntMixin {
/*
    @Redirect(
        method = "destroyBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Explosion;destroyBlock(" +
                            "Lnet/minecraft/core/BlockPos;" +
                            "Z" +
                            "Lnet/minecraft/world/entity/Entity;" +
                            "I" +
                            ")Z"
            )
    )

    public boolean cancelDestroyBlockDrops(Level level, BlockPos pos, boolean dropResources, Entity breaker, int updateLimit) {
        if(breaker instanceof PrimedTnt) {
            return level.destroyBlock(pos, false, breaker, updateLimit);
        }
        return level.destroyBlock(pos, dropResources, breaker, updateLimit);
    }
 */
}
