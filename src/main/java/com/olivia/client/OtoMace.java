package com.olivia.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.slot.SlotActionType;

public class OtoMace {
    public static boolean active = false;
    public static int delayMs = 300;
    private static long lastActionTime = 0;
    private static PlayerEntity target = null;
    private static int state = 0;

    public static void tick(MinecraftClient mc) {
        if (!active || mc.player == null || mc.world == null) return;
        ClientPlayerEntity player = mc.player;

        if (target == null || target.isDead() || target.distanceTo(player) > 40) {
            target = findTarget(mc);
            state = 0;
        }
        if (target == null) return;

        long now = System.currentTimeMillis();
        int randomDelay = delayMs + (int)(Math.random() * (delayMs * 0.3));
        if (now - lastActionTime < randomDelay) return;
        lastActionTime = now;

        switch (state) {
            case 0:
                if (!player.isGliding()) {
                    player.jump();
                    mc.interactionManager.interactItem(player, Hand.MAIN_HAND);
                } else {
                    state = 1;
                }
                break;
            case 1:
                lookAt(mc, target);
                if (player.distanceTo(target) <= 6.0) {
                    state = 2;
                } else {
                    useFirework(mc, player);
                }
                break;
            case 2:
                equipArmor(mc, player);
                state = 3;
                break;
            case 3:
                if (player.distanceTo(target) <= 4.5) {
                    lookAt(mc, target);
                    equipMace(mc, player);
                    mc.interactionManager.attackEntity(player, target);
                    player.swingHand(Hand.MAIN_HAND);
                    if (!target.isDead()) state = 0;
                } else {
                    state = 1;
                }
                break;
        }
    }

    private static void lookAt(MinecraftClient mc, PlayerEntity target) {
        Vec3d targetPos = target.getPos().add(0, target.getEyeHeight(target.getPose()), 0);
        Vec3d playerPos = mc.player.getEyePos();
        double dx = targetPos.x - playerPos.x;
        double dy = targetPos.y - playerPos.y;
        double dz = targetPos.z - playerPos.z;
        double dist = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, dist));
        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
    }

    private static void useFirework(MinecraftClient mc, ClientPlayerEntity player) {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(Items.FIREWORK_ROCKET)) {
                mc.interactionManager.clickSlot(player.playerScreenHandler.syncId, i, 0, SlotActionType.SWAP, player);
                mc.interactionManager.interactItem(player, Hand.MAIN_HAND);
                break;
            }
        }
    }

    private static void equipArmor(MinecraftClient mc, ClientPlayerEntity player) {
        // 1.21.5 için envanter API'si (yer tutucu, gerekirse geliştirilecek)
    }

    private static void equipMace(MinecraftClient mc, ClientPlayerEntity player) {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(Items.MACE)) {
                mc.interactionManager.clickSlot(player.playerScreenHandler.syncId, i, 0, SlotActionType.SWAP, player);
                break;
            }
        }
    }
    
    private static PlayerEntity findTarget(MinecraftClient mc) {
        PlayerEntity closest = null;
        double bestDist = Double.MAX_VALUE;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player || p.isDead() || p.isSpectator()) continue;
            double dist = p.distanceTo(mc.player);
            if (dist < bestDist && dist <= 40) {
                bestDist = dist;
                closest = p;
            }
        }
        return closest;
    }
}
