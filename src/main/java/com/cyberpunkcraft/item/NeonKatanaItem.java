package com.cyberpunkcraft.item;

import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * A glowing sword. Right-click to dash forward in the direction you are looking.
 */
public class NeonKatanaItem extends Item {
	private static final int DASH_COOLDOWN_TICKS = 40;
	private static final double DASH_SPEED = 1.8;
	private static final double DASH_LIFT = 0.3;

	public NeonKatanaItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		// Player movement is controlled by the client, so the dash is applied on both sides.
		Vec3 look = player.getLookAngle();
		Vec3 dash = new Vec3(look.x, 0.0, look.z).normalize().scale(DASH_SPEED);
		player.setDeltaMovement(dash.x, DASH_LIFT, dash.z);
		player.resetFallDistance();
		player.getCooldowns().addCooldown(stack, DASH_COOLDOWN_TICKS);

		if (level instanceof ServerLevel serverLevel) {
			serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
					SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.6F);
			serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
					player.getX(), player.getY() + 1.0, player.getZ(),
					25, 0.4, 0.6, 0.4, 0.15);
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(Component.translatable("item.cyberpunkcraft.neon_katana.tooltip").withStyle(ChatFormatting.AQUA));
	}
}
