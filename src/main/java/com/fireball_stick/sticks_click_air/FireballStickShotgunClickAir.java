package com.fireball_stick.sticks_click_air;

import com.fireball_stick.customFunctions.tnt.CustomTnt;
import com.fireball_stick.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FireballStickShotgunClickAir extends Item {
    public FireballStickShotgunClickAir(Properties properties) {
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

    public static Projectile asFireballProjectile(Item item, Level level, Player player, InteractionHand hand) {
        if(level instanceof ServerLevel server) {
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        //Max distance we can click on an entity
        int reach = 1000;
        double incremented = 2;
        double changePos = 0;
        double fireballAmount = 100;
        //Clicks on air/liquid
        int explosionPowerAir = 10;
        //fireball's velocity
        double velocity = 3;
        double dirX = player.getX();
        double dirY = player.getY();
        double dirZ = player.getZ();
        //Vec3 dir = new Vec3(dirX, dirY, dirZ);
        Vec3 playerStartDirForward = player.getLookAngle();
        Vec3 playerStartDir = player.getEyePosition();
        Vec3 playerEndDir = playerStartDir.add(playerStartDirForward.scale(reach));
        playerStartDirForward.add(dirX, dirY, dirZ).normalize();
        Vec3 playerStartDirRight = playerStartDirForward.cross(new Vec3(0,1,0)).normalize();
        Vec3 playerStartDirLeft = playerStartDirRight.scale(-1);
        //playerLookDir.add(0, 1000, 0).normalize();
        for(int i = 0; i < fireballAmount; i++) {
            LargeFireball fireballAir = new LargeFireball(level, player, playerStartDirForward, explosionPowerAir);
            //Fireball's initial spawn position
            if(blockHitResult.getType() != HitResult.Type.BLOCK) {
                Vec3 fireballInAirPosition = playerStartDir.add(playerStartDirForward.scale(2.5)) //in front
                        //Ensures that the fireballs are evenly distributed in front of the player
                        .add(playerStartDirRight.scale((incremented * (fireballAmount / 2)) - (changePos + incremented / 2))) //left/right
                        .add(0, - 0.25, 0); //up/down
                //Sets the fireball's position
                fireballAir.moveOrInterpolateTo(fireballInAirPosition);
            } else {
                Vec3 fireballInAirPosition = blockHitResult.getLocation() //in front
                        //Ensures that the fireballs are evenly distributed in front of the player
                        .add(playerStartDirRight.scale((incremented * (fireballAmount / 2)) - (changePos + incremented / 2))) //left/right
                        .add(0, + 0.25, 0); //up/down
                //Sets the fireball's position
                fireballAir.moveOrInterpolateTo(fireballInAirPosition);
            }
            //Set's the fireball's velocity
            fireballAir.setDeltaMovement(playerStartDirForward.scale(velocity));
            //Ensures the sound is not played for every single fireball that spawns

            //Spawns the fireball
            server.addFreshEntity(fireballAir);
            changePos += incremented;
        }
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8F, 1.0F);
        }
        return null;
    }
 }

 //TODO: When we look directly up/down, the fireballs' positions are the exact same. Ensure they are
//spread out the same way as when we look any other direction
