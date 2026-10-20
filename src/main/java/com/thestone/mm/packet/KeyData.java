package com.thestone.mm.packet;

import com.thestone.mm.Mm;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record KeyData(String name, int value) implements CustomPacketPayload {
    public static final Type<KeyData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Mm.MOD_ID, "key_data"));

    public static final StreamCodec<ByteBuf, KeyData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            KeyData::name,

            ByteBufCodecs.VAR_INT,
            KeyData::value,

            KeyData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
