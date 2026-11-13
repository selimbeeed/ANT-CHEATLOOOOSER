package com.olivia.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class BaseFinder {
    public static boolean active = false;
    public static final Set<Block> FILTERED_BLOCKS = new HashSet<>(List.of(
        Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.NETHERITE_BLOCK, 
        Blocks.SPAWNER, Blocks.ENDER_CHEST, Blocks.ENCHANTING_TABLE
    ));
    public static List<BlockPos> foundBases = new ArrayList<>();

    public static void tick(MinecraftClient mc) {
        if (!active || mc.world == null || mc.player == null) return;

        int pX = mc.player.getBlockX() >> 4;
        int pZ = mc.player.getBlockZ() >> 4;
        
        for (int cx = pX - 4; cx <= pX + 4; cx++) {
            for (int cz = pZ - 4; cz <= pZ + 4; cz++) {
                scanChunk(mc, cx, cz);
            }
        }
    }

    private static void scanChunk(MinecraftClient mc, int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = -64; y < 320; y++) {
                    BlockPos pos = new BlockPos((chunkX << 4) + x, y, (chunkZ << 4) + z);
                    Block block = mc.world.getBlockState(pos).getBlock();

                    if (FILTERED_BLOCKS.contains(block)) {
                        if (!isNearby(pos, 10)) {
                            foundBases.add(pos);
                        }
                    }
                }
            }
        }
    }

    private static boolean isNearby(BlockPos pos, int radius) {
        for (BlockPos base : foundBases) {
            if (base.isWithinDistance(pos, radius)) return true;
        }
        return false;
    }
}
