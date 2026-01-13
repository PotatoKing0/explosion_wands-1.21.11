package com.fireball_stick.abstractClasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class BaseUseOn extends Item {
    public BaseUseOn(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        if(!canUse(context)) {
            return InteractionResult.CONSUME;
        }
        placementSound(context);
        cast(context);

        return InteractionResult.SUCCESS;
    }

    protected boolean canUse(UseOnContext context) {
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        BlockPos clickedPos = placeContext.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        return (player != null && !level.isClientSide());
    }

    protected void placementSound(UseOnContext context) {
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        BlockPos clickedPos = placeContext.getClickedPos();
        double xDir = clickedPos.getX();
        double yDir = clickedPos.getY();
        double zDir = clickedPos.getZ();
        Level level = context.getLevel();
        level.playSound(null, clickedPos.getX(), clickedPos.getY(), clickedPos.getZ(), SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 0.1F, 1.0F);
    }
    protected abstract void cast(UseOnContext context);


}

//To be used for later maybe
