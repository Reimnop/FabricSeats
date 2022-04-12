package me.reimnop.fabricseats.renderer;

import me.reimnop.fabricseats.entity.SeatEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class SeatEntityRenderer extends EntityRenderer<SeatEntity> {
    public SeatEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(SeatEntity entity) {
        return null;
    }
}
