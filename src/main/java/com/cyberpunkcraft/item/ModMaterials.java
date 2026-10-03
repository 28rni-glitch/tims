package com.cyberpunkcraft.item;

import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import com.cyberpunkcraft.CyberpunkCraft;

public final class ModMaterials {
	// Items that can repair cyber gear in an anvil (chrome ingots).
	public static final TagKey<Item> REPAIRS_CYBER_GEAR = TagKey.create(Registries.ITEM, CyberpunkCraft.id("repairs_cyber_gear"));

	// Diamond-tier blade that is a little sharper and much more enchantable.
	public static final ToolMaterial CYBER_TOOL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
			1561, // durability
			8.0F, // mining speed
			3.5F, // attack damage bonus
			18, // enchantment value
			REPAIRS_CYBER_GEAR
	);

	public static final int CYBER_ARMOR_DURABILITY = 30;

	// Points at assets/cyberpunkcraft/equipment/cyber.json
	public static final ResourceKey<EquipmentAsset> CYBER_ARMOR_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, CyberpunkCraft.id("cyber"));

	public static final ArmorMaterial CYBER_ARMOR = new ArmorMaterial(
			CYBER_ARMOR_DURABILITY,
			Map.of(
					ArmorType.HELMET, 3,
					ArmorType.CHESTPLATE, 8,
					ArmorType.LEGGINGS, 6,
					ArmorType.BOOTS, 3
			),
			18, // enchantment value
			SoundEvents.ARMOR_EQUIP_NETHERITE,
			2.0F, // toughness
			0.0F, // knockback resistance
			REPAIRS_CYBER_GEAR,
			CYBER_ARMOR_ASSET
	);

	private ModMaterials() {
	}
}
