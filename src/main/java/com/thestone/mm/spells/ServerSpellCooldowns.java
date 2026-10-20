
package com.thestone.mm.spells;

import com.thestone.mm.packet.CooldownData;
import net.minecraft.server.level.ServerPlayer;

public class ServerSpellCooldowns extends SpellCooldown{
    private final ServerPlayer player;

    public ServerSpellCooldowns(ServerPlayer player) {
        this.player = player;
    }

    @Override
    protected void onCooldownStarted(ISpell spell, int ticks) {
        super.onCooldownStarted(spell, ticks);
        this.player.connection.send(new CooldownData(spell, ticks));
    }

    @Override
    protected void onCooldownEnded(ISpell spell) {
        super.onCooldownEnded(spell);
        this.player.connection.send(new CooldownData(spell,0));
    }
}

