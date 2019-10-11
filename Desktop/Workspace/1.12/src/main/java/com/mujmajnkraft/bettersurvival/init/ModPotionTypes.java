package com.mujmajnkraft.bettersurvival.init;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModPotionTypes {
	
	public static PotionType blindness;
	public static PotionType longblindness;
	public static PotionType decay;
	public static PotionType longdecay;
	public static PotionType strongdecay;
	public static PotionType warp;
	public static PotionType strongwarp;
	public static PotionType antiwarp;
	public static PotionType longantiwarp;
	public static PotionType milk;
	public static PotionType cure;
	public static PotionType dispel;
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<PotionType> event)
	{
		blindness = new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.BLINDNESS, 300)});
		longblindness = new PotionType("blindness", new PotionEffect[] {new PotionEffect(MobEffects.BLINDNESS, 800)});
		decay = new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.WITHER, 800)});
		longdecay = new PotionType("decay", new PotionEffect[] {new PotionEffect(MobEffects.WITHER, 2134)});
		strongdecay = new PotionType("decay", new PotionEffect[] {new PotionEffect(MobEffects.WITHER, 400, 1)});
		warp = new PotionType(new PotionEffect[] {new PotionEffect(ModPotions.warp, 1)});
		strongwarp = new PotionType("warp", new PotionEffect[] {new PotionEffect(ModPotions.warp, 1, 1)});
		antiwarp = new PotionType(new PotionEffect[] {new PotionEffect(ModPotions.antiwarp, 1800)});
		longantiwarp = new PotionType("antiwarp", new PotionEffect[] {new PotionEffect(ModPotions.antiwarp, 4800)});
		milk = new PotionType("milk", new PotionEffect [] {new PotionEffect(ModPotions.milk, 1)});
		cure = new PotionType("cure", new PotionEffect [] {new PotionEffect(ModPotions.cure, 1)});
		dispel = new PotionType("dispel", new PotionEffect [] {new PotionEffect(ModPotions.dispel, 1)});
		
		blindness.setRegistryName("blindness");
		longblindness.setRegistryName("long_blindness");
		decay.setRegistryName("decay");
		longdecay.setRegistryName("long_decay");
		strongdecay.setRegistryName("strong_decay");
		warp.setRegistryName("warp");
		strongwarp.setRegistryName("strong_warp");
		antiwarp.setRegistryName("antiwarp");
		longantiwarp.setRegistryName("long_antiwarp");
		milk.setRegistryName("milk");
		cure.setRegistryName("cure");
		dispel.setRegistryName("dispel");
		
		event.getRegistry().register(blindness);
		event.getRegistry().register(longblindness);
		event.getRegistry().register(decay);
		event.getRegistry().register(longdecay);
		event.getRegistry().register(strongdecay);
		event.getRegistry().register(warp);
		event.getRegistry().register(strongwarp);
		event.getRegistry().register(antiwarp);
		event.getRegistry().register(longantiwarp);
		event.getRegistry().register(milk);
		event.getRegistry().register(cure);
		event.getRegistry().register(dispel);
	}

}
