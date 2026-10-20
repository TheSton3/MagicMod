package com.thestone.mm.item;

import com.thestone.mm.core.ModDataComponents;
import com.thestone.mm.core.ModSpells;
import com.thestone.mm.data_component.MagicWandComponent;
import com.thestone.mm.spells.ISpell;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MagicWandItem extends Item {
    public MagicWandItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        switchSpell(player, usedHand);

        player.startUsingItem(usedHand);
        return super.use(level, player, usedHand);


    }

    private static void switchSpell(Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        Holder<ISpell> nowspell = stack.get(ModDataComponents.MAGIC_WAND_COMPONENT.get()).getSpell();
        List<Holder.Reference<ISpell>> holders = ModSpells.SPELL_REGISTRY.holders().toList();
        int foundIndex = 0;
        if (player.isCrouching()) {
            if(!player.level().isClientSide()) {
                for (Holder.Reference<ISpell> holder : holders) {
                    boolean flag = holder.is(nowspell);
                    foundIndex ++;
                    if(flag) {
                        stack.set(ModDataComponents.MAGIC_WAND_COMPONENT, new MagicWandComponent(holders.get(foundIndex)));
                        break;
                    }
                    if(holders.get(foundIndex).is(holders.getLast())) {
                        foundIndex =0;
                        stack.set(ModDataComponents.MAGIC_WAND_COMPONENT, new MagicWandComponent(holders.get(foundIndex)));
                        break;
                    }

                }
            }

        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
        Holder<ISpell> nowspell = stack.get(ModDataComponents.MAGIC_WAND_COMPONENT.get()).getSpell();
        nowspell.value().use((Player) livingEntity);

    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Holder<ISpell> nowspell = stack.get(ModDataComponents.MAGIC_WAND_COMPONENT.get()).getSpell();
         tooltipComponents.add(Component.translatable("item.mm.spell").append(CommonComponents.SPACE).append(nowspell.value().spellName()));
    }


}
