package com.thestone.mm.packet;

import com.thestone.mm.Mm;
import com.thestone.mm.core.ModSpells;
import com.thestone.mm.spells.ISpell;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CooldownData(ISpell spell, int duration) implements CustomPacketPayload {


    public static final Type<CooldownData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Mm.MOD_ID, "cooldown_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CooldownData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(ModSpells.SPELL_REGISTRY_KEY),
            CooldownData::spell,
            ByteBufCodecs.VAR_INT,
            CooldownData::duration,
            CooldownData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
