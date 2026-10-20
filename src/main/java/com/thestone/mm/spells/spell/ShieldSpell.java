package com.thestone.mm.spells.spell;

import com.thestone.mm.core.ModAttachmentTypes;
import com.thestone.mm.mana.ManaHandler;
import com.thestone.mm.spells.ISpell;
import com.thestone.mm.spells.SpellCooldown;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ShieldSpell implements ISpell {
    @Override
    public Component spellName() {
        return Component.translatable("mm.spells.shield");
    }

    @Override
    public void setCooldown(Player player) {
        player.getData(ModAttachmentTypes.SPELL_COOLDOWN.get()).addCooldown(this, getCooldown());
    }

    @Override
    public boolean isOnCooldown(Player player) {
        return player.getData(ModAttachmentTypes.SPELL_COOLDOWN.get()).isOnCooldown(this);
    }

    @Override
    public int getCooldown() {
        return 100;
    }

    @Override
    public int manaCost() {
        return 1;
    }

    @Override
    public boolean canUse(Player player) {
        int mana = ManaHandler.getManaData(player);

        SpellCooldown data = player.getData(ModAttachmentTypes.SPELL_COOLDOWN.get());
        return mana > this.manaCost() && !player.level().isClientSide() && !data.isOnCooldown(this);
    }

    @Override
    public void use(Player player) {
        if (!player.level().isClientSide()) {
            if (canUse(player)) {
                setCooldown(player);
                System.out.println(getAmplifier(player));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, getAmplifier(player)));
            }
        }
    }

    @Override
    public int getAmplifier(Player player) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        return stack.getUseDuration(player) / 10000;
    }
}
