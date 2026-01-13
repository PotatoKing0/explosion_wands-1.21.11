package com.fireball_stick.fireball_stick;

import com.fireball_stick.fireball_stick.FireballStickClickAir;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class FireballStickItem extends Item {
    public FireballStickItem(Properties properties) {
        super(properties);
    }

    //Click on block

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return FireballStickClickBlock.useOn(context);
    }

    //Click on air/liquid/entity
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            Projectile projectile = FireballStickClickAir.asFireballProjectile(this, level, player, hand);
            if (projectile != null) {
                level.addFreshEntity(projectile);
            }
        }
        return FireballStickClickAir.use(this, level, player, hand);
    }
}
