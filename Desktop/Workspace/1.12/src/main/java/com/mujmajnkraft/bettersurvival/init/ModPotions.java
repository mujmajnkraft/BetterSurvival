package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.potions.PotionCustom;
import com.mujmajnkraft.bettersurvival.potions.PotionMilk;
import com.mujmajnkraft.bettersurvival.potions.PotionWarp;
import com.mujmajnkraft.bettersurvival.potions.PotionCleanse;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModPotions {
	public static Potion stun;
	public static Potion warp;
	public static Potion antiwarp;
	public static Potion milk;
	public static Potion cure;
	public static Potion dispel;
	
	public static void init()
	{
		stun = new PotionCustom(false, 16774925);
		warp = new PotionWarp(false, 9838591);
		antiwarp = new PotionCustom(false, 14378216);
		milk = new PotionMilk(true, 16777215);
		cure = new PotionCleanse(false, 16774925);
		dispel = new PotionCleanse(true, 11921919);
		
		stun.setRegistryName("Stun");
		stun.setPotionName("effect.stun");
		warp.setPotionName("effect.warp");
		antiwarp.setRegistryName("Antiwarp");
		antiwarp.setPotionName("effect.antiwarp");
		milk.setRegistryName("Milk");
		milk.setPotionName("effect.milk");
		cure.setRegistryName("Cure");
		cure.setPotionName("effect.cure");
		dispel.setRegistryName("dispel");
		dispel.setPotionName("effect.dispel");
	}
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		event.getRegistry().register(stun);
		stun.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-3368-940E-514C1F160890", -10, 2);
		stun.registerPotionAttributeModifier(SharedMonsterAttributes.FOLLOW_RANGE, "26107045-134f-4c54-a645-75c3ae5c7a27", -2048, 0);
		event.getRegistry().register(warp);
		event.getRegistry().register(antiwarp);
		event.getRegistry().register(milk);
		event.getRegistry().register(cure);
		event.getRegistry().register(dispel);
	}
	
	public static void register()
	{
		
	}
}
