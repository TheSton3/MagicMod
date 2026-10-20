package com.thestone.mm.core;

import com.thestone.mm.Mm;
import com.thestone.mm.data_component.MagicWandComponent;
import com.thestone.mm.item.MagicWandItem;
import com.thestone.mm.item.ModifierWeapon;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Mm.MOD_ID);

    public static final DeferredItem<Item> MAGIC_WAND = ITEMS.registerItem("magic_wand",
            properties -> new MagicWandItem(new Item.Properties().stacksTo(1).durability(100).component(ModDataComponents.MAGIC_WAND_COMPONENT, new MagicWandComponent(ModSpells.TELEPORT_SPELL.getDelegate()))));

    public static final DeferredItem<Item> TEST_SWORD = ITEMS.registerItem("test_sword",
            properties -> new ModifierWeapon());

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
