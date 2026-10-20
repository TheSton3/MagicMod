package com.thestone.mm;

import com.mojang.logging.LogUtils;
import com.thestone.mm.client.renderer.MagicMissileRenderer;
import com.thestone.mm.core.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;


@Mod(Mm.MOD_ID)
public class Mm {
    public static final String MOD_ID = "mm";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Mm(IEventBus modEventBus, ModContainer modContainer) {
        ModAttachmentTypes.init(modEventBus);
        ModSpells.init(modEventBus);
        ModItems.init(modEventBus);
        ModDataComponents.init(modEventBus);
        ModEntities.init(modEventBus);
        ModKeymappings.init();


        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }


    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.MAGIC_MISSILE.get(), MagicMissileRenderer::new);
        }
    }
}
