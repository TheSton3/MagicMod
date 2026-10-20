package com.thestone.mm.core;

import com.mojang.serialization.Codec;
import com.thestone.mm.Mm;
import com.thestone.mm.spells.SpellCooldown;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Mm.MOD_ID);

    // Serialization via codec
    public static final Supplier<AttachmentType<Integer>> MANA = ATTACHMENT_TYPES.register(
            "mana", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<SpellCooldown>> SPELL_COOLDOWN = ATTACHMENT_TYPES.register(
            "spell_cooldown", ()-> AttachmentType.builder(SpellCooldown::new).build()
    );


    public static void init(IEventBus bus){
        ATTACHMENT_TYPES.register(bus);
    }
}
