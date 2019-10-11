package com.mujmajnkraft.bettersurvival;

import java.io.File;

import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowProperties;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.SpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.WeaponEffect;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.eventhandlers.ModEnchantmentHandler;
import com.mujmajnkraft.bettersurvival.eventhandlers.ModLootHandler;
import com.mujmajnkraft.bettersurvival.eventhandlers.ModShieldHandler;
import com.mujmajnkraft.bettersurvival.eventhandlers.ModWeaponHandler;
import com.mujmajnkraft.bettersurvival.eventhandlers.TickEventHandler;
import com.mujmajnkraft.bettersurvival.init.ModBlocks;
import com.mujmajnkraft.bettersurvival.init.ModCrafting;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModEntities;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.init.ModLootManager;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.init.ModPotionTypes;
import com.mujmajnkraft.bettersurvival.proxy.CommonProxy;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityCustomCauldron;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityWorkshop;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, 
	version = Reference.MOD_VERSION, 
	acceptedMinecraftVersions = Reference.MC_VERSION,
	guiFactory = "com.mujmajnkraft.bettersurvival.config.GuiFactoryBettersurvival")
public class Bettersurvival {
	
	@Instance
	public static Bettersurvival instance;
	
	public static File config;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		ArrowProperties.Register();
		NunchakuCombo.Register();
		SpearsIn.Register();
		WeaponEffect.Register();
		
		config = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
		config.mkdirs();
		ConfigHandler.init(new File(config.getPath(), Reference.MOD_ID + ".cfg"));
		ModLootManager.register();
		
		ModPotions.init();
		ModPotions.register();
		
		MinecraftForge.EVENT_BUS.register(new ModItems());
		
		MinecraftForge.EVENT_BUS.register(new ModBlocks());
		
		MinecraftForge.EVENT_BUS.register(new ModPotionTypes());
		
		MinecraftForge.EVENT_BUS.register(new ModEnchantments());
		
		ModEntities.registerEntities();
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		proxy.init();
		
		MinecraftForge.EVENT_BUS.register(new ModWeaponHandler());
		MinecraftForge.EVENT_BUS.register(new ModEnchantmentHandler());
		MinecraftForge.EVENT_BUS.register(new ModLootHandler());
		MinecraftForge.EVENT_BUS.register(new ModShieldHandler());
		FMLCommonHandler.instance().bus().register(new TickEventHandler());
		
		GameRegistry.registerTileEntity(TileEntityCustomCauldron.class, Reference.MOD_ID + "customcauldron");
		//GameRegistry.registerTileEntity(TileEntityWorkshop.class, Reference.MOD_ID + "workshop");
		
		//NetworkRegistry.INSTANCE.registerGuiHandler(Bettersurvival.instance, new ModGUIHandler());
		
		ModCrafting.register();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
	}
}
