package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentAgility;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentArrowRecovery;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentBlast;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentBlockPower;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVersatility;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVitality;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentWeightless;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentReflection;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModEnchantments {
	public static Enchantment vampirism;
	public static Enchantment agility;
	public static Enchantment arrowrecovery;
	public static Enchantment highjump;
	public static Enchantment diamonds;
	public static Enchantment reflection;
	public static Enchantment heavy;
	public static Enchantment blast;
	public static Enchantment blockpower;
	public static Enchantment tunneling;
	public static Enchantment smelting;
	public static Enchantment spellshield;
	public static Enchantment education;
	public static Enchantment multishot;
	public static Enchantment versatility;
	public static Enchantment rapidfire;
	public static Enchantment range;
	public static Enchantment penetration;
	public static Enchantment fling;
	public static Enchantment vitality;
	public static Enchantment weightless;
	
	public static void init()
	{
		rapidfire = new EnchantmentRapidFire();
		blast = new EnchantmentBlast();
		blockpower = new EnchantmentBlockPower();
		vampirism = new EnchantmentVampirism();
		agility = new EnchantmentAgility();
		arrowrecovery = new EnchantmentArrowRecovery();
		highjump = new EnchantmentHighJump();
		diamonds = new EnchantmentDiamonds();
		reflection = new EnchantmentReflection();
		heavy = new EnchantmentHeavy();
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
	}
	
	public static void register()
	{
		GameRegistry.register(vampirism);
		GameRegistry.register(agility);
		GameRegistry.register(arrowrecovery);
		GameRegistry.register(blast);
		GameRegistry.register(blockpower);
		GameRegistry.register(highjump);
		GameRegistry.register(heavy);
		GameRegistry.register(diamonds);
		GameRegistry.register(reflection);
		GameRegistry.register(tunneling);
		GameRegistry.register(smelting);
		GameRegistry.register(spellshield);
		GameRegistry.register(education);
		GameRegistry.register(versatility);
		GameRegistry.register(multishot);
		GameRegistry.register(range);
		GameRegistry.register(rapidfire);
		GameRegistry.register(penetration);
		GameRegistry.register(fling);
		GameRegistry.register(vitality);
		GameRegistry.register(weightless);
	}

}
