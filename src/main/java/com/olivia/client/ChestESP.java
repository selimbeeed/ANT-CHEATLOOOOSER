package com.olivia.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.List;

public class ChestESP {
    public static boolean active = false;
    public static final List<BlockPos> chests = new ArrayList<>();

    public static void tick(MinecraftClient mc) {
        if (!active || mc.world == null || mc.player == null) {
            chests.clear();
            return;
        }

        chests.clear();
        int pX = mc.player.getBlockX();
        int pY = mc.player.getBlockY();
        int pZ = mc.player.getBlockZ();
        int range = 32; // 32 blocks radius

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = new BlockPos(pX + x, pY + y, pZ + z);
                    Block block = mc.world.getBlockState(pos).getBlock();

                    if (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST || 
                        block == Blocks.ENDER_CHEST || block == Blocks.SHULKER_BOX || 
                        block == Blocks.HOPPER || block == Blocks.FURNACE || 
                        block == Blocks.BLAST_FURNACE) {
                        chests.add(pos);
                    }
                }
            }
        }
    }
}
