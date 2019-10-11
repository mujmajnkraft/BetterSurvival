package com.mujmajnkraft.bettersurvival.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static int agilitylevel;
	public static int arrowrecoverylevel;
	public static int blastlevel;
	public static int blockpowerlevel;
	public static int diamondslevel;
	public static int educationlevel;
	public static int flinglevel;
	public static int heavylevel;
	public static int highjumplevel;
	public static int multishotlevel;
	public static int penetrationlevel;
	public static int rangelevel;
	public static int rapidfirelevel;
	public static int reflectionlevel;
	public static int smeltinglevel;
	public static int spellshieldlevel;
	public static int tunnelinglevel;
	public static int vampirismlevel;
	public static int versatilitylevel;
	public static int vitalitylevel;
	public static int weightlesslevel;
	
	public static boolean agility;
	public static boolean arrowrecovery;
	public static boolean blast;
	public static boolean blockpower;
	public static boolean diamonds;
	public static boolean education;
	public static boolean fling;
	public static boolean heavy;
	public static boolean highjump;
	public static boolean multishot;
	public static boolean penetration;
	public static boolean range;
	public static boolean rapidfire;
	public static boolean reflection;
	public static boolean smelting;
	public static boolean spellshield;
	public static boolean tunneling;
	public static boolean vampirism;
	public static boolean versatility;
	public static boolean vitality;
	public static boolean weightless;
	
	public static boolean allowvanillashields;
	public static boolean FOV;
	public static boolean integration;
	
	public static void init(File file)
	{
		config = new Configuration(file);
		syncConfig();
	}
	
	public static void syncConfig()
	{
		String category;
		
		category = "Enchantments max level";
		config.addCustomCategoryComment(category, "Customize max level of mod enchantments (0 will disalbe them completely)");
		agilitylevel = config.getInt("agility", category, 2, 0, 10, "agility");
		arrowrecoverylevel = config.getInt("arrowrecovery", category, 3, 0, 10, "arrowrecovery");
		blastlevel = config.getInt("blast", category, 2, 0, 10, "blast");
		blockpowerlevel = config.getInt("blockpower", category, 3, 0, 10, "blockpower");
		diamondslevel = config.getInt("diamonds everywere", category, 3, 0, 10, "diamonds everywere");
		educationlevel = config.getInt("education", category, 3, 0, 10, "education");
		flinglevel = config.getInt("fling", category, 2, 0, 10, "fling");
		heavylevel = config.getInt("heavy", category, 1, 0, 1, "heavy");
		highjumplevel = config.getInt("high jump", category, 2, 0, 10, "high jump");
		multishotlevel = config.getInt("multishot", category, 3, 0, 10, "multishot");
		penetrationlevel = config.getInt("penetration", category, 5, 0, 10, "penetration");
		rangelevel = config.getInt("range", category, 1, 0, 1, "range");
		rapidfirelevel = config.getInt("rapid fire", category, 3, 0, 10, "rapid fire");
		reflectionlevel = config.getInt("reflection", category, 3, 0, 10, "reflection");
		smeltinglevel = config.getInt("smelting", category, 1, 0, 1, "smelting");
		spellshieldlevel = config.getInt("spellshield", category, 3, 0, 10, "spellshield");
		tunnelinglevel = config.getInt("tunneling", category, 2, 0, 10, "tunneling");
		vampirismlevel = config.getInt("vampirism", category, 2, 0, 10, "vampirism");
		versatilitylevel = config.getInt("versatility", category, 1, 0, 1, "versatility");
		vitalitylevel = config.getInt("vitality", category, 1, 0, 10, "vitality");
		weightlesslevel = config.getInt("weightless", category, 1, 0, 1, "weightless");
		
		String category2;
		
		category2 = "Treasure enchantments";
		config.addCustomCategoryComment(category2, "Set whether are mod enchantments theasure or not");
		agility = config.getBoolean("agility", category2, false, "agility");
		arrowrecovery = config.getBoolean("arrowrecovery", category2, false, "arrowrecovery");
		blast = config.getBoolean("blast", category2, false, "blast");
		blockpower = config.getBoolean("blockpower", category, false,"blockpower");
		diamonds = config.getBoolean("diamonds", category2, false, "diamonds");
		education = config.getBoolean("education", category2, false, "education");
		fling = config.getBoolean("fling", category2, false, "fling");
		heavy = config.getBoolean("heavy", category, false, "heavy");
		highjump = config.getBoolean("highjump", category2, false, "highjump");
		multishot = config.getBoolean("multishot", category2, false, "multishot");
		penetration = config.getBoolean("penetration", category2, false, "penetration");
		range = config.getBoolean("range", category2, false, "range");
		rapidfire = config.getBoolean("rapidfire", category2, false, "rapidfire");
		reflection = config.getBoolean("reflection", category2, false, "reflection");
		smelting = config.getBoolean("smelting", category2, false, "smelting");
		spellshield = config.getBoolean("spellshield", category, false, "spellshield");
		tunneling = config.getBoolean("tunneling", category2, false, "tunneling");
		vampirism = config.getBoolean("vampirism", category2, false, "vampirism");
		versatility = config.getBoolean("versatility", category2, false, "versatility");
		vitality = config.getBoolean("vitality", category2, false, "vitality");
		weightless = config.getBoolean("weightless", category2, false, "weightless");
		
		String category3;
		
		category3 = "Other";
		config.addCustomCategoryComment(category3, "Other settings");
		FOV = config.getBoolean("FOV", category3, true, "When set to true, your movement speed won't effect your field of view.");
		integration = config.getBoolean("integration", category3, true, "When set to true, you can craft weapons from materials from other mods");
		allowvanillashields = config.getBoolean("AllowVanillaShields", category3, true, "When set flase, vanilla shield recipe is disabled");
		
		config.save();
	}

}
