package com.cyberpunkcraft.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

/**
 * Gives players wearing cyber armor their bonus effects, checked once per second.
 */
public final class CyberArmorEffects {
	private static final int CHECK_INTERVAL_TICKS = 20;
	// Night Vision flickers when it has less than 10 seconds left, so keep it topped up above that.
	private static final int NIGHT_VISION_TICKS = 15 * 20;
	private static final int SET_BONUS_TICKS = 3 * 20;

	private CyberArmorEffects() {
	}

	public static void initialize() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (server.getTickCount() % CHECK_INTERVAL_TICKS != 0) {
				return;
			}

			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				applyEffects(player);
			}
		});
	}

	private static void applyEffects(ServerPlayer player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CYBER_VISOR)) {
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, NIGHT_VISION_TICKS, 0, true, false, true));
		}

		if (hasFullSet(player)) {
			player.addEffect(new MobEffectInstance(MobEffects.SPEED, SET_BONUS_TICKS, 0, true, false, true));
			player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, SET_BONUS_TICKS, 0, true, false, true));
		}
	}

	private static boolean hasFullSet(ServerPlayer player) {
		return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CYBER_VISOR)
				&& player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CYBER_CHESTPLATE)
				&& player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CYBER_LEGGINGS)
				&& player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CYBER_BOOTS);
	}
}
