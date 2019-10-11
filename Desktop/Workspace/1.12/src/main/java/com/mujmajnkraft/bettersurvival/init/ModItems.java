package com.mujmajnkraft.bettersurvival.init;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.WorkshopRecipe;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EnumWeaponType;
import com.mujmajnkraft.bettersurvival.items.ItemAmmo;
import com.mujmajnkraft.bettersurvival.items.ItemBattleAxe;
import com.mujmajnkraft.bettersurvival.items.ItemBlueprint;
import com.mujmajnkraft.bettersurvival.items.ItemCrossbow;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemHammer;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;
import com.mujmajnkraft.bettersurvival.items.ItemWeaponPlacer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
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
	
	public static ToolMaterial COPPER = EnumHelper.addToolMaterial("Copper", 1, 160, 5.0f, 1.0f, 5);
	public static ToolMaterial BRONZE = EnumHelper.addToolMaterial("Bronze", 2, 200, 6.0f, 1.8f, 14);
	public static ToolMaterial INVAR = EnumHelper.addToolMaterial("Invar", 2, 250, 6.5f, 2.1f, 10);
	public static ToolMaterial SILVER = EnumHelper.addToolMaterial("Silver", 0, 32, 12.0f, 0.5f, 22);
	public static ToolMaterial ELECTRUM = EnumHelper.addToolMaterial("Electrum", 0, 32, 15.0f, 0.6f, 35);
	public static ToolMaterial ALUMINIUM = EnumHelper.addToolMaterial("Aluminium", 2, 220, 12.0f, 1.8f, 14);
	public static ToolMaterial STEEL = EnumHelper.addToolMaterial("Steel", 2, 350, 6.5f, 2.5f, 14);
	public static ToolMaterial SIGNALUM = EnumHelper.addToolMaterial("Signalum", 2, 500, 10.0f, 2.0f, 14);
	public static ToolMaterial LUMIUM = EnumHelper.addToolMaterial("Lumium", 2, 600, 12.0f, 2.5f, 14);
	public static ToolMaterial ENDERIUM = EnumHelper.addToolMaterial("Enderium", 3, 1000, 10.0f, 4.0f, 20);
	
	public static List<ToolMaterial> materials = new ArrayList<ToolMaterial>(EnumSet.of(COPPER, BRONZE, INVAR, SILVER, ELECTRUM, ALUMINIUM, STEEL, SIGNALUM, LUMIUM, ENDERIUM,
									ToolMaterial.WOOD, ToolMaterial.STONE, ToolMaterial.IRON, ToolMaterial.GOLD, ToolMaterial.DIAMOND));
	public static List<ItemDagger> daggers = new ArrayList<ItemDagger>();
	public static List<ItemHammer> hammers = new ArrayList<ItemHammer>();
	public static List<ItemSpear> spears = new ArrayList<ItemSpear>();
	public static List<ItemBattleAxe> battleaxes = new ArrayList<ItemBattleAxe>();
	public static List<ItemNunchaku> nunchakus = new ArrayList<ItemNunchaku>();
	
	@SubscribeEvent
	public void regiserCusomRecipes(RegistryEvent.Register<IRecipe> event)
	{
		//event.getRegistry().register(new CustomShieldRecipe.Decoration().setRegistryName(Reference.MOD_ID, "customshielddecoration"));
		
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
