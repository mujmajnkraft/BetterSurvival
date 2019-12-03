package com.mujmajnkraft.bettersurvival.init;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemBattleAxe;
import com.mujmajnkraft.bettersurvival.items.ItemCrossbow;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemHammer;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModItems {
	
	public static ItemHammer hammer;
	public static ItemBattleAxe battleaxe;
	public static ItemSpear spear;
	public static ItemDagger dagger;
	public static ItemNunchaku nunchaku;
	public static ItemCrossbow crossbow;
	public static Item smallshield;
	public static Item bigshield;
	
	public static Item ballistabolt;
	public static Item cannonball;
	public static Item cannonballexplosive;
	public static Item cannonballburning;
	public static Item ballistaplacer;
	public static Item throwerplacer;
	public static Item cannonplacer;
	public static Item zeppelinplacer;
	public static Item blueprint;
	/*
	public static final CreativeTabs siegeweapons = new CreativeTabs(Reference.MOD_ID+"siegeweapons") {
		
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ballistaplacer);
		}
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_)
		{
			List<ItemStack> list = WorkshopRecipe.instance().getBlueprints();
			p_78018_1_.addAll(list);
			super.displayAllRelevantItems(p_78018_1_);
		}
	};*/
	
	public static ToolMaterial COPPER = EnumHelper.addToolMaterial("Copper", ConfigHandler.copperStats.get(0).intValue(), ConfigHandler.copperStats.get(1).intValue(), ConfigHandler.copperStats.get(2).floatValue(), ConfigHandler.copperStats.get(3).floatValue(), ConfigHandler.copperStats.get(4).intValue());
	public static ToolMaterial BRONZE = EnumHelper.addToolMaterial("Bronze", ConfigHandler.bronzeStats.get(0).intValue(), ConfigHandler.bronzeStats.get(1).intValue(), ConfigHandler.bronzeStats.get(2).floatValue(), ConfigHandler.bronzeStats.get(3).floatValue(), ConfigHandler.bronzeStats.get(4).intValue());
	public static ToolMaterial INVAR = EnumHelper.addToolMaterial("Invar", ConfigHandler.invarStats.get(0).intValue(), ConfigHandler.invarStats.get(1).intValue(), ConfigHandler.invarStats.get(2).floatValue(), ConfigHandler.invarStats.get(3).floatValue(), ConfigHandler.invarStats.get(4).intValue());
	public static ToolMaterial SILVER = EnumHelper.addToolMaterial("Silver", ConfigHandler.silverStats.get(0).intValue(), ConfigHandler.silverStats.get(1).intValue(), ConfigHandler.silverStats.get(2).floatValue(), ConfigHandler.silverStats.get(3).floatValue(), ConfigHandler.silverStats.get(4).intValue());
	public static ToolMaterial ELECTRUM = EnumHelper.addToolMaterial("Electrum", ConfigHandler.electrumStats.get(0).intValue(), ConfigHandler.electrumStats.get(1).intValue(), ConfigHandler.electrumStats.get(2).floatValue(), ConfigHandler.electrumStats.get(3).floatValue(), ConfigHandler.electrumStats.get(4).intValue());
	public static ToolMaterial ALUMINIUM = EnumHelper.addToolMaterial("Aluminium", ConfigHandler.aluminiumStats.get(0).intValue(), ConfigHandler.aluminiumStats.get(1).intValue(), ConfigHandler.aluminiumStats.get(2).floatValue(), ConfigHandler.aluminiumStats.get(3).floatValue(), ConfigHandler.aluminiumStats.get(4).intValue());
	public static ToolMaterial STEEL = EnumHelper.addToolMaterial("Steel", ConfigHandler.steelStats.get(0).intValue(), ConfigHandler.steelStats.get(1).intValue(), ConfigHandler.steelStats.get(2).floatValue(), ConfigHandler.steelStats.get(3).floatValue(), ConfigHandler.steelStats.get(4).intValue());
	public static ToolMaterial SIGNALUM = EnumHelper.addToolMaterial("Signalum", ConfigHandler.signalumStats.get(0).intValue(), ConfigHandler.signalumStats.get(1).intValue(), ConfigHandler.signalumStats.get(2).floatValue(), ConfigHandler.signalumStats.get(3).floatValue(), ConfigHandler.signalumStats.get(4).intValue());
	public static ToolMaterial LUMIUM = EnumHelper.addToolMaterial("Lumium", ConfigHandler.lumiumStats.get(0).intValue(), ConfigHandler.lumiumStats.get(1).intValue(), ConfigHandler.lumiumStats.get(2).floatValue(), ConfigHandler.lumiumStats.get(3).floatValue(), ConfigHandler.lumiumStats.get(4).intValue());
	public static ToolMaterial ENDERIUM = EnumHelper.addToolMaterial("Enderium", ConfigHandler.enderiumStats.get(0).intValue(), ConfigHandler.enderiumStats.get(1).intValue(), ConfigHandler.enderiumStats.get(2).floatValue(), ConfigHandler.enderiumStats.get(3).floatValue(), ConfigHandler.enderiumStats.get(4).intValue());
	public static ToolMaterial DRAGON_BONE = EnumHelper.addToolMaterial("Dragonbone", 4, 1660, 10.0F, 4.0F, 22);
	public static ToolMaterial JUNGLE_CHITIN = EnumHelper.addToolMaterial("JungleChitin", 3, 600, 6.0F, -1.0F, 8);
	public static ToolMaterial DESERT_CHITIN = EnumHelper.addToolMaterial("DesertChitin", 3, 600, 6.0F, -1.0F, 8);
	
	public static List<ToolMaterial> materials = new ArrayList<ToolMaterial>(EnumSet.of(COPPER, BRONZE, INVAR, SILVER, ELECTRUM, ALUMINIUM, STEEL, SIGNALUM, LUMIUM, ENDERIUM, DRAGON_BONE, JUNGLE_CHITIN, DESERT_CHITIN,
									ToolMaterial.WOOD, ToolMaterial.STONE, ToolMaterial.IRON, ToolMaterial.GOLD, ToolMaterial.DIAMOND));
	public static List<ItemDagger> daggers = new ArrayList<ItemDagger>();
	public static List<ItemHammer> hammers = new ArrayList<ItemHammer>();
	public static List<ItemSpear> spears = new ArrayList<ItemSpear>();
	public static List<ItemBattleAxe> battleaxes = new ArrayList<ItemBattleAxe>();
	public static List<ItemNunchaku> nunchakus = new ArrayList<ItemNunchaku>();
	
	public static void setRepairMaterials()
	{
		if (Bettersurvival.isIafLoaded)
		{
			if (Item.getByNameOrId("iceandfire:dragonbone") != null)
			{
				ModItems.DRAGON_BONE.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:dragonbone")));
			}
			if (Item.getByNameOrId("iceandfire:myrmex_desert_chitin") != null)
			{
				ModItems.DESERT_CHITIN.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_desert_chitin")));
			}
			if (Item.getByNameOrId("iceandfire:myrmex_jungle_chitin") != null)
			{
				ModItems.JUNGLE_CHITIN.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_jungle_chitin")));
			}
		}
	}
	
	@SubscribeEvent
	public void regiserItems(RegistryEvent.Register<Item> event)
	{
		crossbow = new ItemCrossbow();
		smallshield = new ItemCustomShield(0.5F, 1);
		smallshield.setRegistryName("ItemSmallShield");
		smallshield.setUnlocalizedName("smallshield");
		bigshield = new ItemCustomShield(0.8F, 3);
		bigshield.setRegistryName("ItemBigShield");
		bigshield.setUnlocalizedName("bigshield");/*
		ballistabolt = new ItemAmmo(1);
		ballistabolt.setRegistryName("Cannonball");
		ballistabolt.setUnlocalizedName("cannonball");
		cannonball = new ItemAmmo(2);
		cannonball.setRegistryName("Cannonball_Explosive");
		cannonball.setUnlocalizedName("cannonballexplosive");
		cannonballexplosive = new ItemAmmo(3);
		cannonballexplosive.setRegistryName("Cannonball_Burning");
		cannonballexplosive.setUnlocalizedName("cannonballburning");
		cannonballburning = new ItemAmmo(4);
		cannonballburning.setRegistryName("ItemBallistaBolt");
		cannonballburning.setUnlocalizedName("ballistabolt");
		ballistaplacer = new ItemWeaponPlacer(EnumWeaponType.BALLISTA);
		throwerplacer = new ItemWeaponPlacer(EnumWeaponType.POTION_THROWER);
		cannonplacer = new ItemWeaponPlacer(EnumWeaponType.CANNON);
		zeppelinplacer = new ItemWeaponPlacer(EnumWeaponType.ZEPPELIN);
		blueprint = new ItemBlueprint();*/
		
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
		
		event.getRegistry().register(smallshield);
		event.getRegistry().register(crossbow);
		event.getRegistry().register(bigshield);/*
		event.getRegistry().register(ballistabolt);
		event.getRegistry().register(ballistaplacer);
		event.getRegistry().register(throwerplacer);
		event.getRegistry().register(cannonball);
		event.getRegistry().register(cannonballexplosive);
		event.getRegistry().register(cannonballburning);
		event.getRegistry().register(cannonplacer);
		event.getRegistry().register(zeppelinplacer);
		event.getRegistry().register(blueprint);*/
		
		for (Item item : hammers)
		{
			event.getRegistry().register(item);
		}
		for (Item item : spears)
		{
			event.getRegistry().register(item);
		}
		for (Item item : battleaxes)
		{
			event.getRegistry().register(item);
		}
		for (Item item : daggers)
		{
			event.getRegistry().register(item);
		}
		for (Item item : nunchakus)
		{
			event.getRegistry().register(item);
		}
	}
	
	public static void registerRenders() {
		registerRender(bigshield);
		registerRender(smallshield);
		registerRender(crossbow);/*
		registerRender(ballistabolt);
		registerRender(ballistaplacer);
		registerRender(throwerplacer);
		registerRender(cannonball);
		registerRender(cannonballexplosive);
		registerRender(cannonballburning);
		registerRender(cannonplacer);
		registerRender(zeppelinplacer);
		registerRender(blueprint);*/
		
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
