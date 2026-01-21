package com.fireball_stick.item_classes.block;

import com.fireball_stick.sticks_click_block.TNTStickUnboundClickBlock;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TNTStickUnboundBlockItem extends Item {
    public TNTStickUnboundBlockItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return TNTStickUnboundClickBlock.use(this, level, player, hand);
    }
}
