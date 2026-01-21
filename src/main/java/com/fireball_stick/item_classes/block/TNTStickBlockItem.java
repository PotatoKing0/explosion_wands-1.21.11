package com.fireball_stick.item_classes.block;

import com.fireball_stick.sticks_click_block.TNTStickClickBlock;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TNTStickBlockItem extends Item {
    public TNTStickBlockItem(Properties properties) {
        super(properties);
    }

    //Click on block
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return TNTStickClickBlock.use(this, level, player, hand);
    }

    //Use animation of item
    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return TNTStickClickBlock.useAnimation(this, itemStack);
    }

    //How fast we can use the item
    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return TNTStickClickBlock.useDuration(this, itemStack, user);
    }
}
