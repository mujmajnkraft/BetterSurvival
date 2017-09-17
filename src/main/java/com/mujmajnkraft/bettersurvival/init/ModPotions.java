package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.potions.PotionCustom;
import com.mujmajnkraft.bettersurvival.potions.PotionMilk;
import com.mujmajnkraft.bettersurvival.potions.PotionWarp;
import com.mujmajnkraft.bettersurvival.potions.PotionCleanse;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModPotions {
	public static Potion stun;
	public static Potion warp;
	public static Potion antiwarp;
	public static Potion milk;
	public static Potion cure;
	public static Potion dispell;
	
	public static void init()
	{
		stun = new PotionCustom(false, 16774925);
		warp = new PotionWarp(false, 9838591);
		antiwarp = new PotionCustom(false, 14378216);
		milk = new PotionMilk(true, 16777215);
		cure = new PotionCleanse(false, 16774925);
		dispell = new PotionCleanse(true, 11921919);
		
		stun.setRegistryName("Stun");
		stun.setPotionName("effect.stun");
		warp.setPotionName("effect.warp");
		antiwarp.setRegistryName("Antiwarp");
		antiwarp.setPotionName("effect.antiwarp");
		milk.setRegistryName("Milk");
		milk.setPotionName("effect.milk");
		cure.setRegistryName("Cure");
		cure.setPotionName("effect.cure");
		dispell.setRegistryName("Dispell");
		dispell.setPotionName("effect.dispell");
	}
	
	public static void register()
	{
		GameRegistry.register(stun).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-3368-940E-514C1F160890", -10, 2);
		stun.registerPotionAttributeModifier(SharedMonsterAttributes.FOLLOW_RANGE, "26107045-134f-4c54-a645-75c3ae5c7a27", -2048, 0);
		GameRegistry.register(warp);
		GameRegistry.register(antiwarp);
		GameRegistry.register(milk);
		GameRegistry.register(cure);
		GameRegistry.register(dispell);
	}
}
