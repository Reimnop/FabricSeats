package me.reimnop.fabricseats.block;

import me.reimnop.fabricseats.FabricSeats;
import me.reimnop.fabricseats.blockentity.SeatBlockEntity;
import me.reimnop.fabricseats.entity.SeatEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSeatBlock extends BlockWithEntity {
    public AbstractSeatBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof SeatBlockEntity seatBlockEntity)) {
            return ActionResult.FAIL;
        }
        SeatEntity seatEntity = seatBlockEntity.getSeatEntity((ServerWorld) world);
        // Check if seatEntity is already spawned, if it isn't, spawn it
        if (seatEntity == null) {
            Vec3d offset = getSitOffset();
            seatEntity = new SeatEntity(world,
                    pos.getX() + offset.getX(),
                    pos.getY() + offset.getY(),
                    pos.getZ() + offset.getZ());
            if (world.spawnEntity(seatEntity)) {
                seatBlockEntity.setSeatEntity(seatEntity);
            } else {
                return ActionResult.FAIL;
            }
        }
        // Ride the seat entity only if there isn't another player riding
        if (!seatEntity.hasPassengers()) {
            player.startRiding(seatEntity);
        } else if (seatEntity.getFirstPassenger() != player) {
            player.sendMessage(new LiteralText("This seat is occupied"), true);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (world.isClient()) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof SeatBlockEntity seatBlockEntity)) {
            return;
        }
        ServerWorld serverWorld = (ServerWorld) world;
        SeatEntity seatEntity = seatBlockEntity.getSeatEntity(serverWorld);
        if (seatEntity != null) {
            seatEntity.kill();
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SeatBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, FabricSeats.SEAT_BLOCK_ENTITY, (w, p, s, be) -> SeatBlockEntity.tick(w, p, s, be, this));
    }

    public abstract Vec3d getSitOffset();
}
