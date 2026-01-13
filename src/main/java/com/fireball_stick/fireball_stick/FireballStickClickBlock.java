package com.fireball_stick.fireball_stick;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FireballStickClickBlock {
    private static final List<Runnable> QUEUE = new ArrayList<>();
    private static int tickCounter = 0;
    private static int taskCount = 0;
    private static final Queue<Runnable> nextTickQueue = new ArrayDeque<>();

    public static void add(Runnable task) {
        QUEUE.add(task);
    }

    //Tick queue system
    public static void tick() {
        if (QUEUE.isEmpty()) {
            return;
        }
        tickCounter++;
        if (tickCounter >= 1) {
            //Resets the tick counter
            tickCounter = 0;
            QUEUE.remove(0).run();
        }
    }

    //Hits a block
    public static InteractionResult useOn(UseOnContext context) {
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        BlockPos clickedPos = placeContext.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        int spawnHeight = 50;
        double amplitude = 15;
        int fireballAmount = 50;
        int explosionPower = 15;
        //Direction the fireballs will head towards
        double xDir = 0;
        double yDir = -2;
        double zDir = 0;

        if(player != null && level instanceof ServerLevel serverLevel) {
            double angle = Math.toRadians(player.getYRot() + 90);
            //Makes the fireballs equally spread out
            double angleStep = Math.PI / ((double) fireballAmount / 2);
            //How far away the fireballs spawn from the player
            Vec3 playerLookDir = player.getLookAngle().normalize();
            Vec3 playerEye = player.getEyePosition().add(playerLookDir.scale(10));
            //Position the fireballs will face
            double xPos = playerEye.x;
            //To make the y-position consistent
            double yPos = clickedPos.getY();
            double zPos = playerEye.z;
            for (int i = 0; i < fireballAmount; i++) {
                Vec3 dir = new Vec3(xDir, yDir, zDir);
                LargeFireball largeFireball = new LargeFireball(
                        level,
                        player,
                        dir,
                        explosionPower);

                largeFireball.setPos(
                        xPos + (Math.cos(angle) * amplitude),
                        yPos + spawnHeight,
                        zPos + (Math.sin(angle) * amplitude));

                largeFireball.setDeltaMovement(xDir, yDir, zDir);
                serverLevel.addFreshEntity(largeFireball);
                angle += angleStep;
                yDir = yDir;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
