package com.thestone.mm.spells;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ISpell {
    Component spellName();

    void setCooldown(Player player);

    boolean isOnCooldown(Player player);

    int getCooldown();

    int manaCost();

    boolean canUse(Player player);


    void use(Player player);

    int getAmplifier(Player player);
}
