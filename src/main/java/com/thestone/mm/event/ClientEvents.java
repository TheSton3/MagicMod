package com.thestone.mm.event;

import com.thestone.mm.Mm;
import com.thestone.mm.core.ModKeymappings;
import com.thestone.mm.packet.KeyData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Mm.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerKeybind(RegisterKeyMappingsEvent event) {
        event.register(ModKeymappings.PRESS_Z.get());
    }


    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (ModKeymappings.PRESS_Z.get().consumeClick()) {
            // Do things --> ON CLIENT

            PacketDistributor.sendToServer(new KeyData("Spell", 100));

        }
    }
}
