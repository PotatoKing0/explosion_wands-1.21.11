package com.fireball_stick.initialization;

import com.fireball_stick.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

import static com.fireball_stick.fireballStick.FireballStickClickBlock.tick;

public class ModInitialization implements ModInitializer {
    public static final String MOD_ID = "fireball_stick";

    public void onInitialize() {
        ModItems.init();

        //FIREBALL STICK
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(ModItems.FIREBALL_STICK));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register(entries -> entries.accept(ModItems.FIREBALL_STICK));

        //DESTRUCTION STICK
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(ModItems.DESTRUCTION_STICK));

        //Makes the tick-based placement of TNT work properly
        ServerTickEvents.END_SERVER_TICK.register(server -> tick());
    }
}
