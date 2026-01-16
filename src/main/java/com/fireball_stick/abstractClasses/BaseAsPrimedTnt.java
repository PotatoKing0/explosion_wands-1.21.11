package com.fireball_stick.abstractClasses;

import com.fireball_stick.customFunctions.tnt.CustomTnt;
import com.fireball_stick.entity.ModEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class BaseAsPrimedTnt extends Item {
    //Currently unused, will most likely be used when implementing functionality for both tnt_stick versions
    public BaseAsPrimedTnt(Properties properties) {
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

        int reach = 1000;
        int velocity = 10;
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
                    .add(playerLookDir.scale(2.5));
            customTnt.moveOrInterpolateTo(customTntInAirPosition);
            customTnt.setDeltaMovement(playerLookDir.scale(velocity));
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 1.0F, 1.0F);
            return customTnt;
        }
        return null;
    }

    protected abstract void cast1(Item item, Level level, Player player, InteractionHand hand);

    protected abstract void cast2(Item item, Level level, Player player, InteractionHand hand);
}

//TODO:
//Remove the fire shooting the projectile causes to improve performance
//Make it be able to destroy bedrock or any other block
//Make it so that it can spawn and shoot out other types of entities (maybe a different item)
//Make it so we can explode mountable entities
