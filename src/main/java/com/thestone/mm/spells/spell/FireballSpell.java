package com.thestone.mm.spells.spell;

import com.thestone.mm.core.ModAttachmentTypes;
import com.thestone.mm.mana.ManaHandler;
import com.thestone.mm.spells.ISpell;
import com.thestone.mm.spells.SpellCooldown;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireballSpell implements ISpell {
    @Override
    public Component spellName() {
        return Component.translatable("mm.spells.fireball_spell");
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
        return 20;
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
               shootSmallFireball(player,player.level());
               setCooldown(player);

            }
        }
    }

    @Override
    public int getAmplifier(Player player) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        return stack.getUseDuration(player);
    }

    private static void shootSmallFireball(Player player, Level level) {

        Vec3 motion = player.getLookAngle().scale(1.5);


        SmallFireball smallfireball = new SmallFireball(level, player, motion);


        Vec3 spawnPos = player.getEyePosition().add(player.getLookAngle().scale(1.5));
        smallfireball.setPos(spawnPos);


        level.addFreshEntity(smallfireball);


        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);

    }
}
