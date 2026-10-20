package com.thestone.mm.spells;

import com.google.common.collect.Maps;
import net.minecraft.util.Mth;

import java.util.Iterator;
import java.util.Map;

public class SpellCooldown {

    private final Map<ISpell, SpellCooldown.CooldownInstance> cooldowns = Maps.newHashMap();
    private int tickCount;

    public SpellCooldown() {
    }

    public boolean isOnCooldown(ISpell spell) {
        return this.getCooldownPercent(spell, 0.0F) > 0.0F;
    }

    public float getCooldownPercent(ISpell spell, float partialTicks) {
        SpellCooldown.CooldownInstance spellcooldowns$cooldowninstance = this.cooldowns.get(spell);
        if (spellcooldowns$cooldowninstance != null) {
            float f = (float) (spellcooldowns$cooldowninstance.endTime - spellcooldowns$cooldowninstance.startTime);
            float f1 = (float) spellcooldowns$cooldowninstance.endTime - ((float) this.tickCount + partialTicks);
            return Mth.clamp(f1 / f, 0.0F, 1.0F);
        } else {
            return 0.0F;
        }
    }

    public void tick() {
        ++this.tickCount;
        if (!this.cooldowns.isEmpty()) {
            Iterator<Map.Entry<ISpell, SpellCooldown.CooldownInstance>> iterator = this.cooldowns.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<ISpell, SpellCooldown.CooldownInstance> entry = iterator.next();
                if (entry.getValue().endTime <= this.tickCount) {
                    iterator.remove();
                    this.onCooldownEnded(entry.getKey());
                }
            }
        }

    }

    public void addCooldown(ISpell spell, int ticks) {
        this.cooldowns.put(spell, new SpellCooldown.CooldownInstance(this.tickCount, this.tickCount + ticks));
        this.onCooldownStarted(spell, ticks);
    }

    public void removeCooldown(ISpell spell) {
        this.cooldowns.remove(spell);
        this.onCooldownEnded(spell);
    }

    protected void onCooldownStarted(ISpell spell, int ticks) {
    }

    protected void onCooldownEnded(ISpell spell) {
    }

    static class CooldownInstance {
        final int startTime;
        final int endTime;

        CooldownInstance(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
