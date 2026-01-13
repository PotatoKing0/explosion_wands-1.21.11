package com.fireball_stick.tnt_stick_unbound;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TNTStickUnboundClickBlock {

    //Hits a block
    public static InteractionResult useOn(UseOnContext context)  {
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        BlockPos clickedPos = placeContext.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (level instanceof ServerLevel serverLevel && serverLevel.getBlockState(clickedPos).canBeReplaced() && player != null && !level.isClientSide()) {
            double xDir = clickedPos.getX();
            double yDir = clickedPos.getY();
            double zDir = clickedPos.getZ();
            int tntAmount = 100;
            //Makes the start spawn angle of the TNT be equal to the direction the player is facing (default (0): east)
            final double[] angle = {Math.toRadians(player.getYRot() + 90)};
            double angleStep = Math.PI / ((double) tntAmount / 2); //How smooth the curve looks
            double amplitude = 15; //Width of the curve
            //Making sure the primed TNTs explode when all the primed TNTs in the current loop has spawned
            int tntFuseTimer = (tntAmount * 50) / 50 ; //50 ms = 1 tick
            final double[] changePosition = {0}; //Initial position of the starting TNT
            for (int i = 0; i < tntAmount; i++) {
                int finalI = i;
                    //Creates primed TNTs every iteration
                    PrimedTnt primedTnt = new PrimedTnt(level,
                            //X dir: cos, Z dir: sin, makes a circle
                            xDir + (Math.cos(angle[0]) * amplitude),
                            yDir + 3,
                            zDir + (Math.sin(angle[0]) * amplitude),
                            player);
                    primedTnt.setFuse(tntFuseTimer);
                    //Adds the primed TNT to the world
                    serverLevel.addFreshEntity(primedTnt);
                    //Changes the initial angle by the value of angleStep every iteration so the TNTs are not static
                    angle[0] += angleStep;
                    //Height of the cos curve every iteration
                    changePosition[0] += Math.PI / ((double) (tntAmount / 4) / 2);
            }
            //Plays a sound when a block is clicked
            level.playSound(null, clickedPos.getX(), clickedPos.getY(), clickedPos.getZ(), SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 0.1F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

}

//TODO: Make the primed TNT explode on contact with ground/whatever