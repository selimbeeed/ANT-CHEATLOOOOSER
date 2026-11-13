package com.olivia.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.Items;

public class ElytraAimBot {
    public static boolean active = false;
    private static long lastActionTime = 0;
    private static PlayerEntity target = null;

    public static void tick(MinecraftClient mc) {
        if (!active || mc.player == null || mc.world == null) return;
        PlayerEntity player = mc.player;
        if (!player.isGliding()) return;

        if (target == null || target.isDead() || target.distanceTo(player) > 50) {
            target = findTarget(mc);
        }

        if (target != null) {
            long now = System.currentTimeMillis();
            if (now - lastActionTime > 150 + Math.random() * 150) {
                lookAt(mc, target);

                if (player.getMainHandStack().isOf(Items.FIREWORK_ROCKET) || player.getOffHandStack().isOf(Items.FIREWORK_ROCKET)) {
                    mc.interactionManager.interactItem(player, Hand.MAIN_HAND);
                }

                if (player.distanceTo(target) <= 4.0) {
                    mc.interactionManager.attackEntity(player, target);
                    player.swingHand(Hand.MAIN_HAND);
                }
                lastActionTime = now;
            }
        }
    }

    private static PlayerEntity findTarget(MinecraftClient mc) {
        PlayerEntity closest = null;
        double bestDist = Double.MAX_VALUE;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player || p.isDead() || p.isSpectator()) continue;
            double dist = p.distanceTo(mc.player);
            if (dist < bestDist && dist <= 50) {
                bestDist = dist;
                closest = p;
            }
        }
        return closest;
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
}
