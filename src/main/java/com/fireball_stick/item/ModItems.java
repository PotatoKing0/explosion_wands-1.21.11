package com.fireball_stick.item;

import com.fireball_stick.destructionStick.DestructionStickItem;
import com.fireball_stick.fireballStick.FireballStickClickBlock;
import com.fireball_stick.fireballStick.FireballStickItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final String MOD_ID = "fireball_stick";
//FIREBALL STICK

    public static final ResourceKey<Item> FIREBALL_STICK_KEY =
            key("fireball_stick");

    public static final Item FIREBALL_STICK =
            register(FIREBALL_STICK_KEY,
                    new FireballStickItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(FIREBALL_STICK_KEY)));
    //DESTRUCTION STICK
    public static final ResourceKey<Item> DESTRUCTION_STICK_KEY =
            key("destruction_stick");

    public static final Item DESTRUCTION_STICK =
            register(DESTRUCTION_STICK_KEY,
                    new DestructionStickItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .setId(DESTRUCTION_STICK_KEY)));

//HELPER METHODS
    //Creating the item's identity
    private static ResourceKey<Item> key(String name) {
        return ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(MOD_ID, name));
    }

    //Registering the item
    private static Item register(ResourceKey<Item> key, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    //Initializes the item
    public static void init() {}

}
