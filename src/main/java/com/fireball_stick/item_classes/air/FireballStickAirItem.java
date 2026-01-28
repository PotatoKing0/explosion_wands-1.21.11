package com.fireball_stick.item_classes.air;

import com.fireball_stick.sticks_click_air.FireballStickClickAir;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class FireballStickAirItem extends Item {
    public FireballStickAirItem(Properties properties) {
        super(properties);
    }

    //Click on air/liquid/entity
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            Projectile projectile = FireballStickClickAir.asFireballProjectile(this, level, player, hand);
            level.addFreshEntity(projectile);
        }
        return FireballStickClickAir.use(this, level, player, hand);
    }
    //Supposed to be overriding actions like opening the villager's trading GUI and riding horses
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if(player.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if(target instanceof Villager) {
            Projectile projectile = FireballStickClickAir.asFireballProjectile(this, player.level(), player, hand);
            if(projectile != null) {
                player.level().addFreshEntity(projectile);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
