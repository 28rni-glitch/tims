package com.cyberpunkcraft.test;

import net.minecraft.client.CameraType;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.core.BlockPos;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;

import com.cyberpunkcraft.block.ModBlocks;

/**
 * Builds a small neon street in a real client, then takes screenshots of it,
 * of the cyber armor, and of the inventory so the textures can be checked.
 */
@SuppressWarnings("UnstableApiUsage")
public class CyberpunkCraftClientGameTest implements FabricClientGameTest {
	private static final int ROAD_Y = 200;

	@Override
	public void runTest(ClientGameTestContext context) {
		try (TestSingleplayerContext singleplayer = context.worldBuilder()
				.adjustSettings(creator -> creator.setGameMode(WorldCreationUiState.SelectedGameMode.CREATIVE))
				.create()) {
			TestServerContext server = singleplayer.getServer();
			singleplayer.getConnection().waitForChunksRender();

			BlockPos spawn = context.computeOnClient(client -> BlockPos.containing(client.player.position()));
			int x = spawn.getX();
			int z = spawn.getZ();

			server.runCommand("time set midnight");
			server.runCommand("weather clear");
			buildStreet(server, x, z);
			giveGear(server);
			server.runCommand("tp @a %d.5 %d %d.5 0 10".formatted(x, ROAD_Y + 1, z));

			BlockPos marker = new BlockPos(x, ROAD_Y + 12, z + 18);
			server.runCommand("setblock %d %d %d cyberpunkcraft:neon_block_orange".formatted(marker.getX(), marker.getY(), marker.getZ()));
			context.waitFor(client -> client.level.getBlockState(marker).is(ModBlocks.NEON_BLOCK_ORANGE));
			context.waitTicks(20);
			singleplayer.getConnection().waitForChunksRender();
			context.takeScreenshot("cyberpunk_street");

			equipArmor(server);
			context.runOnClient(client -> client.options.setCameraType(CameraType.THIRD_PERSON_FRONT));
			context.waitTicks(10);
			context.takeScreenshot("cyber_armor");
			context.runOnClient(client -> client.options.setCameraType(CameraType.FIRST_PERSON));

			server.runCommand("gamemode survival @a");
			context.waitTicks(5);
			context.getInput().pressKey(options -> options.keyInventory);
			context.waitForScreen(InventoryScreen.class);
			context.takeScreenshot("inventory");
			context.setScreen(() -> null);
		}
	}

	private static void buildStreet(TestServerContext server, int x, int z) {
		int y = ROAD_Y;

		// Road with a glowing center line and a hazard stripe.
		fill(server, x - 7, y, z - 2, x + 7, y, z + 18, "asphalt");
		fill(server, x - 7, y, z + 2, x + 7, y, z + 2, "hazard_block");
		fill(server, x, y, z + 4, x, y, z + 18, "neon_block_cyan");

		// Left building: chrome plating with glass windows and magenta/pink neon bands.
		fill(server, x - 6, y + 1, z + 3, x - 6, y + 12, z + 18, "chrome_plating");
		fill(server, x - 6, y + 2, z + 5, x - 6, y + 4, z + 16, "cyber_glass");
		fill(server, x - 6, y + 7, z + 5, x - 6, y + 9, z + 16, "cyber_glass");
		fill(server, x - 6, y + 6, z + 3, x - 6, y + 6, z + 18, "neon_block_magenta");
		fill(server, x - 6, y + 12, z + 3, x - 6, y + 12, z + 18, "neon_block_pink");

		// Right building: circuit boards with purple/lime neon bands.
		fill(server, x + 6, y + 1, z + 3, x + 6, y + 12, z + 18, "circuit_block");
		fill(server, x + 6, y + 2, z + 5, x + 6, y + 4, z + 16, "cyber_glass");
		fill(server, x + 6, y + 6, z + 3, x + 6, y + 6, z + 18, "neon_block_purple");
		fill(server, x + 6, y + 12, z + 3, x + 6, y + 12, z + 18, "neon_block_lime");

		// Back wall: chrome with an orange neon sign.
		fill(server, x - 5, y + 1, z + 18, x + 5, y + 11, z + 18, "chrome_block");
		fill(server, x - 3, y + 7, z + 17, x + 3, y + 8, z + 17, "neon_block_orange");
	}

	private static void fill(TestServerContext server, int x1, int y1, int z1, int x2, int y2, int z2, String block) {
		server.runCommand("fill %d %d %d %d %d %d cyberpunkcraft:%s".formatted(x1, y1, z1, x2, y2, z2, block));
	}

	private static void giveGear(TestServerContext server) {
		server.runCommand("clear @a");

		String[] items = {
				// Hotbar
				"neon_katana", "plasma_blaster", "synth_cola", "chrome_ingot", "neon_dust",
				"microchip", "neon_block_cyan", "circuit_block", "cyber_glass",
				// Main inventory
				"neon_block_magenta", "neon_block_pink", "neon_block_purple", "neon_block_lime",
				"neon_block_orange", "chrome_block", "chrome_plating", "asphalt", "hazard_block"
		};

		for (String item : items) {
			server.runCommand("give @a cyberpunkcraft:" + item);
		}
	}

	private static void equipArmor(TestServerContext server) {
		server.runCommand("item replace entity @a armor.head with cyberpunkcraft:cyber_visor");
		server.runCommand("item replace entity @a armor.chest with cyberpunkcraft:cyber_chestplate");
		server.runCommand("item replace entity @a armor.legs with cyberpunkcraft:cyber_leggings");
		server.runCommand("item replace entity @a armor.feet with cyberpunkcraft:cyber_boots");
	}
}
