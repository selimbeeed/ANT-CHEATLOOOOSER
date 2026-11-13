package com.olivia.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class XRay {
    public static boolean active = false;
    
    // Only these blocks will be visible in the air
    public static final Set<Block> VISIBLE_BLOCKS = new HashSet<>(List.of(
        Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
        Blocks.NETHERITE_BLOCK, Blocks.ANCIENT_DEBRIS,
        Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
        Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
        Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, 
        Blocks.SHULKER_BOX, Blocks.SPAWNER, Blocks.WATER, Blocks.LAVA
    ));

    public static void tick(MinecraftClient mc) {
        // Trigger for world render reload when toggled
        if (active && mc.world != null && mc.player != null) {
            if (mc.world.getTime() % 20 == 0) { // Check every 1 second
                // We don't reload every second to protect performance.
                // Reloading is handled in OliviaMenu when the button is clicked.
            }
        }
    }
}
