package com.thestone.mm.item;

import com.thestone.mm.core.ModDataComponents;
import com.thestone.mm.data_component.WeaponMode;
import com.thestone.mm.data_component.WeaponOwner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;


public class ModifierWeapon extends SwordItem {
    private static UUID ownerId;
    private static boolean flag = false;
    public ModifierWeapon() {
        super(Tiers.DIAMOND, new Properties().component(ModDataComponents.WEAPON_MODE_COMPONENT, WeaponMode.STANDARD));
    }

    private WeaponMode getMode(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.WEAPON_MODE_COMPONENT.get(), WeaponMode.STANDARD);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide()) {
            if (player.isCrouching()) {
                switch (getMode(stack)) {
                    case STANDARD -> stack.set(ModDataComponents.WEAPON_MODE_COMPONENT, WeaponMode.POWER);
                    case POWER -> stack.set(ModDataComponents.WEAPON_MODE_COMPONENT, WeaponMode.EXECUTE);
                    case EXECUTE -> stack.set(ModDataComponents.WEAPON_MODE_COMPONENT, WeaponMode.GUARD);
                    case GUARD -> stack.set(ModDataComponents.WEAPON_MODE_COMPONENT, WeaponMode.STANDARD);

                }
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(stack);

    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {

        if (entity instanceof Player player && !flag) {
            ownerId = player.getUUID();
            flag = true;
            stack.set(ModDataComponents.WEAPON_OWNER_COMPONENT,new WeaponOwner(ownerId));
        }
        System.out.println(ownerId);
        return super.onEntitySwing(stack, entity, hand);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        double percentToKill = (target.getHealth() / target.getMaxHealth()) * 100;
        if (!attacker.level().isClientSide()) {

            if (stack.get(ModDataComponents.WEAPON_MODE_COMPONENT.get()) == WeaponMode.EXECUTE) {
                if (30 >= percentToKill) {
                    target.kill();
                }
            }
        }
        System.out.println(percentToKill);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide()) {
            if (stack.get(ModDataComponents.WEAPON_MODE_COMPONENT.get()) == WeaponMode.GUARD) {
                if (entity instanceof Player player) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE));
                }
            }
            if (stack.get(ModDataComponents.WEAPON_MODE_COMPONENT.get()) == WeaponMode.POWER) {
                if (entity instanceof Player player) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.mm.weapon_mode.mode " + getMode(stack)));
    }
}
