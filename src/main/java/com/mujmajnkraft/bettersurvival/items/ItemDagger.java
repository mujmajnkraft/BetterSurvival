package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDagger extends ItemCustomWeapon{
	
	public ItemDagger(ToolMaterial material) {
		super(material, ForgeConfigHandler.weapons.daggerDmgMod, ForgeConfigHandler.weapons.daggerSpd);
		this.setRegistryName(Reference.MOD_ID,"item"+material.name().toLowerCase()+"dagger");
		this.setTranslationKey(material.name().toLowerCase()+"dagger");
	}
	
	public float getBackstabMultiplier(EntityLivingBase user, Entity target, boolean offhand) {
		double attackerYaw = Math.toRadians(user.rotationYaw);
		double targetYaw = Math.toRadians(target.rotationYaw);
		if(Math.abs(Math.sin(attackerYaw) - Math.sin(targetYaw)) < 0.5D && Math.abs(Math.cos(attackerYaw)-Math.cos(targetYaw))< 0.5D) {
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.assassinate, offhand ? user.getHeldItemOffhand() : user.getHeldItemMainhand());
			return 2 + l/3.0f;
		}
		return 1.0F;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String s = net.minecraft.client.resources.I18n.format(Reference.MOD_ID + ".dagger.desc");
		tooltip.add(TextFormatting.AQUA + s);
	}
}