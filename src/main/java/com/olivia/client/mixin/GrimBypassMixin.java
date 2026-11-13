package com.olivia.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.olivia.client.OliviaClient;

@Mixin(ClientPlayerEntity.class)
public class GrimBypassMixin {

    @Inject(method = "sendMovementPackets", at = @At("HEAD"), cancellable = true)
    private void onSendMovement(CallbackInfo ci) {
        if (!OliviaClient.bypassActive) return;

        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        
        if (!player.isOnGround()) {
            ci.cancel();
            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                player.getX(), player.getY(), player.getZ(), true, player.isSneaking()
            ));
        }
    }
}
