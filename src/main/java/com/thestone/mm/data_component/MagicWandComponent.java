package com.thestone.mm.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thestone.mm.core.ModSpells;
import com.thestone.mm.spells.ISpell;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record MagicWandComponent(Holder<ISpell> spell) {

    private static final Codec<Holder<ISpell>> CODEC = ModSpells.SPELL_REGISTRY.holderByNameCodec();

    public static final Codec<MagicWandComponent> FULL_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CODEC.fieldOf("spell").forGetter(MagicWandComponent::spell)

            ).apply(instance, MagicWandComponent::new)
    );
    public static final StreamCodec<ByteBuf, MagicWandComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CODEC), MagicWandComponent::spell,
            MagicWandComponent::new
    );

    public Holder<ISpell> getSpell() {
        return this.spell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.spell);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof MagicWandComponent magicWandComponent && this.spell == magicWandComponent.spell;
        }
    }


}


