package com.fireball_stick.item_classes.block;

import com.fireball_stick.sticks_click_block.FireballStickClickBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class FireballStickBlockItem extends Item {
    public FireballStickBlockItem(Properties properties) {
        super(properties);
    }

    //Click on block
    @Override
    public InteractionResult useOn(UseOnContext context) {
        return FireballStickClickBlock.useOn(context);
    }
}
