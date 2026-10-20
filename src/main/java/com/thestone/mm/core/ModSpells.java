package com.thestone.mm.core;

import com.thestone.mm.Mm;
import com.thestone.mm.spells.ISpell;
import com.thestone.mm.spells.spell.BasicMissileSpell;
import com.thestone.mm.spells.spell.FireballSpell;
import com.thestone.mm.spells.spell.ShieldSpell;
import com.thestone.mm.spells.spell.TeleportSpell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ModSpells {

    public static final ResourceKey<Registry<ISpell>> SPELL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Mm.MOD_ID, "spells"));

    public static final Registry<ISpell> SPELL_REGISTRY = new RegistryBuilder<ISpell>(SPELL_REGISTRY_KEY)
            .sync(true)
            .maxId(256)
            .create();


    public static final DeferredRegister<ISpell> SPELLS = DeferredRegister.create(SPELL_REGISTRY_KEY, Mm.MOD_ID);

    public static final DeferredHolder<ISpell, TeleportSpell> TELEPORT_SPELL = SPELLS.register("teleport_spell", TeleportSpell::new);
    public static final DeferredHolder<ISpell, FireballSpell> FIREBALL_SPELL = SPELLS.register("fireball_spell", FireballSpell::new);
    public static final DeferredHolder<ISpell, ShieldSpell> SMALL_SHIELD_SPELL = SPELLS.register("small_shield_spell", ShieldSpell::new);
    public static final DeferredHolder<ISpell, BasicMissileSpell> BASIC_MISSILE_SPELL = SPELLS.register("basic_missile_spell", BasicMissileSpell::new);

    public static void init(IEventBus bus) {
        SPELLS.register(bus);
    }

}
