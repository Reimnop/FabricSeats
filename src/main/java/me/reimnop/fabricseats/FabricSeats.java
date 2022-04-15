package me.reimnop.fabricseats;

import me.reimnop.fabricseats.block.*;
import me.reimnop.fabricseats.blockentity.SeatBlockEntity;
import me.reimnop.fabricseats.entity.SeatEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricSeats implements ModInitializer {
	public static final String MODID = "fabricseats";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	private static final FabricBlockSettings SEAT_BLOCK_SETTINGS = FabricBlockSettings
			.of(Material.WOOD)
			.sounds(BlockSoundGroup.WOOD)
			.hardness(2.0f);
	public static final FabricItemSettings SEAT_BLOCK_ITEM_SETTINGS = new FabricItemSettings()
			.group(ItemGroup.DECORATIONS);

	public static ChairBlock CHAIR_BLOCK = new ChairBlock(SEAT_BLOCK_SETTINGS);
	public static BlockItem CHAIR_BLOCK_ITEM = new BlockItem(CHAIR_BLOCK, SEAT_BLOCK_ITEM_SETTINGS);

	// BENCHES
	public static BenchLeftBlock BENCH_LEFT_BLOCK = new BenchLeftBlock(SEAT_BLOCK_SETTINGS);
	public static BlockItem BENCH_LEFT_BLOCK_ITEM = new BlockItem(BENCH_LEFT_BLOCK, SEAT_BLOCK_ITEM_SETTINGS);

	public static BenchMidBlock BENCH_MID_BLOCK = new BenchMidBlock(SEAT_BLOCK_SETTINGS);
	public static BlockItem BENCH_MID_BLOCK_ITEM = new BlockItem(BENCH_MID_BLOCK, SEAT_BLOCK_ITEM_SETTINGS);

	public static BenchRightBlock BENCH_RIGHT_BLOCK = new BenchRightBlock(SEAT_BLOCK_SETTINGS);
	public static BlockItem BENCH_RIGHT_BLOCK_ITEM = new BlockItem(BENCH_RIGHT_BLOCK, SEAT_BLOCK_ITEM_SETTINGS);
	// END BENCHES

	public static EntityType<SeatEntity> SEAT_ENTITY;
	public static BlockEntityType<SeatBlockEntity> SEAT_BLOCK_ENTITY;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello world!");

		Registry.register(Registry.BLOCK, Utils.id("chair"), CHAIR_BLOCK);
		Registry.register(Registry.ITEM, Utils.id("chair"), CHAIR_BLOCK_ITEM);

		// BENCHES
		Registry.register(Registry.BLOCK, Utils.id("bench_left"), BENCH_LEFT_BLOCK);
		Registry.register(Registry.ITEM, Utils.id("bench_left"), BENCH_LEFT_BLOCK_ITEM);

		Registry.register(Registry.BLOCK, Utils.id("bench_mid"), BENCH_MID_BLOCK);
		Registry.register(Registry.ITEM, Utils.id("bench_mid"), BENCH_MID_BLOCK_ITEM);

		Registry.register(Registry.BLOCK, Utils.id("bench_right"), BENCH_RIGHT_BLOCK);
		Registry.register(Registry.ITEM, Utils.id("bench_right"), BENCH_RIGHT_BLOCK_ITEM);
		// END BENCHES

		// ????
		SEAT_ENTITY = (EntityType<SeatEntity>) (Object) Registry.register(
				Registry.ENTITY_TYPE,
				Utils.id("seat_impl_entity"),
				FabricEntityTypeBuilder
						.create(SpawnGroup.MISC, SeatEntity::new)
						.dimensions(EntityDimensions.fixed(0.0f, 0.0f))
						.build());

		SEAT_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Utils.id("seat"),
				FabricBlockEntityTypeBuilder
						.create(SeatBlockEntity::new, CHAIR_BLOCK, BENCH_LEFT_BLOCK, BENCH_MID_BLOCK, BENCH_RIGHT_BLOCK)
						.build());
	}
}
