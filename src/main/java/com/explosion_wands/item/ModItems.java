package com.explosion_wands.item;

import com.explosion_wands.item_classes.air.*;
import com.explosion_wands.initialization.ModInitialization;
import com.explosion_wands.item_classes.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItems {
//AIR

    //TNT CHICKEN WAND
    public static final ResourceKey<Item> TNT_STICK_AIR_KEY =
            key("tnt_chicken_wand");

    public static final Item TNT_STICK_AIR =
            register(TNT_STICK_AIR_KEY,
                    new TNTStickAirItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_STICK_AIR_KEY)));

    //TNT INFINITE WAND
    public static final ResourceKey<Item> TNT_STICK_UNBOUND_AIR_KEY =
            key("tnt_infinite_wand");

    public static final Item TNT_STICK_UNBOUND_AIR =
            register(TNT_STICK_UNBOUND_AIR_KEY,
                    new TNTStickUnboundAirItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_STICK_UNBOUND_AIR_KEY)));

    //TNT TORNADO WAND
    public static final ResourceKey<Item> TNT_STICK_MID_AIR_KEY =
            key("tnt_tornado_wand");

    public static final Item TNT_STICK_MID_AIR =
            register(TNT_STICK_MID_AIR_KEY,
                    new TNTStickMidAirItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_STICK_MID_AIR_KEY)));

    //FIREBALL WAND
    public static final ResourceKey<Item> FIREBALL_STICK_AIR_KEY =
            key("fireball_wand");

    public static final Item FIREBALL_STICK_AIR =
            register(FIREBALL_STICK_AIR_KEY,
                    new FireballStickAirItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(FIREBALL_STICK_AIR_KEY)));

    //FIREBALL SHOTGUN WAND
    public static final ResourceKey<Item> FIREBALL_STICK_SHOTGUN_AIR_KEY =
            key("fireball_shotgun_wand");

    public static final Item FIREBALL_STICK_SHOTGUN_AIR =
            register(FIREBALL_STICK_SHOTGUN_AIR_KEY,
                    new FireballStickShotgunAirItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(FIREBALL_STICK_SHOTGUN_AIR_KEY)));

    //FIREBALL HITSCAN WAND
    public static final ResourceKey<Item> FIREBALL_STICK_HITSCAN_AIR_KEY =
            key("fireball_hitscan_wand");

    public static final Item FIREBALL_STICK_HITSCAN_AIR =
            register(FIREBALL_STICK_HITSCAN_AIR_KEY,
                    new FireballStickHitscanAirItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(FIREBALL_STICK_HITSCAN_AIR_KEY)));
//BLOCK

    //FIREBALL BARRAGE WAND
    public static final ResourceKey<Item> FIREBALL_STICK_BLOCK_KEY =
            key("fireball_barrage_wand");

    public static final Item FIREBALL_STICK_BLOCK =
            register(FIREBALL_STICK_BLOCK_KEY,
                    new FireballStickBlockItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(FIREBALL_STICK_BLOCK_KEY)));


    //TNT SLOW BARRAGE WAND
    public static final ResourceKey<Item> TNT_STICK_BLOCK_KEY =
            key("tnt_slow_barrage_wand");

    public static final Item TNT_STICK_BLOCK =
            register(TNT_STICK_BLOCK_KEY,
                    new TNTStickBlockItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_STICK_BLOCK_KEY)));

    //TNT INSTANT BARRAGE WAND
    public static final ResourceKey<Item> TNT_STICK_UNBOUND_BLOCK_KEY =
            key("tnt_instant_barrage_wand");

    public static final Item TNT_STICK_UNBOUND_BLOCK =
            register(TNT_STICK_UNBOUND_BLOCK_KEY,
                    new TNTStickUnboundBlockItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_STICK_UNBOUND_BLOCK_KEY)));

    //TNT EXPLODING BLOCKS WAND
    public static final ResourceKey<Item> TNT_STICK_FALLING_BLOCK_KEY =
            key("tnt_exploding_blocks_wand");

    public static final Item TNT_STICK_FALLING_BLOCK =
            register(TNT_STICK_FALLING_BLOCK_KEY,
                    new TNTStickFallingBlockItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_STICK_FALLING_BLOCK_KEY)));
    //TNT EXPLODING ENTITIES WAND
    public static final ResourceKey<Item> TNT_STICK_ENTITIES_BLOCK_KEY =
            key("tnt_exploding_entities_wand");

    public static final Item TNT_STICK_ENTITIES_BLOCK =
            register(TNT_STICK_ENTITIES_BLOCK_KEY,
                    new TNTStickEntitiesBlockItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_STICK_ENTITIES_BLOCK_KEY)));
    //FIREBALL SCATTER WAND
    public static final ResourceKey<Item> TNT_FIREBALL_STICK_EXPLOSION_BLOCK_KEY =
            key("fireball_scatter_wand");

    public static final Item TNT_FIREBALL_STICK_EXPLOSION_BLOCK =
            register(TNT_FIREBALL_STICK_EXPLOSION_BLOCK_KEY,
                    new TNTFireballStickExplosionBlockItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_FIREBALL_STICK_EXPLOSION_BLOCK_KEY)));

    //TNT FALLING WAND
    public static final ResourceKey<Item> TNT_FALLING_WAND_KEY =
            key("tnt_falling_wand");

    public static final Item TNT_FALLING_WAND =
            register(TNT_FALLING_WAND_KEY,
                    new TNTFallingWandItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(TNT_FALLING_WAND_KEY)));
//HELPER METHODS
    //Creating the item's identity
    private static ResourceKey<Item> key(String name) {
        return ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(ModInitialization.MOD_ID, name));
    }

    //Registering the item
    private static Item register(ResourceKey<Item> key, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    //Initializes the item
    public static void init() {}

}
