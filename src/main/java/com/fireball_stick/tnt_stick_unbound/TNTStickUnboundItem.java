package com.fireball_stick.tnt_stick_unbound;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class TNTStickUnboundItem extends Item {
    public TNTStickUnboundItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return TNTStickUnboundClickBlock.useOn(context);
    }
}
