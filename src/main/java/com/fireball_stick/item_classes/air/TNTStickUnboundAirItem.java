package com.fireball_stick.item_classes.air;

import com.fireball_stick.sticks_click_air.TNTStickClickAir;
import com.fireball_stick.sticks_click_air.TNTStickUnboundClickAir;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class TNTStickUnboundAirItem extends Item {
    public TNTStickUnboundAirItem(Properties properties) {
        super(properties);
    }

    //Click on air/liquid/entity
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            PrimedTnt customTnt = TNTStickUnboundClickAir.asPrimedTnt(this, level, player, hand);
            if (customTnt != null) {
                level.addFreshEntity(customTnt);
            }
        }
        return TNTStickUnboundClickAir.use(this, level, player, hand);
    }
}
