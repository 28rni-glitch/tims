package com.cyberpunkcraft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.Identifier;

import net.fabricmc.api.ModInitializer;

import com.cyberpunkcraft.block.ModBlocks;
import com.cyberpunkcraft.item.CyberArmorEffects;
import com.cyberpunkcraft.item.ModCreativeTab;
import com.cyberpunkcraft.item.ModItems;

public class CyberpunkCraft implements ModInitializer {
	public static final String MOD_ID = "cyberpunkcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModItems.initialize();
		ModCreativeTab.initialize();
		CyberArmorEffects.initialize();

		LOGGER.info("Wake up, samurai. Cyberpunk Craft is online.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
