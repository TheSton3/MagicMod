package com.thestone.mm.packet;

import com.thestone.mm.Mm;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ManaData(int oldValue, int newValue) implements CustomPacketPayload {


    public static final CustomPacketPayload.Type<ManaData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Mm.MOD_ID, "mana_data"));

    public static final StreamCodec<ByteBuf, ManaData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ManaData::oldValue,
            ByteBufCodecs.INT,
            ManaData::newValue,
            ManaData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
