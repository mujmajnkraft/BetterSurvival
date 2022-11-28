package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ItemDagger extends ItemCustomWeapon{
	
	public ItemDagger(ToolMaterial material) {
		super(material, 0.7F, 0.8F);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Dagger");
	}
	
	public float getBackstabMultiplyer(EntityLivingBase user, Entity target) {
		double attackerYaw = Math.toRadians(user.rotationYaw);
		double targetYaw = Math.toRadians(target.rotationYaw);
		if(Math.abs(Math.sin(attackerYaw) - Math.sin(targetYaw)) < 0.5D && Math.abs(Math.cos(attackerYaw)-Math.cos(targetYaw))< 0.5D) {
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.assassinate, user.getHeldItemMainhand());
			return 2 + l/3.0f;
		}
		return 1.0F;
	}
}