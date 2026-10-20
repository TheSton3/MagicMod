package com.thestone.mm.mana;

import com.thestone.mm.core.ModAttachmentTypes;
import com.thestone.mm.packet.ManaData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class ManaHandler {
    public static int getManaData(Player player) {
        return player.getData(ModAttachmentTypes.MANA.get());
    }
    public static void setManaData(Player player, int data) {
        player.setData(ModAttachmentTypes.MANA, data);
        PacketDistributor.sendToPlayer(((ServerPlayer) player), new ManaData(0, data));
    }

    public static void addManaData(Player player, int mana) {
        int newValue = player.getData(ModAttachmentTypes.MANA) + mana;
        setManaData(player, newValue);
    }
    public static void removeManaData(Player player, int mana) {
        int newValue = player.getData(ModAttachmentTypes.MANA) - mana;
        setManaData(player, newValue);

    }

}
