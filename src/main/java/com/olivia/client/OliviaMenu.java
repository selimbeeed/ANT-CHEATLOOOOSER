package com.olivia.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.block.Blocks;

public class OliviaMenu extends Screen {
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

        // 4. Oto Mace Delay Slider
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

        // 5. Base Finder
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Base Finder: " + (BaseFinder.active ? "ACIK" : "KAPALI")), btn -> {
            BaseFinder.active = !BaseFinder.active;
            btn.setMessage(Text.literal("Base Finder: " + (BaseFinder.active ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy + 20, 200, 20).build());

        // 6. Filtreler (Obsidyen)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Obsidyen Ara: " + (BaseFinder.FILTERED_BLOCKS.contains(Blocks.OBSIDIAN) ? "ACIK" : "KAPALI")), btn -> {
            if (BaseFinder.FILTERED_BLOCKS.contains(Blocks.OBSIDIAN)) BaseFinder.FILTERED_BLOCKS.remove(Blocks.OBSIDIAN);
            else BaseFinder.FILTERED_BLOCKS.add(Blocks.OBSIDIAN);
            btn.setMessage(Text.literal("Obsidyen Ara: " + (BaseFinder.FILTERED_BLOCKS.contains(Blocks.OBSIDIAN) ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy + 50, 200, 20).build());

        // 7. Filtreler (Sandık)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Sandik Ara: " + (BaseFinder.FILTERED_BLOCKS.contains(Blocks.CHEST) ? "ACIK" : "KAPALI")), btn -> {
            if (BaseFinder.FILTERED_BLOCKS.contains(Blocks.CHEST)) BaseFinder.FILTERED_BLOCKS.remove(Blocks.CHEST);
            else BaseFinder.FILTERED_BLOCKS.add(Blocks.CHEST);
            btn.setMessage(Text.literal("Sandik Ara: " + (BaseFinder.FILTERED_BLOCKS.contains(Blocks.CHEST) ? "ACIK" : "KAPALI")));
        }).dimensions(cx - 100, cy + 80, 200, 20).build());

        // 8. Kapat
        this.addDrawableChild(ButtonWidget.builder(Text.literal("KAPAT"), btn -> this.close())
            .dimensions(cx - 50, cy + 120, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFF5555);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
