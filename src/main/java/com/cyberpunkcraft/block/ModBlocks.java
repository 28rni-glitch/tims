package com.cyberpunkcraft.block;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import com.cyberpunkcraft.CyberpunkCraft;

public final class ModBlocks {
	// Glowing neon blocks, one per color. Light level 15, like glowstone.
	public static final Block NEON_BLOCK_CYAN = registerNeon("neon_block_cyan", MapColor.COLOR_CYAN);
	public static final Block NEON_BLOCK_MAGENTA = registerNeon("neon_block_magenta", MapColor.COLOR_MAGENTA);
	public static final Block NEON_BLOCK_PINK = registerNeon("neon_block_pink", MapColor.COLOR_PINK);
	public static final Block NEON_BLOCK_PURPLE = registerNeon("neon_block_purple", MapColor.COLOR_PURPLE);
	public static final Block NEON_BLOCK_LIME = registerNeon("neon_block_lime", MapColor.COLOR_LIGHT_GREEN);
	public static final Block NEON_BLOCK_ORANGE = registerNeon("neon_block_orange", MapColor.COLOR_ORANGE);

	public static final Block CHROME_BLOCK = register(
			"chrome_block",
			Block::new,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.METAL)
					.requiresCorrectToolForDrops()
					.strength(5.0F, 6.0F)
					.sound(SoundType.METAL)
	);

	public static final Block CHROME_PLATING = register(
			"chrome_plating",
			Block::new,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.METAL)
					.requiresCorrectToolForDrops()
					.strength(3.0F, 6.0F)
					.sound(SoundType.METAL)
	);

	// Animated circuit board that glows faintly.
	public static final Block CIRCUIT_BLOCK = register(
			"circuit_block",
			Block::new,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_CYAN)
					.requiresCorrectToolForDrops()
					.strength(3.0F, 6.0F)
					.sound(SoundType.METAL)
					.lightLevel(state -> 7)
	);

	// Road surface that makes you walk faster.
	public static final Block ASPHALT = register(
			"asphalt",
			Block::new,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_BLACK)
					.requiresCorrectToolForDrops()
					.strength(1.5F, 6.0F)
					.sound(SoundType.STONE)
					.speedFactor(1.15F)
	);

	public static final Block HAZARD_BLOCK = register(
			"hazard_block",
			Block::new,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_YELLOW)
					.requiresCorrectToolForDrops()
					.strength(3.0F, 6.0F)
					.sound(SoundType.METAL)
	);

	// Dark tinted glass with a glowing cyan frame.
	public static final Block CYBER_GLASS = register(
			"cyber_glass",
			TransparentBlock::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
					.mapColor(MapColor.COLOR_BLACK)
	);

	private ModBlocks() {
	}

	private static Block registerNeon(String name, MapColor color) {
		return register(
				name,
				Block::new,
				BlockBehaviour.Properties.of()
						.mapColor(color)
						.strength(0.8F)
						.sound(SoundType.GLASS)
						.lightLevel(state -> 15)
		);
	}

	private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
		BlockItemId id = BlockItemId.create(CyberpunkCraft.id(name), CyberpunkCraft.id(name));

		Block block = blockFactory.apply(properties.setId(id.block()));
		Registry.register(BuiltInRegistries.BLOCK, id.block(), block);

		BlockItem blockItem = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(id.item()));
		Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

		return block;
	}

	public static void initialize() {
		// Loading this class registers the blocks above.
	}
}
