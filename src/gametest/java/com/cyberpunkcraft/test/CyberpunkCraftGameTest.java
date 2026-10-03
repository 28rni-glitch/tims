package com.cyberpunkcraft.test;

import java.util.Map;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import net.fabricmc.fabric.api.gametest.v1.GameTest;

import com.cyberpunkcraft.CyberpunkCraft;
import com.cyberpunkcraft.block.ModBlocks;

public class CyberpunkCraftGameTest {
	@GameTest
	public void neonBlocksGlow(GameTestHelper helper) {
		Block[] neonBlocks = {
				ModBlocks.NEON_BLOCK_CYAN, ModBlocks.NEON_BLOCK_MAGENTA, ModBlocks.NEON_BLOCK_PINK,
				ModBlocks.NEON_BLOCK_PURPLE, ModBlocks.NEON_BLOCK_LIME, ModBlocks.NEON_BLOCK_ORANGE
		};

		for (int i = 0; i < neonBlocks.length; i++) {
			BlockPos pos = new BlockPos(i, 1, 1);
			helper.setBlock(pos, neonBlocks[i]);
			int light = helper.getBlockState(pos).getLightEmission();

			if (light != 15) {
				throw fail(helper, BuiltInRegistries.BLOCK.getKey(neonBlocks[i]) + " should give off light level 15, got " + light);
			}
		}

		helper.succeed();
	}

	@GameTest
	public void allRecipesLoad(GameTestHelper helper) {
		MinecraftServer server = helper.getLevel().getServer();
		Map<Identifier, Resource> recipeFiles = server.getResourceManager().listResources(
				"recipe", file -> file.getNamespace().equals(CyberpunkCraft.MOD_ID) && file.getPath().endsWith(".json")
		);

		if (recipeFiles.isEmpty()) {
			throw fail(helper, "No Cyberpunk Craft recipe files were found");
		}

		for (Identifier file : recipeFiles.keySet()) {
			String path = file.getPath().substring("recipe/".length(), file.getPath().length() - ".json".length());
			Identifier recipeId = CyberpunkCraft.id(path);

			if (server.getRecipeManager().byKey(ResourceKey.create(Registries.RECIPE, recipeId)).isEmpty()) {
				throw fail(helper, "Recipe failed to load: " + recipeId);
			}
		}

		helper.succeed();
	}

	@GameTest
	public void everyBlockHasALootTable(GameTestHelper helper) {
		MinecraftServer server = helper.getLevel().getServer();

		for (Block block : BuiltInRegistries.BLOCK) {
			if (!BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(CyberpunkCraft.MOD_ID)) {
				continue;
			}

			Optional<ResourceKey<LootTable>> lootTableKey = block.getLootTable();

			if (lootTableKey.isEmpty() || server.reloadableRegistries().getLootTable(lootTableKey.get()) == LootTable.EMPTY) {
				throw fail(helper, "Missing loot table for " + BuiltInRegistries.BLOCK.getKey(block));
			}
		}

		helper.succeed();
	}

	private static GameTestAssertException fail(GameTestHelper helper, String message) {
		return new GameTestAssertException(Component.literal(message), (int) helper.getTick());
	}
}
