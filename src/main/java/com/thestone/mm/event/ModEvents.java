package com.thestone.mm.event;

import com.thestone.mm.Mm;
import com.thestone.mm.command.CheckComponentCommand;
import com.thestone.mm.command.CheckSpellsCommand;
import com.thestone.mm.command.ManaCommand;
import com.thestone.mm.core.ModAttachmentTypes;
import com.thestone.mm.core.ModSpells;
import com.thestone.mm.networking.ClientPayloadHandler;
import com.thestone.mm.packet.CooldownData;
import com.thestone.mm.packet.KeyData;
import com.thestone.mm.packet.ManaData;
import com.thestone.mm.mana.ManaHandler;
import com.thestone.mm.networking.ServerPayloadHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = Mm.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {

        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.MAIN);

        registrar.playToClient(ManaData.TYPE, ManaData.STREAM_CODEC, ServerPayloadHandler::handleManaOnClient);
        registrar.playToClient(CooldownData.TYPE, CooldownData.STREAM_CODEC, ServerPayloadHandler::handleCooldown);

        registrar.playToServer(KeyData.TYPE,KeyData.STREAM_CODEC, ClientPayloadHandler::handleManaOnMain);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        new ManaCommand(event.getDispatcher());
        new CheckComponentCommand(event.getDispatcher());
        new CheckSpellsCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent // on the mod event bus
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(ModSpells.SPELL_REGISTRY);
    }

    @SubscribeEvent
    public static void setPlayersManaOnSpawn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        ManaHandler.setManaData(player, 5);
    }

    @SubscribeEvent
    public static void setPlayersManaOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        ManaHandler.setManaData(player, ManaHandler.getManaData(player) / 2);
        player.setData(ModAttachmentTypes.SPELL_COOLDOWN,player.getData(ModAttachmentTypes.SPELL_COOLDOWN));

    }

    @SubscribeEvent
    public static void setPlayersManaOnClone(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        ManaHandler.setManaData(player, event.getOriginal().getData(ModAttachmentTypes.MANA));
        player.setData(ModAttachmentTypes.SPELL_COOLDOWN, event.getOriginal().getData(ModAttachmentTypes.SPELL_COOLDOWN));
    }

    @SubscribeEvent
    public static void setPlayersManaOnDimensionChange(final PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ManaHandler.setManaData(player, ManaHandler.getManaData(player));
        player.setData(ModAttachmentTypes.SPELL_COOLDOWN, player.getData(ModAttachmentTypes.SPELL_COOLDOWN));

    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        MinecraftServer server = event.getServer();
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.getData(ModAttachmentTypes.SPELL_COOLDOWN).tick();
        }
    }


}

