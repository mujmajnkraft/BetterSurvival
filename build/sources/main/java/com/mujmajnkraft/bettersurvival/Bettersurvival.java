package com.mujmajnkraft.bettersurvival;

import java.io.File;

import com.mujmajnkraft.bettersurvival.capabilities.entityspeed.EntitySpeed;
import com.mujmajnkraft.bettersurvival.capabilities.entityspeed.EntitySpeedStorage;
import com.mujmajnkraft.bettersurvival.capabilities.entityspeed.IEntitySpeed;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowProperties;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesStorage;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.IArrowProperties;
import com.mujmajnkraft.bettersurvival.capabilities.spearsin.ISpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsin.SpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsin.SpearsInStorage;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.IWeaponEffect;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.WeaponEffect;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.WeaponEffectStorage;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, 
	version = Reference.MOD_VERSION, 
	acceptedMinecraftVersions = Reference.MC_VERSIONS,
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
		System.out.println("PreInit");
		
		CapabilityManager.INSTANCE.register(ISpearsIn.class, new SpearsInStorage(), SpearsIn.class);
		CapabilityManager.INSTANCE.register(IArrowProperties.class, new ArrowPropertiesStorage(), ArrowProperties.class);
		CapabilityManager.INSTANCE.register(IEntitySpeed.class, new EntitySpeedStorage(), EntitySpeed.class);
		CapabilityManager.INSTANCE.register(IWeaponEffect.class, new WeaponEffectStorage(), WeaponEffect.class);
		
		config = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
		config.mkdirs();
		ConfigHandler.init(new File(config.getPath(), Reference.MOD_ID + ".cfg"));
		ModLootManager.register();
		
		ModPotions.init();
		ModPotions.register();
		
		ModItems.init();
		ModItems.register();
		
		ModBlocks.init();
		ModBlocks.register();
		
		ModPotionTypes.init();
		ModPotionTypes.register();
		
		ModEnchantments.init();
		ModEnchantments.register();
		
		ModEntities.registerEntities();
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		System.out.println("Init");
		proxy.init();

		MinecraftForge.EVENT_BUS.register(new ModWeaponHandler());
		MinecraftForge.EVENT_BUS.register(new ModEnchantmentHandler());
		MinecraftForge.EVENT_BUS.register(new ModLootHandler());
		MinecraftForge.EVENT_BUS.register(new ModShieldHandler());
		FMLCommonHandler.instance().bus().register(new TickEventHandler());
		
		GameRegistry.registerTileEntity(TileEntityCustomCauldron.class, Reference.MOD_ID + "customcauldron");
		
		ModCrafting.register();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		System.out.println("PostInit");/*
		proxy.postinit();*/
	}

}
