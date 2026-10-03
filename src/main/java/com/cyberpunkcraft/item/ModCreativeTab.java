package com.cyberpunkcraft.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;

import com.cyberpunkcraft.CyberpunkCraft;
import com.cyberpunkcraft.block.ModBlocks;

public final class ModCreativeTab {
	public static final ResourceKey<CreativeModeTab> KEY = ResourceKey.create(
			BuiltInRegistries.CREATIVE_MODE_TAB.key(), CyberpunkCraft.id("cyberpunkcraft")
	);

	public static final CreativeModeTab TAB = FabricCreativeModeTab.builder()
			.icon(() -> new ItemStack(ModItems.NEON_KATANA))
			.title(Component.translatable("itemGroup.cyberpunkcraft"))
			.displayItems((params, output) -> {
				output.accept(ModItems.NEON_KATANA);
				output.accept(ModItems.PLASMA_BLASTER);
				output.accept(ModItems.CYBER_VISOR);
				output.accept(ModItems.CYBER_CHESTPLATE);
				output.accept(ModItems.CYBER_LEGGINGS);
				output.accept(ModItems.CYBER_BOOTS);
				output.accept(ModItems.SYNTH_COLA);
				output.accept(ModItems.CHROME_INGOT);
				output.accept(ModItems.NEON_DUST);
				output.accept(ModItems.MICROCHIP);

				output.accept(ModBlocks.NEON_BLOCK_CYAN);
				output.accept(ModBlocks.NEON_BLOCK_MAGENTA);
				output.accept(ModBlocks.NEON_BLOCK_PINK);
				output.accept(ModBlocks.NEON_BLOCK_PURPLE);
				output.accept(ModBlocks.NEON_BLOCK_LIME);
				output.accept(ModBlocks.NEON_BLOCK_ORANGE);
				output.accept(ModBlocks.CHROME_BLOCK);
				output.accept(ModBlocks.CHROME_PLATING);
				output.accept(ModBlocks.CIRCUIT_BLOCK);
				output.accept(ModBlocks.CYBER_GLASS);
				output.accept(ModBlocks.ASPHALT);
				output.accept(ModBlocks.HAZARD_BLOCK);
			})
			.build();

	private ModCreativeTab() {
	}

	public static void initialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, KEY, TAB);
	}
}
