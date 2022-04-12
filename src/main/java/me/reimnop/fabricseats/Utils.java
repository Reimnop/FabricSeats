package me.reimnop.fabricseats;

import net.minecraft.util.Identifier;

public class Utils {
    public static Identifier id(String path) {
        return new Identifier(FabricSeats.MODID, path);
    }
}
