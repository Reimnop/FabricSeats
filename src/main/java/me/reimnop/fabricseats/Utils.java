package me.reimnop.fabricseats;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class Utils {
    public static Identifier id(String path) {
        return new Identifier(FabricSeats.MODID, path);
    }

    // source: https://ptb.discord.com/channels/507304429255393322/523251999899385875/794185582892941312
    // discord server: https://discord.com/invite/v6v4pMv
    /**
     * Can be used for outline shapes of directional blocks
     * @param direction the direction it will be rotated
     * @param xMin north xMin
     * @param yMin north yMin
     * @param zMin north zMin
     * @param xMax north xMax
     * @param yMax north yMax
     * @param zMax north zMax
     * @return rotated VoxelShape
     */
    public static VoxelShape rotatedCuboid(Direction direction, double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        double newXMin;
        double newYMin;
        double newZMin;
        double newXMax;
        double newYMax;
        double newZMax;
        switch (direction) {
            case NORTH -> {
                newXMin = xMin;
                newYMin = yMin;
                newZMin = zMin;
                newXMax = xMax;
                newYMax = yMax;
                newZMax = zMax;
            }
            case SOUTH -> {
                newXMin = 1 - xMax;
                newYMin = yMin;
                newZMin = 1 - zMin;
                newXMax = 1 - xMin;
                newYMax = yMax;
                newZMax = 1 - zMax;
            }
            case WEST -> {
                newXMin = zMin;
                newYMin = yMin;
                newZMin = 1 - xMin;
                newXMax = zMax;
                newYMax = yMax;
                newZMax = 1 - xMax;
            }
            case EAST -> {
                newXMin = 1 - zMin;
                newYMin = yMin;
                newZMin = xMin;
                newXMax = 1 - zMax;
                newYMax = yMax;
                newZMax = xMax;
            }
            default -> throw new IllegalArgumentException("Incorrect direction argument.");
        }
        if (newXMin > newXMax) {
            double temp = newXMax;
            newXMax = newXMin;
            newXMin = temp;
        }
        if (newZMin > newZMax) {
            double temp = newZMax;
            newZMax = newZMin;
            newZMin = temp;
        }
        return VoxelShapes.cuboid(newXMin, newYMin, newZMin, newXMax, newYMax, newZMax);
    }
}
