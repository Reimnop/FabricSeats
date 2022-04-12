package me.reimnop.fabricseats;

import me.reimnop.fabricseats.renderer.SeatEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FabricSeats.SEAT_ENTITY, SeatEntityRenderer::new);
    }
}
