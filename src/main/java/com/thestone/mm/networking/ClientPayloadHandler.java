package com.thestone.mm.networking;

import com.thestone.mm.Mm;
import com.thestone.mm.core.ModSpells;
import com.thestone.mm.packet.KeyData;
import com.thestone.mm.spells.ISpell;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    // Here I am ON THE SERVER

    public static void handleManaOnMain(KeyData keyData, IPayloadContext context) {
        Player player = context.player();
        ISpell spell = ModSpells.TELEPORT_SPELL.get();
        if (spell != null && player.isAlive() && !spell.isOnCooldown(player)) {
            spell.use(context.player());
        }
        if(spell != null && spell.isOnCooldown(player)){
            Mm.LOGGER.debug("Cooldown spell is on cooldown");
        }
    }


}
