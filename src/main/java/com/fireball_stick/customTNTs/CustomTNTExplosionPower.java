package com.fireball_stick.customTNTs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class CustomTNTExplosionPower extends PrimedTnt {
    public CustomTNTExplosionPower(EntityType<? extends PrimedTnt> type, Level level) {
        super(type, level);
    }

    float explosionPower = 10F;
    @Override
    public void tick() {
        setFuse(getFuse() - 1);
        if(getFuse() <= 0) {
            discard();

        }
        level().explode(
                this,
                getX(),
                getY(),
                getZ(),
                explosionPower,
                Level.ExplosionInteraction.TNT
        );
    }
}
