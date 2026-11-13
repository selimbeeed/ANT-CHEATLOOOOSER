package com.olivia.client.mixin;

import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.olivia.client.XRay;

@Mixin(ChunkOcclusionDataBuilder.class)
public class XRayMixin {

    @Inject(method = "markClosed", at = @At("HEAD"), cancellable = true)
    private void onMarkClosed(BlockPos pos, CallbackInfo ci) {
        // If X-Ray is active, prevent background blocks from occluding (hiding) the ores
        if (XRay.active) {
            ci.cancel(); // Cancel the occlusion calculation
        }
    }
}
