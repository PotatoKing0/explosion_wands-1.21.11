package com.fireball_stick.sticks_click_air;

import com.fireball_stick.customFunctions.tnt.CustomTnt;
import com.fireball_stick.entity.ModEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TNTStickClickAir extends Item {
    public TNTStickClickAir(Item.Properties properties) {
        super(properties);
    }

    //Initializes the item
    public static InteractionResult use(Item item, Level level, Player player, InteractionHand hand) {
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (hitResult.getType() != HitResult.Type.BLOCK && !level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    public static PrimedTnt asPrimedTnt(Item item, Level level, Player player, InteractionHand hand) {
        int velocity = 4;
        int amount = 5;
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        double dirX = player.getX();
        double dirY = player.getY();
        double dirZ = player.getZ();

        Vec3 playerLookDir = player.getLookAngle();
        Vec3 playerStartDir = player.getEyePosition();
        Vec3 playerEndDir = playerStartDir.add(playerLookDir.scale(1));
        playerLookDir.add(dirX, dirY, dirZ).normalize();
        CustomTnt customTnt = ModEntities.CUSTOM_TNT.create(level, EntitySpawnReason.TRIGGERED);

        if(blockHitResult.getType() != HitResult.Type.BLOCK && customTnt != null) {
            Vec3 customTntInAirPosition = player.position().add(0, player.getEyeHeight() - 0.25, 0)
                    .add(playerLookDir.scale(3.0));
            customTnt.moveOrInterpolateTo(customTntInAirPosition);
            customTnt.setDeltaMovement(playerLookDir.scale(velocity));
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 0.6F, 1.0F);
            customTnt.setAfterSpawnEffects(false);
            customTnt.setDiscardOnFirstUse(true);
            customTnt.setExplodeOnContact(true);
            customTnt.setExplosionPower(4.0F);
            customTnt.setEntitySpawnAfterExplosion(true);
            customTnt.setCircle(true);
            customTnt.setAmplitude(2);
            customTnt.setXChange(2);
            customTnt.setYChange(2);
            customTnt.setZChange(2);
            customTnt.setEntityToSpawn(EntityType.CHICKEN);
            customTnt.setEntityAmount(50);
            return customTnt;
            }
            return null;
    }
}