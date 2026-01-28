package com.fireball_stick.item_classes.air;

import com.fireball_stick.sticks_click_air.TNTStickClickAir;
import com.fireball_stick.sticks_click_air.TNTStickMidClickAir;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class TNTStickMidAirItem extends Item {
    public TNTStickMidAirItem(Properties properties) {
        super(properties);
    }

    //Click on air/liquid/entity
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            PrimedTnt customTnt = TNTStickMidClickAir.asPrimedTnt(this, level, player, hand);
            if (customTnt != null) {
                level.addFreshEntity(customTnt);
            }
        }
        return TNTStickMidClickAir.use(this, level, player, hand);
    }
}
