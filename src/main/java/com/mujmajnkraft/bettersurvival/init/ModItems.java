package com.mujmajnkraft.bettersurvival.init;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemBattleAxe;
import com.mujmajnkraft.bettersurvival.items.ItemCrossbow;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemDebug;
import com.mujmajnkraft.bettersurvival.items.ItemHammer;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static Item debug;
	
	public static ItemHammer hammer;
	public static ItemBattleAxe battleaxe;
	public static ItemSpear spear;
	public static ItemDagger dagger;
	public static ItemNunchaku nunchaku;
	public static ItemCrossbow crossbow;
	public static Item smallshield;
	public static Item bigshield;
	
	public static ToolMaterial COPPER = EnumHelper.addToolMaterial((String)"Copper", (int)1, (int)160, (float)5.0f, (float)1.0f, (int)5);
	public static ToolMaterial BRONZE = EnumHelper.addToolMaterial((String)"Bronze", (int)2, (int)200, (float)6.0f, (float)1.8f, (int)14);
	public static ToolMaterial INVAR = EnumHelper.addToolMaterial((String)"Invar", (int)2, (int)250, (float)6.5f, (float)2.1f, (int)10);
	public static ToolMaterial SILVER = EnumHelper.addToolMaterial((String)"Silver", (int)0, (int)32, (float)12.0f, (float)0.5f, (int)22);
	public static ToolMaterial ELECTRUM = EnumHelper.addToolMaterial((String)"Electrum", (int)0, (int)32, (float)15.0f, (float)0.6f, (int)35);
	public static ToolMaterial ALUMINIUM = EnumHelper.addToolMaterial((String)"Aluminium", (int)2, (int)220, (float)12.0f, (float)1.8f, (int)14);
	public static ToolMaterial STEEL = EnumHelper.addToolMaterial((String)"Steel", (int)2, (int)350, (float)6.5f, (float)2.5f, (int)14);
	public static ToolMaterial SIGNALUM = EnumHelper.addToolMaterial((String)"Signalum", (int)2, (int)500, (float)10.0f, (float)2.0f, (int)14);
	public static ToolMaterial LUMIUM = EnumHelper.addToolMaterial((String)"Lumium", (int)2, (int)600, (float)12.0f, (float)2.5f, (int)14);
	public static ToolMaterial ENDERIUM = EnumHelper.addToolMaterial((String)"Enderium", (int)3, (int)1000, (float)10.0f, (float)4.0f, (int)20);
	
	public static List<ToolMaterial> materials = new ArrayList<ToolMaterial>(EnumSet.of(COPPER, BRONZE, INVAR, SILVER, ELECTRUM, ALUMINIUM, STEEL, SIGNALUM, LUMIUM, ENDERIUM,
									ToolMaterial.WOOD, ToolMaterial.STONE, ToolMaterial.IRON, ToolMaterial.GOLD, ToolMaterial.DIAMOND));
	public static List<ItemDagger> daggers = new ArrayList<ItemDagger>();
	public static List<ItemHammer> hammers = new ArrayList<ItemHammer>();
	public static List<ItemSpear> spears = new ArrayList<ItemSpear>();
	public static List<ItemBattleAxe> battleaxes = new ArrayList<ItemBattleAxe>();
	public static List<ItemNunchaku> nunchakus = new ArrayList<ItemNunchaku>();
	
	public static void init() 
	{
		debug = new ItemDebug();
		crossbow = new ItemCrossbow();
		smallshield = new ItemCustomShield(0.5F, 1);
		smallshield.setRegistryName("ItemSmallShield");
		smallshield.setUnlocalizedName("smallshield");
		bigshield = new ItemCustomShield(0.8F, 3);
		bigshield.setRegistryName("ItemBigShield");
		bigshield.setUnlocalizedName("bigshield");
		
		for (ToolMaterial material:materials)
		{
			hammer = new ItemHammer(material);
			hammers.add(hammer);
			battleaxe = new ItemBattleAxe(material);
			battleaxes.add(battleaxe);
			spear = new ItemSpear(material);
			spears.add(spear);
			dagger = new ItemDagger(material);
			daggers.add(dagger);
			nunchaku = new ItemNunchaku(material);
			nunchakus.add(nunchaku);
		};
	}
	
	public static void register() {
		GameRegistry.register(debug);
		GameRegistry.register(crossbow);
		GameRegistry.register(smallshield);
		GameRegistry.register(bigshield);
		
		for (Item item : hammers)
		{
			GameRegistry.register(item);
		}
		for (Item item : spears)
		{
			GameRegistry.register(item);
		}
		for (Item item : battleaxes)
		{
			GameRegistry.register(item);
		}
		for (Item item : daggers)
		{
			GameRegistry.register(item);
		}
		for (Item item : nunchakus)
		{
			GameRegistry.register(item);
		}
	}
	
	public static void registerRenders() {
		registerRender(debug);
		registerRender(crossbow);
		registerRender(smallshield);
		registerRender(bigshield);
		
		for (Item item : hammers)
		{
			registerRender(item);
		}
		for (Item item : spears)
		{
			registerRender(item);
		}
		for (Item item : battleaxes)
		{
			registerRender(item);
		}
		for (Item item : daggers)
		{
			registerRender(item);
		}
		for (Item item : nunchakus)
		{
			registerRender(item);
		}
	}
	
	private static void registerRender(Item item) 
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}
