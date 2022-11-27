package com.mujmajnkraft.bettersurvival;

import java.io.File;

import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowProperties;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.SpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.WeaponEffect;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.eventhandlers.CommonEventHandler;
import com.mujmajnkraft.bettersurvival.eventhandlers.TickEventHandler;
import com.mujmajnkraft.bettersurvival.init.ModCrafting;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModEntities;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.init.ModPotionTypes;
import com.mujmajnkraft.bettersurvival.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, 
	version = Reference.MOD_VERSION, 
	acceptedMinecraftVersions = Reference.MC_VERSION)
public class Bettersurvival {
	
	@Instance
	public static Bettersurvival instance;
	
	public static File config;
	
	public static boolean isIafLoaded;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		isIafLoaded = Loader.isModLoaded("iceandfire");
		
		proxy.preInit();
		
		ArrowProperties.Register();
		NunchakuCombo.Register();
		SpearsIn.Register();
		WeaponEffect.Register();
		
		config = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
		config.mkdirs();
		ConfigHandler.init(new File(config.getPath(), Reference.MOD_ID + ".cfg"));
		
		ModPotions.init();
		
		MinecraftForge.EVENT_BUS.register(new ModItems());
		
		//MinecraftForge.EVENT_BUS.register(new ModBlocks());
		MinecraftForge.EVENT_BUS.register(new ModPotionTypes());
		MinecraftForge.EVENT_BUS.register(new ModPotions());
		
		MinecraftForge.EVENT_BUS.register(new ModEnchantments());
		
		ModEntities.registerEntities();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		proxy.init();
		BetterSurvivalPacketHandler.init();
		
		ModItems.setRepairMaterials();
		
		//MinecraftForge.EVENT_BUS.register(new ModWeaponHandler());
		//MinecraftForge.EVENT_BUS.register(new ModEnchantmentHandler());
		//MinecraftForge.EVENT_BUS.register(new ModLootHandler());
		//MinecraftForge.EVENT_BUS.register(new ModShieldHandler());
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		FMLCommonHandler.instance().bus().register(new TickEventHandler());	
		
		//GameRegistry.registerTileEntity(TileEntityCustomCauldron.class, Reference.MOD_ID + ":customcauldron");
		
		ModCrafting.register();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
	}
}
