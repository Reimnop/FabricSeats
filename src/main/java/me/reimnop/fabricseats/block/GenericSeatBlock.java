package me.reimnop.fabricseats.block;

import net.minecraft.util.math.Vec3d;

public class GenericSeatBlock extends AbstractSeatBlock {
    public GenericSeatBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Vec3d getSitOffset() {
        return new Vec3d(0.5, 0.75, 0.5);
    }
}
