package com.fireball_stick.item_classes.block;

import com.fireball_stick.sticks_click_block.TNTStickUnboundClickBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class TNTStickUnboundBlockItem extends Item {
    public TNTStickUnboundBlockItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return TNTStickUnboundClickBlock.useOn(context);
    }
}
