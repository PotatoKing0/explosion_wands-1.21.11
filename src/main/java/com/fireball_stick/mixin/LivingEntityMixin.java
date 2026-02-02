package com.fireball_stick.mixin;

import com.fireball_stick.customFunctions.tnt.CustomTnt;
import com.fireball_stick.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(
            method = "dropAllDeathLoot",
            at = @At("HEAD"),
            cancellable = true
    )
    //Makes entities which include this tag not drop items upon death
    private void cancelDrops(ServerLevel level, DamageSource source, CallbackInfo ci) {
        LivingEntity spawnedFromExplosion = (LivingEntity)(Object)this;
            if(spawnedFromExplosion.getTags().contains("no_drops")) {
                ci.cancel();
            }
    }
}
