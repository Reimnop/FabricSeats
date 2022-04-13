package me.reimnop.fabricseats.entity;

import me.reimnop.fabricseats.FabricSeats;
import me.reimnop.fabricseats.blockentity.SeatBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SeatEntity extends Entity {
    private BlockPos seatPos = BlockPos.ORIGIN;

    // Kind of a hack
    private int killTimer = 0;

    public SeatEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
        init();
    }

    public SeatEntity(World world, double x, double y, double z) {
        super(FabricSeats.SEAT_ENTITY, world);
        setPosition(x, y, z);
        prevX = x;
        prevY = y;
        prevZ = z;
        init();
    }

    private void init() {
        setNoGravity(true);
        setInvulnerable(true);
        setInvisible(true);
    }

    @Override
    public double getMountedHeightOffset() {
        return -0.25;
    }

    public void setSeatPos(BlockPos pos) {
        seatPos = pos;
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isClient) {
            return;
        }
        // If it's been 40 ticks after seat is destroyed (to account for block entity tick lag)
        // kill the seat entity
        // Kill in here to account for TNTs and setblock command
        BlockEntity blockEntity = world.getBlockEntity(seatPos);
        if (!(blockEntity instanceof SeatBlockEntity)) {
            killTimer++;
        }
        else {
            killTimer = 0;
        }
        if (killTimer > 40) {
            kill();
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("SeatPos")) {
            int[] pos = nbt.getIntArray("SeatPos");
            seatPos = new BlockPos(pos[0], pos[1], pos[2]);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putIntArray("SeatPos", new int[] { seatPos.getX(), seatPos.getY(), seatPos.getZ() });
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
