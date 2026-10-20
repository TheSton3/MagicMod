package com.thestone.mm.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.jarjar.nio.util.Lazy;
import net.minecraft.client.KeyMapping;

import org.lwjgl.glfw.GLFW;

public class ModKeymappings {
    private static final KeyMapping KEY_MAPPING_Z = new KeyMapping("key.mm.z",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "key.categories.mod");

    public static final Lazy<KeyMapping> PRESS_Z = Lazy.of(() -> KEY_MAPPING_Z);


    public static void init() {

    }

}
