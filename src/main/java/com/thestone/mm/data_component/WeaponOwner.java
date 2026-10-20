package com.thestone.mm.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;
import java.util.UUID;

public record WeaponOwner(UUID ownerId) {


    public static final Codec<WeaponOwner> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    UUIDUtil.CODEC.fieldOf("ownerId").forGetter(WeaponOwner::ownerId)
            ).apply(instance, WeaponOwner::new)
    );
    public static final StreamCodec<ByteBuf, WeaponOwner> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, WeaponOwner::ownerId,
            WeaponOwner::new
    );

    @Override
    public int hashCode() {
        return Objects.hash(this.ownerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof WeaponOwner weaponOwner && this.ownerId == weaponOwner.ownerId;
        }
    }
}
