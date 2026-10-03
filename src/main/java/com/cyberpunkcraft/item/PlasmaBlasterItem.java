package com.cyberpunkcraft.item;

import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Fires an instant laser beam. Damages the first entity in its path and stops at walls.
 */
public class PlasmaBlasterItem extends Item {
	private static final double RANGE = 48.0;
	private static final float DAMAGE = 8.0F;
	private static final int COOLDOWN_TICKS = 12;
	private static final DustParticleOptions BEAM_PARTICLE = new DustParticleOptions(0x00FFFF, 1.0F);

	public PlasmaBlasterItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (!(level instanceof ServerLevel serverLevel)) {
			return InteractionResult.SUCCESS;
		}

		ItemStack stack = player.getItemInHand(hand);
		Vec3 start = player.getEyePosition();
		Vec3 direction = player.getViewVector(1.0F);
		Vec3 end = start.add(direction.scale(RANGE));

		// Stop the beam at the first solid block.
		BlockHitResult blockHit = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
		if (blockHit.getType() != HitResult.Type.MISS) {
			end = blockHit.getLocation();
		}

		// Find the first entity between the player and the wall.
		AABB searchArea = player.getBoundingBox().expandTowards(end.subtract(start)).inflate(1.0);
		EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
				player, start, end, searchArea,
				entity -> !entity.isSpectator() && entity.isPickable(),
				start.distanceToSqr(end)
		);

		if (entityHit != null) {
			Entity target = entityHit.getEntity();
			end = entityHit.getLocation();
			target.hurtServer(serverLevel, serverLevel.damageSources().playerAttack(player), DAMAGE);
			serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, end.x, end.y, end.z, 15, 0.2, 0.2, 0.2, 0.2);
		}

		drawBeam(serverLevel, start.add(direction.scale(0.8)).add(0.0, -0.15, 0.0), end);
		serverLevel.sendParticles(ParticleTypes.END_ROD, end.x, end.y, end.z, 6, 0.1, 0.1, 0.1, 0.05);
		serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
				SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.PLAYERS, 0.8F, 1.8F);

		player.getCooldowns().addCooldown(stack, COOLDOWN_TICKS);
		stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

		return InteractionResult.SUCCESS;
	}

	private static void drawBeam(ServerLevel level, Vec3 from, Vec3 to) {
		Vec3 step = to.subtract(from);
		int points = Math.max(1, (int) (step.length() * 3));

		for (int i = 0; i <= points; i++) {
			Vec3 point = from.add(step.scale(i / (double) points));
			level.sendParticles(BEAM_PARTICLE, point.x, point.y, point.z, 1, 0.0, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(Component.translatable("item.cyberpunkcraft.plasma_blaster.tooltip").withStyle(ChatFormatting.AQUA));
	}
}
