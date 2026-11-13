package com.olivia.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class OliviaMenu extends Screen {
    private float rotation = 0.0f;

    public OliviaMenu() {
        super(Text.literal("OLIVIA'S PRIVATE CLIENT"));
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        // 1. Grim Bypass
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Grim Bypass: " + (OliviaClient.bypassActive ? "ACIK" : "KAPALI")), btn -> {
            OliviaClient.bypassActive = !OliviaClient.bypassActive;
            btn.setMessage(Text.literal("Grim Bypass: " + (OliviaClient.bypassActive ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy - 100, 200, 20).build());

        // 2. Elytra Aimbot
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Elytra Target: " + (ElytraAimBot.active ? "ACIK" : "KAPALI")), btn -> {
            ElytraAimBot.active = !ElytraAimBot.active;
            btn.setMessage(Text.literal("Elytra Target: " + (ElytraAimBot.active ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy - 70, 200, 20).build());

        // 3. Oto Mace
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Oto Mace: " + (OtoMace.active ? "ACIK" : "KAPALI")), btn -> {
            OtoMace.active = !OtoMace.active;
            btn.setMessage(Text.literal("Oto Mace: " + (OtoMace.active ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy - 40, 200, 20).build());

        // 4. Delay Slider
        this.addDrawableChild(new SliderWidget(cx - 100, cy - 10, 200, 20, Text.empty(), (OtoMace.delayMs - 100) / 900.0) {
            @Override
            protected void updateMessage() {
                int currentDelay = (int) (100 + (this.value * 900));
                this.setMessage(Text.literal("Vurus Gecikmesi: " + currentDelay + " ms"));
            }
            @Override
            protected void applyValue() {
                OtoMace.delayMs = (int) (100 + (this.value * 900));
            }
        });

        // 5. Chest ESP
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Chest ESP: " + (ChestESP.active ? "ACIK" : "KAPALI")), btn -> {
            ChestESP.active = !ChestESP.active;
            btn.setMessage(Text.literal("Chest ESP: " + (ChestESP.active ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy + 20, 200, 20).build());

        // 6. Base Finder
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Maden/Base Tara: " + (BaseFinder.active ? "ACIK" : "KAPALI")), btn -> {
            BaseFinder.active = !BaseFinder.active;
            btn.setMessage(Text.literal("Maden/Base Tara: " + (BaseFinder.active ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy + 50, 200, 20).build());

        // 7. X-Ray
        this.addDrawableChild(ButtonWidget.builder(Text.literal("X-Ray: " + (XRay.active ? "ACIK" : "KAPALI")), btn -> {
            XRay.active = !XRay.active;
            btn.setMessage(Text.literal("X-Ray: " + (XRay.active ? "ACIK" : "KAPALI")));
            if (net.minecraft.client.MinecraftClient.getInstance().worldRenderer != null) {
                net.minecraft.client.MinecraftClient.getInstance().worldRenderer.reload();
            }
        }).dimensions(cx - 100, cy + 80, 200, 20).build());

        // 8. Kapat
        this.addDrawableChild(ButtonWidget.builder(Text.literal("KAPAT"), btn -> this.close())
            .dimensions(cx - 50, cy + 120, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Animasyonlu arka plan (Mavi-Mor)
        rotation += delta * 0.5f;
        float r = (float) (Math.sin(rotation) * 0.5f + 0.5f);
        float g = 0.1f;
        float b = (float) (Math.cos(rotation) * 0.5f + 0.5f);
        
        context.fillGradient(0, 0, this.width, this.height, 
            (int)(r * 200) << 24 | (int)(r * 255) << 16 | (int)(g * 255) << 8 | (int)(b * 255), 
            (int)(b * 200) << 24 | (int)(r * 255) << 16 | (int)(g * 255) << 8 | (int)(b * 255)
        );

        // Ayı Logosu
        String[] bearLogo = {
            "   (  )   ",
            "  (    )  ",
            " (  O O ) ",
            "  \\  ^  / ",
            "   `---'  "
        };
        int logoY = 30;
        for (String line : bearLogo) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(line), this.width / 2, logoY, 0xFFAA00);
            logoY += 10;
        }

        // Başlık
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 90, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
