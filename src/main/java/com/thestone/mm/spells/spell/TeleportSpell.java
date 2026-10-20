package com.thestone.mm.spells.spell;

import com.thestone.mm.core.ModAttachmentTypes;
import com.thestone.mm.mana.ManaHandler;
import com.thestone.mm.spells.ISpell;
import com.thestone.mm.spells.SpellCooldown;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class TeleportSpell implements ISpell {
    @Override
    public Component spellName() {
        return Component.translatable("mm.spells.teleport");
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
        return 2;
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
                if (player.level().isClientSide()) {
                    return;
                }
                ClipContext context = getClipContext(player);


                BlockHitResult hitResult = player.level().clip(context);

                if (hitResult.getType() != HitResult.Type.MISS) {
                    BlockPos blockPos = hitResult.getBlockPos();


                    double x = blockPos.getX() + 0.5;
                    double y = blockPos.getY() + 1.0;
                    double z = blockPos.getZ() + 0.5;


                    player.teleportTo(x, y, z);


                }
            }
        }
    }

    @Override
    public int getAmplifier(Player player) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        return stack.getUseDuration(player);
    }

    private @NotNull ClipContext getClipContext(Player player) {
        double maxDistance = 0.12 + this.getAmplifier(player);


        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();


        Vec3 endVec = eyePos.add(
                lookVec.x * maxDistance,
                lookVec.y * maxDistance,
                lookVec.z * maxDistance
        );


        ClipContext context = new ClipContext(
                eyePos,
                endVec,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        );
        return context;
    }
}
