package com.thestone.mm.networking;

import com.thestone.mm.core.ModAttachmentTypes;
import com.thestone.mm.packet.CooldownData;
import com.thestone.mm.packet.ManaData;
import com.thestone.mm.spells.ServerSpellCooldowns;
import com.thestone.mm.spells.SpellCooldown;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    // HERE WE ARE ON THE CLIENT
    public static void handleManaOnClient(ManaData manaData, IPayloadContext context){
        context.player().setData(ModAttachmentTypes.MANA, manaData.newValue());

    }
    public static void handleCooldown(CooldownData cooldownData, IPayloadContext context) {
    context.player().getData(ModAttachmentTypes.SPELL_COOLDOWN).addCooldown(cooldownData.spell(), cooldownData.duration());

    }
}
