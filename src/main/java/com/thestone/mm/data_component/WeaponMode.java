package com.thestone.mm.data_component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;

import java.util.function.IntFunction;

public enum WeaponMode implements StringRepresentable, IExtensibleEnum {
    STANDARD(0, "standard", ChatFormatting.DARK_GRAY),
    POWER(1, "power", ChatFormatting.RED),
    EXECUTE(2, "execute", ChatFormatting.DARK_RED),
    GUARD(3, "guard", ChatFormatting.GRAY);
    private final int id;
    private final String name;
    private final ChatFormatting color;
    public static final Codec<WeaponMode> CODEC = StringRepresentable.fromValues(WeaponMode::values);
    public static final IntFunction<WeaponMode> BY_ID = ByIdMap.continuous(mode -> mode.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, WeaponMode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, mode -> mode.id);

    WeaponMode(final int id, final String name, final ChatFormatting color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }


    @Override
    public String getSerializedName() {
        return this.name;
    }
}
