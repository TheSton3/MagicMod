package com.thestone.mm.core;

import com.thestone.mm.Mm;
import com.thestone.mm.entity.MagicMissileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Mm.MOD_ID);



    public static final Supplier<EntityType<MagicMissileEntity>> MAGIC_MISSILE =
            ENTITY_TYPE.register("magic_missile" ,()-> EntityType.Builder.<MagicMissileEntity>of(MagicMissileEntity::new, MobCategory.MISC)
                    .sized(1,1)
                    .build("magic_missile")
            );

    public static void init(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }
}
