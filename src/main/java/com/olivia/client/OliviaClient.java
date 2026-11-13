package com.olivia.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.MinecraftClient;

public class OliviaClient implements ClientModInitializer {
    public static boolean bypassActive = true;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Open menu with 'L' key
            if (client.currentScreen == null && isKeyPressed(GLFW.GLFW_KEY_L)) {
                client.setScreen(new OliviaMenu());
            }
            
            // Module ticks
            ElytraAimBot.tick(client);
            OtoMace.tick(client);
            BaseFinder.tick(client);
            XRay.tick(client); // Added XRay here
        });
    }

    private boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), key) == GLFW.GLFW_PRESS;
    }
}
