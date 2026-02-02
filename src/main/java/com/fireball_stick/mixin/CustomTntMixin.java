package com.fireball_stick.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrimedTnt.class)
public abstract class CustomTntMixin {
    /*
    @Redirect(
        method = "destroyBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;destroyBlock(Lnet/net.minecraft.core.BlockPos;B;net/minecraft.world.entity.Entity;I;)B"
            )
    )
     */

    private void cancelExplosionDrop(BlockPos pos, boolean dropResources, Entity breaker, int updateLimit) {
        PrimedTnt spawnedPrimedTnt = (PrimedTnt) (Object)this;
        if(spawnedPrimedTnt.getTags().contains("customTnt")) {

        }
    }
}
