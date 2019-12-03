package com.mujmajnkraft.bettersurvival.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.mujmajnkraft.bettersurvival.Reference;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static final String MAX_LEVEL = "max_level";
	public static final String IS_TREASURE = "is_treasure";
	public static final String MATERIAL_STATS = "material_stats";
	public static final String OTHER = "other";
	
	public static int assassinatelevel;
	public static int agilitylevel;
	public static int arrowrecoverylevel;
	public static int blastlevel;
	public static int blockpowerlevel;
	public static int bashlevel;
	public static int combolevel;
	public static int diamondslevel;
	public static int disarmlevel;
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
	
	public static boolean assassinate;
	public static boolean agility;
	public static boolean arrowrecovery;
	public static boolean bash;
	public static boolean blast;
	public static boolean blockpower;
	public static boolean combo;
	public static boolean diamonds;
	public static boolean disarm;
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

	public static ArrayList<Double> copperStats = new ArrayList<Double>();
	public static ArrayList<Double> bronzeStats = new ArrayList<Double>();
	public static ArrayList<Double> invarStats = new ArrayList<Double>();
	public static ArrayList<Double> silverStats = new ArrayList<Double>();
	public static ArrayList<Double> electrumStats = new ArrayList<Double>();
	public static ArrayList<Double> aluminiumStats = new ArrayList<Double>();
	public static ArrayList<Double> steelStats = new ArrayList<Double>();
	public static ArrayList<Double> signalumStats = new ArrayList<Double>();
	public static ArrayList<Double> lumiumStats = new ArrayList<Double>();
	public static ArrayList<Double> enderiumStats = new ArrayList<Double>();
	
	public static boolean allowvanillashields;
	public static boolean FOV;
	public static boolean integration;
	
	public static void init(File file)
	{
		config = new Configuration(file);
		syncFromFiles();
	}
	public static Configuration getConfig()
	{
		return config;
	}
	
	public static void clientPreInit()
	{
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}
	
	public static void syncFromFiles()
	{
		syncConfig(true, true);
	}
	
	public static void syncFromFields()
	{
		syncConfig(false, false);
	}
	
	public static void syncFromGui()
	{
		syncConfig(false, true);
	}
	
	public static void syncConfig(boolean loadFromFile, boolean readFields)
	{
		if (loadFromFile) config.load();
		
		Property agL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".agility", 2, "Set max level of Agility enchantments (setting it to 0 will disable it).", 0, 10);
		Property arL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".arrowrecovery", 3, "Set max level of Arrow Recovery enchantments (setting it to 0 will disable it).", 0, 10);
		Property asL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".assassinate", 3, "Set max level of Assassinate enchantments (setting it to 0 will disable it).", 0, 10);
		Property baL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".bash", 3, "Set max level of Bash enchantments (setting it to 0 will disable it).", 0, 10);
		Property blL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".blast", 2, "Set max level of Blast enchantments (setting it to 0 will disable it).", 0, 10);
		Property bpL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".blockpower", 3, "Set max level of Blocking Power enchantments (setting it to 0 will disable it).", 0, 10);
		Property coL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".combo", 3, "Set max level of Combo enchantments (setting it to 0 will disable it).", 0, 10);
		Property deL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".diamonds", 3, "Set max level of Diamonds Everywhere enchantments (setting it to 0 will disable it).", 0, 10);
		Property diL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".disarm", 3, "Set max level of Disarming enchantments (setting it to 0 will disable it).", 0, 10);
		Property edL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".education", 3, "Set max level of Education enchantments (setting it to 0 will disable it).", 0, 10);
		Property flL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".fling", 2, "Set max level of Fling enchantments (setting it to 0 will disable it).", 0, 10);
		Property heL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".heavy", 1, "Set max level of Heavy enchantments (setting it to 0 will disable it).", 0, 1);
		Property hiL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".highjump", 2, "Set max level of High Jump enchantments (setting it to 0 will disable it).", 0, 10);
		Property muL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".multishot", 3, "Set max level of Multishot enchantments (setting it to 0 will disable it).", 0, 10);
		Property peL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".penetration", 5, "Set max level of Penetration enchantments (setting it to 0 will disable it).", 0, 10);
		Property raL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".range", 1, "Set max level of Range enchantments (setting it to 0 will disable it).", 0, 1);
		Property rfL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".rapidfire", 5, "Set max level of Rapid Fire enchantments (setting it to 0 will disable it).", 0, 10);
		Property reL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".reflection", 3, "Set max level of Reflection enchantments (setting it to 0 will disable it).", 0, 10);
		Property smL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".smelting", 1, "Set max level of Smelting enchantments (setting it to 0 will disable it).", 0, 1);
		Property spL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".spellshield", 3, "Set max level of Spellshield enchantments (setting it to 0 will disable it).", 0, 10);
		Property tuL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".tunneling", 2, "Set max level of Tunneling enchantments (setting it to 0 will disable it).", 0, 10);
		Property vaL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".vampirism", 2, "Set max level of Vampirism enchantments (setting it to 0 will disable it).", 0, 10);
		Property veL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".versatility", 1, "Set max level of Versatility enchantments (setting it to 0 will disable it).", 0, 1);
		Property viL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".vitality", 1, "Set max level of Vitality enchantments (setting it to 0 will disable it).", 0, 10);
		Property weL = config.get(MAX_LEVEL, "enchantment." + Reference.MOD_ID + ".weightless", 1, "Set max level of Weightless enchantments (setting it to 0 will disable it).", 0, 10);
		
		Property ag = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".agility", false, "Set whether Agility is a treasure enchantment or not");
		Property ar = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".arrowrecovery", false, "Set whether Arrow Recovery is a treasure enchantment or not");
		Property as = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".assassinate", false, "Set whether Assassinate is a treasure enchantment or not");
		Property ba = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".bash", false, "Set whether Bash is a treasure enchantment or not");
		Property bl = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".blast", false, "Set whether Blast is a treasure enchantment or not");
		Property bp = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".blockpower", false, "Set whether Blocking Power is a treasure enchantment or not");
		Property co = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".combo", false, "Set whether Combo is a treasure enchantment or not");
		Property de = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".diamonds", false, "Set whether Diamonds Everywhere is a treasure enchantment or not");
		Property di = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".disarm", false, "Set whether Disarm is a treasure enchantment or not");
		Property ed = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".education", false, "Set whether Education is a treasure enchantment or not");
		Property fl = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".fling", false, "Set whether Fling is a treasure enchantment or not");
		Property he = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".heavy", false, "Set whether Heavy is a treasure enchantment or not");
		Property hi = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".highjump", false, "Set whether High Jump is a treasure enchantment or not");
		Property mu = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".multishot", false, "Set whether Multishot is a treasure enchantment or not");
		Property pe = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".penetration", false, "Set whether Penetration is a treasure enchantment or not");
		Property ra = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".range", false, "Set whether Range is a treasure enchantment or not");
		Property rf = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".rapidfire", false, "Set whether Rapid Fire is a treasure enchantment or not");
		Property re = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".reflection", false, "Set whether Reflection is a treasure enchantment or not");
		Property sm = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".smelting", false, "Set whether Smelting is a treasure enchantment or not");
		Property sp = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".spellshield", false, "Set whether Spellshield is a treasure enchantment or not");
		Property tu = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".tunneling", false, "Set whether Tunneling is a treasure enchantment or not");
		Property va = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".vampirism", false, "Set whether Vampirism is a treasure enchantment or not");
		Property ve = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".versatility", false, "Set whether Versatility is a treasure enchantment or not");
		Property vi = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".vitality", false, "Set whether Vitality is a treasure enchantment or not");
		Property we = config.get(IS_TREASURE, "enchantment." + Reference.MOD_ID + ".weightless", false, "Set whether Weightless is a treasure enchantment or not");
		
		Property coS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.copper_stats", new double[]{1, 160, 5.0f, 1.0f, 5}, "Set copper gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		coS.setRequiresMcRestart(true);
		Property brS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.bronze_stats", new double[]{2, 200, 6.0f, 1.8f, 14}, "Set bronze gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		brS.setRequiresMcRestart(true);
		Property inS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.invar_stats", new double[]{2, 250, 6.5f, 2.1f, 10}, "Set invar gear stats (harvest level, durability, efficiency, damage, enchantability)");
		inS.setRequiresMcRestart(true);
		Property siS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.silver_stats", new double[]{0, 32, 12.0f, 0.5f, 22}, "Set silver gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		siS.setRequiresMcRestart(true);
		Property elS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.electrum_stats", new double[]{0, 32, 15.0f, 0.6f, 35}, "Set electrum gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		elS.setRequiresMcRestart(true);
		Property alS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.aluminium_stats", new double[]{2, 220, 12.0f, 1.8f, 14}, "Set aluminium gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		alS.setRequiresMcRestart(true);
		Property stS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.steel_stats", new double[]{2, 350, 6.5f, 2.5f, 14}, "Set steel gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		stS.setRequiresMcRestart(true);
		Property sgS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.signalum_stats", new double[]{2, 500, 10.0f, 2.0f, 14}, "Set signalum gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		sgS.setRequiresMcRestart(true);
		Property luS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.lumium_stats", new double[]{2, 600, 12.0f, 2.5f, 14}, "Set lumium gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		luS.setRequiresMcRestart(true);
		Property enS = config.get(MATERIAL_STATS, Reference.MOD_ID + ".configgui.enderium_stats", new double[]{3, 1000, 10.0f, 4.0f, 20}, "Set enderium gear base stats (harvest level, durability, efficiency, damage, enchantability)");
		enS.setRequiresMcRestart(true);
		
		Property fov = config.get(OTHER, Reference.MOD_ID + ".configgui.fov", true, "When set to true, your movement speed won't effect your field of view.");
		Property cmi = config.get(OTHER, Reference.MOD_ID + ".configgui.integration", true, "When set to true, you can craft weapons from materials from other mods");
		cmi.setRequiresMcRestart(true);
		Property avs = config.get(OTHER, Reference.MOD_ID + ".configgui.allow_vanilla_shields", true, "When set flase, vanilla shield recipe is disabled forcing player to use this mod's shields");
		avs.setRequiresMcRestart(true);
		
		if (readFields)
		{
			assassinatelevel = asL.getInt();
			agilitylevel = agL.getInt();
			arrowrecoverylevel = arL.getInt();
			blastlevel = blL.getInt();
			blockpowerlevel =bpL.getInt();
			bashlevel = baL.getInt();
			combolevel = coL.getInt();
			diamondslevel = deL.getInt();
			disarmlevel = diL.getInt();
			educationlevel = edL.getInt();
			flinglevel = flL.getInt();
			heavylevel = heL.getInt();
			highjumplevel = hiL.getInt();
			multishotlevel = muL.getInt();
			penetrationlevel = peL.getInt();
			rangelevel = raL.getInt();
			rapidfirelevel = rfL.getInt();
			reflectionlevel = reL.getInt();
			smeltinglevel = smL.getInt();
			spellshieldlevel = spL.getInt();
			tunnelinglevel = tuL.getInt();
			vampirismlevel = vaL.getInt();
			versatilitylevel = veL.getInt();
			vitalitylevel = viL.getInt();
			weightlesslevel = weL.getInt();
			
			assassinate = as.getBoolean();
			agility = ag.getBoolean();
			arrowrecovery = ar.getBoolean();
			blast = bl.getBoolean();
			blockpower =bp.getBoolean();
			bash = ba.getBoolean();
			combo = co.getBoolean();
			diamonds = de.getBoolean();
			disarm = di.getBoolean();
			education = ed.getBoolean();
			fling = fl.getBoolean();
			heavy = he.getBoolean();
			highjump = hi.getBoolean();
			multishot = mu.getBoolean();
			penetration = pe.getBoolean();
			range = ra.getBoolean();
			rapidfire = rf.getBoolean();
			reflection = re.getBoolean();
			smelting = sm.getBoolean();
			spellshield = sp.getBoolean();
			tunneling = tu.getBoolean();
			vampirism = va.getBoolean();
			versatility = ve.getBoolean();
			vitality = vi.getBoolean();
			weightless = we.getBoolean();
			
			copperStats = (ArrayList<Double>) Arrays.stream(coS.getDoubleList()).boxed().collect(Collectors.toList());
			bronzeStats = (ArrayList<Double>) Arrays.stream(brS.getDoubleList()).boxed().collect(Collectors.toList());
			invarStats = (ArrayList<Double>) Arrays.stream(inS.getDoubleList()).boxed().collect(Collectors.toList());
			silverStats = (ArrayList<Double>) Arrays.stream(siS.getDoubleList()).boxed().collect(Collectors.toList());
			electrumStats = (ArrayList<Double>) Arrays.stream(elS.getDoubleList()).boxed().collect(Collectors.toList());
			aluminiumStats = (ArrayList<Double>) Arrays.stream(alS.getDoubleList()).boxed().collect(Collectors.toList());
			steelStats = (ArrayList<Double>) Arrays.stream(stS.getDoubleList()).boxed().collect(Collectors.toList());
			signalumStats = (ArrayList<Double>) Arrays.stream(sgS.getDoubleList()).boxed().collect(Collectors.toList());
			lumiumStats = (ArrayList<Double>) Arrays.stream(luS.getDoubleList()).boxed().collect(Collectors.toList());
			enderiumStats = (ArrayList<Double>) Arrays.stream(enS.getDoubleList()).boxed().collect(Collectors.toList());
			
			FOV = fov.getBoolean();
			integration = cmi.getBoolean();
			allowvanillashields = avs.getBoolean();
		}
		
		if (config.hasChanged())
		{
			config.save();
		}
	}

}
