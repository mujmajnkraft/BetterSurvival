package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentAgility;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentArrowRecovery;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentBlast;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentBlockPower;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVersatility;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVitality;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentWeightless;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentReflection;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentSpecialBonus;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentSpecialBonus.EnumWeaponType;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentSpellShield;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentDiamonds;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentEducation;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentFling;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentHeavy;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentHighJump;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentMultishot;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentPenetration;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentRange;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentRapidFire;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentTunneling;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVampirism;
import com.mujmajnkraft.bettersurvival.enchantments.EnchatnmentSmelting;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEnchantments {
	public static Enchantment vampirism;
	public static Enchantment agility;
	public static Enchantment arrowrecovery;
	public static Enchantment assassinate;
	public static Enchantment bash;
	public static Enchantment blockpower;
	public static Enchantment combo;
	public static Enchantment disarm;
	public static Enchantment highjump;
	public static Enchantment diamonds;
	public static Enchantment reflection;
	public static Enchantment heavy;
	public static Enchantment blast;
	public static Enchantment tunneling;
	public static Enchantment smelting;
	public static Enchantment education;
	public static Enchantment multishot;
	public static Enchantment spellshield;
	public static Enchantment versatility;
	public static Enchantment rapidfire;
	public static Enchantment range;
	public static Enchantment penetration;
	public static Enchantment fling;
	public static Enchantment vitality;
	public static Enchantment weightless;
	
	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event)
	{
		rapidfire = new EnchantmentRapidFire();
		blast = new EnchantmentBlast();
		blockpower = new EnchantmentBlockPower();
		vampirism = new EnchantmentVampirism();
		agility = new EnchantmentAgility();
		arrowrecovery = new EnchantmentArrowRecovery();
		assassinate = new EnchantmentSpecialBonus(EnumWeaponType.DAGGER);
		bash = new EnchantmentSpecialBonus(EnumWeaponType.HAMMER);
		combo = new EnchantmentSpecialBonus(EnumWeaponType.NUNCHAKU);
		highjump = new EnchantmentHighJump();
		diamonds = new EnchantmentDiamonds();
		disarm = new EnchantmentSpecialBonus(EnumWeaponType.BATTLEAXE);
		reflection = new EnchantmentReflection();
		heavy = new EnchantmentHeavy();
		assassinate = new EnchantmentSpecialBonus(EnumWeaponType.DAGGER);
		tunneling = new EnchantmentTunneling();
		range = new EnchantmentRange();
		smelting = new EnchatnmentSmelting();
		spellshield = new EnchantmentSpellShield();
		education = new EnchantmentEducation();
		multishot = new EnchantmentMultishot();
		versatility = new EnchantmentVersatility();
		penetration = new EnchantmentPenetration();
		fling = new EnchantmentFling();
		vitality = new EnchantmentVitality();
		weightless = new EnchantmentWeightless();
		
		assassinate.setRegistryName("assassinate");
		assassinate.setName("assassinate");
		combo.setRegistryName("combo");
		combo.setName("combo");
		bash.setRegistryName("bash");
		bash.setName("bash");
		disarm.setRegistryName("disarm");
		disarm.setName("disarm");
		
		event.getRegistry().register(vampirism);
		event.getRegistry().register(agility);
		event.getRegistry().register(arrowrecovery);
		event.getRegistry().register(assassinate);
		event.getRegistry().register(bash);
		event.getRegistry().register(blast);
		event.getRegistry().register(blockpower);
		event.getRegistry().register(combo);
		event.getRegistry().register(disarm);
		event.getRegistry().register(heavy);
		event.getRegistry().register(highjump);
		event.getRegistry().register(diamonds);
		event.getRegistry().register(reflection);
		event.getRegistry().register(tunneling);
		event.getRegistry().register(smelting);
		event.getRegistry().register(education);
		event.getRegistry().register(versatility);
		event.getRegistry().register(multishot);
		event.getRegistry().register(range);
		event.getRegistry().register(rapidfire);
		event.getRegistry().register(spellshield);
		event.getRegistry().register(penetration);
		event.getRegistry().register(fling);
		event.getRegistry().register(vitality);
		event.getRegistry().register(weightless);
	}

}
