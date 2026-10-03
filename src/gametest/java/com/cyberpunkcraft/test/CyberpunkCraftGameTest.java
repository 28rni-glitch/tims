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
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import net.fabricmc.fabric.api.gametest.v1.GameTest;

import com.cyberpunkcraft.CyberpunkCraft;
import com.cyberpunkcraft.block.ModBlocks;

public class CyberpunkCraftGameTest {
	@GameTest
	public void neonBlockGlows(GameTestHelper helper) {
		BlockPos neonPos = new BlockPos(1, 1, 1);
		helper.setBlock(neonPos, ModBlocks.NEON_BLOCK_CYAN);

		helper.succeedWhen(() -> {
			int light = helper.getLevel().getBrightness(LightLayer.BLOCK, helper.absolutePos(neonPos.above()));

			if (light < 14) {
				throw fail(helper, "Expected light level 14 next to a neon block, got " + light);
			}
		});
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
