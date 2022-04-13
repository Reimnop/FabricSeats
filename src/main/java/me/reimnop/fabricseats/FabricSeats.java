package me.reimnop.fabricseats;

import me.reimnop.fabricseats.block.SlabSeatBlock;
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

	public static SlabSeatBlock SLAB_SEAT_BLOCK = new SlabSeatBlock(FabricBlockSettings
			.of(Material.WOOD)
			.sounds(BlockSoundGroup.WOOD)
			.strength(4.0f));
	public static BlockItem SLAB_SEAT_BLOCK_ITEM = new BlockItem(SLAB_SEAT_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS));

	public static EntityType<SeatEntity> SEAT_ENTITY;
	public static BlockEntityType<SeatBlockEntity> SEAT_BLOCK_ENTITY;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello world!");

		Registry.register(Registry.BLOCK, Utils.id("slab_seat"), SLAB_SEAT_BLOCK);
		Registry.register(Registry.ITEM, Utils.id("slab_seat"), SLAB_SEAT_BLOCK_ITEM);

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
						.create(SeatBlockEntity::new, SLAB_SEAT_BLOCK)
						.build());
	}
}
