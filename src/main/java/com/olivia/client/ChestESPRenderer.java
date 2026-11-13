package com.olivia.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class ChestESPRenderer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            if (!ChestESP.active || ChestESP.chests.isEmpty()) return;

            MinecraftClient mc = MinecraftClient.getInstance();
            Vec3d cameraPos = context.camera().getPos();
            MatrixStack matrices = context.matrixStack();
            VertexConsumerProvider.Immediate consumers = mc.getBufferBuilders().getEntityVertexConsumers();
            VertexConsumer buffer = consumers.getBuffer(RenderLayer.getLines());

            matrices.push();
            matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
            Matrix4f matrix = matrices.peek().getPositionMatrix();

            for (BlockPos pos : ChestESP.chests) {
                float r = 0.8f, g = 0.2f, b = 1.0f, a = 1.0f; // Purple color

                drawBox(matrix, buffer, pos, r, g, b, a);

                Vec3d playerPos = mc.player.getPos();
                Vec3d blockCenter = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                Vec3d eyePos = playerPos.add(0, mc.player.getStandingEyeHeight(), 0);
                
                buffer.vertex(matrix, (float)eyePos.x, (float)eyePos.y, (float)eyePos.z).color(r, g, b, a);
                buffer.vertex(matrix, (float)blockCenter.x, (float)blockCenter.y, (float)blockCenter.z).color(r, g, b, a);
            }

            matrices.pop();
            consumers.draw(RenderLayer.getLines());
        });
    }

    private void drawBox(Matrix4f matrix, VertexConsumer buffer, BlockPos pos, float r, float g, float b, float a) {
        float x1 = pos.getX(), y1 = pos.getY(), z1 = pos.getZ();
        float x2 = x1 + 1, y2 = y1 + 1, z2 = z1 + 1;

        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a); buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
        buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a); buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
        buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a); buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
        buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a); buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);

        buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a); buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
        buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a); buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
        buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a); buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
        buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a); buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);

        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a); buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);
        buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a); buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
        buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a); buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
        buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a); buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
    }
                                                }
