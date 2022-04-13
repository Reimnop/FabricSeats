package me.reimnop.fabricseats.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SlabSeatBlock extends AbstractSeatBlock {
    public SlabSeatBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Vec3d getSitOffset() {
        return new Vec3d(0.5, 0.5, 0.5);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
    }
}
