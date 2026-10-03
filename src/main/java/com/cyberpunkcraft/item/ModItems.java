package com.cyberpunkcraft.item;

import java.util.List;
import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;

import com.cyberpunkcraft.CyberpunkCraft;

public final class ModItems {
	// Crafting materials
	public static final Item CHROME_INGOT = register("chrome_ingot", Item::new, new Item.Properties());
	public static final Item NEON_DUST = register("neon_dust", Item::new, new Item.Properties());
	public static final Item MICROCHIP = register("microchip", Item::new, new Item.Properties());

	// Synth-Cola: an energy drink that gives Speed and Haste. Durations are in ticks (20 ticks = 1 second).
	public static final FoodProperties SYNTH_COLA_FOOD = new FoodProperties.Builder()
			.nutrition(2)
			.saturationModifier(0.3F)
			.alwaysEdible()
			.build();
	public static final Consumable SYNTH_COLA_CONSUMABLE = Consumables.defaultDrink()
			.onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
					new MobEffectInstance(MobEffects.SPEED, 60 * 20, 1),
					new MobEffectInstance(MobEffects.HASTE, 60 * 20, 1)
			)))
			.build();
	public static final Item SYNTH_COLA = register(
			"synth_cola",
			Item::new,
			new Item.Properties()
					.stacksTo(16)
					.food(SYNTH_COLA_FOOD, SYNTH_COLA_CONSUMABLE)
					.usingConvertsTo(Items.GLASS_BOTTLE)
	);

	// Weapons
	public static final Item NEON_KATANA = register(
			"neon_katana",
			NeonKatanaItem::new,
			new Item.Properties()
					.sword(ModMaterials.CYBER_TOOL, 3.0F, -2.2F)
					.rarity(Rarity.EPIC)
	);
	public static final Item PLASMA_BLASTER = register(
			"plasma_blaster",
			PlasmaBlasterItem::new,
			new Item.Properties()
					.durability(500)
					.enchantable(15)
					.repairable(ModMaterials.REPAIRS_CYBER_GEAR)
					.rarity(Rarity.EPIC)
	);

	// Cyber armor. Wearing the visor gives Night Vision; the full set adds Speed and Jump Boost.
	public static final Item CYBER_VISOR = registerArmor("cyber_visor", ArmorType.HELMET);
	public static final Item CYBER_CHESTPLATE = registerArmor("cyber_chestplate", ArmorType.CHESTPLATE);
	public static final Item CYBER_LEGGINGS = registerArmor("cyber_leggings", ArmorType.LEGGINGS);
	public static final Item CYBER_BOOTS = registerArmor("cyber_boots", ArmorType.BOOTS);

	private ModItems() {
	}

	private static Item registerArmor(String name, ArmorType type) {
		return register(
				name,
				Item::new,
				new Item.Properties()
						.humanoidArmor(ModMaterials.CYBER_ARMOR, type)
						.durability(type.getDurability(ModMaterials.CYBER_ARMOR_DURABILITY))
						.rarity(Rarity.RARE)
		);
	}

	private static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, CyberpunkCraft.id(name));
		Item item = itemFactory.apply(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	public static void initialize() {
		// Loading this class registers the items above.
	}
}
