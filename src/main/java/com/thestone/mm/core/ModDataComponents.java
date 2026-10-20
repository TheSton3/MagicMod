package com.thestone.mm.core;

import com.thestone.mm.Mm;
import com.thestone.mm.data_component.MagicWandComponent;
import com.thestone.mm.data_component.WeaponLvlPoints;
import com.thestone.mm.data_component.WeaponMode;
import com.thestone.mm.data_component.WeaponOwner;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Mm.MOD_ID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MagicWandComponent>> MAGIC_WAND_COMPONENT = register("magic_wand_component",
            builder -> builder.persistent(MagicWandComponent.FULL_CODEC).networkSynchronized(MagicWandComponent.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WeaponOwner>> WEAPON_OWNER_COMPONENT = register("weapon_owner_component",
            builder -> builder.persistent(WeaponOwner.CODEC).networkSynchronized(WeaponOwner.STREAM_CODEC));


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WeaponMode>> WEAPON_MODE_COMPONENT = register("weapon_mode_component",
            builder -> builder.persistent(WeaponMode.CODEC).networkSynchronized(WeaponMode.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WeaponLvlPoints>> WEAPON_LVL_POINT = register("weapon_lvl_point",
            builder -> builder.persistent(WeaponLvlPoints.CODEC).networkSynchronized(WeaponLvlPoints.STREAM_CODEC));


    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }


    public static void init(IEventBus bus) {
        DATA_COMPONENT_TYPES.register(bus);
    }


}
