package com.thestone.mm.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record WeaponLvlPoints(int points, int level) {

    public static final Codec<WeaponLvlPoints> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.INT.fieldOf("points").forGetter(WeaponLvlPoints::points),
                    Codec.INT.fieldOf("level").forGetter(WeaponLvlPoints::level))
            .apply(instance, WeaponLvlPoints::new));


    public static final StreamCodec<ByteBuf, WeaponLvlPoints> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            WeaponLvlPoints::points,
            ByteBufCodecs.INT,
            WeaponLvlPoints::level,
            WeaponLvlPoints::new
    );

    @Override
    public int hashCode() {
        return Objects.hash(this.points, this.level);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof WeaponLvlPoints weaponLvlPoints && this.points == weaponLvlPoints.points && this.level == weaponLvlPoints.level();
        }
    }


}
