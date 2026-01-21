package com.fireball_stick.item_classes.air;

import com.fireball_stick.sticks_click_air.FireballStickShotgunClickAir;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class FireballStickShotgunAirItem extends Item {
    public FireballStickShotgunAirItem(Properties properties) {
        super(properties);
    }

    //Click on air/liquid/entity
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            Projectile projectile = FireballStickShotgunClickAir.asFireballProjectile(this, level, player, hand);
            if (projectile != null) {
                level.addFreshEntity(projectile);
            }
        }
        return FireballStickShotgunClickAir.use(this, level, player, hand);
    }
}
