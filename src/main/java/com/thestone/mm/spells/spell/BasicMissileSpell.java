package com.thestone.mm.spells.spell;

import com.thestone.mm.Config;
import com.thestone.mm.core.ModAttachmentTypes;
import com.thestone.mm.entity.MagicMissileEntity;
import com.thestone.mm.mana.ManaHandler;
import com.thestone.mm.spells.ISpell;
import com.thestone.mm.spells.SpellCooldown;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BasicMissileSpell implements ISpell {
    @Override
    public Component spellName() {
        return Component.translatable("mm.spells.basic_missile");
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
        System.out.println(data.isOnCooldown(this));
        return mana >= this.manaCost() && !player.level().isClientSide() && !data.isOnCooldown(this);
    }

    @Override
    public void use(Player player) {
        if (!player.level().isClientSide()) {
            if (canUse(player)) {
             //   System.out.println(canUse(player));
                shootMagicMissile(player, player.level());
                setCooldown(player);
            }
        }
    }

    @Override
    public int getAmplifier(Player player) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        return stack.getUseDuration(player);
    }


    private void shootMagicMissile(Player player, Level level) {
        MagicMissileEntity magicMissileEntity = new MagicMissileEntity(level, player, ItemStack.EMPTY);
        magicMissileEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);

        level.addFreshEntity(magicMissileEntity);


        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
