package me.reimnop.fabricseats.blockentity;

import me.reimnop.fabricseats.FabricSeats;
import me.reimnop.fabricseats.entity.SeatEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class SeatBlockEntity extends BlockEntity {
    private Optional<UUID> seatEntityUuid = Optional.empty();

    public SeatBlockEntity(BlockPos pos, BlockState state) {
        super(FabricSeats.SEAT_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SeatBlockEntity be) {
        if (world.isClient) {
            return;
        }

        SeatEntity seatEntity = be.getSeatEntity((ServerWorld) world);
        if (seatEntity != null) {
            seatEntity.setPosition(pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5);
            seatEntity.setSeatPos(pos);
        }
    }

    public void setSeatEntity(@Nullable SeatEntity seatEntity) {
        if (seatEntity == null) {
            seatEntityUuid = Optional.empty();
            markDirty();
        }
        else {
            seatEntityUuid = Optional.of(seatEntity.getUuid());
            markDirty();
        }
    }

    @Nullable
    public SeatEntity getSeatEntity(ServerWorld world) {
        if (seatEntityUuid.isEmpty()) {
            return null;
        }
        Entity entity = world.getEntity(seatEntityUuid.get());
        if (entity == null) {
            return null;
        }
        if (!(entity instanceof SeatEntity)) {
            return null;
        }
        return (SeatEntity) entity;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        seatEntityUuid.ifPresent(uuid -> nbt.putUuid("SeatEntityUUID", uuid));
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("SeatEntityUUID")) {
            seatEntityUuid = Optional.of(nbt.getUuid("SeatEntityUUID"));
        }
        else {
            seatEntityUuid = Optional.empty();
        }
        super.readNbt(nbt);
    }
}
