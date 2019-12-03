package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.blocks.BlockCustomCauldron;
import com.mujmajnkraft.bettersurvival.blocks.BlockWorkshop;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModBlocks {
	
	public static Block customcauldron;
	//public static Block workshop;
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		customcauldron = new BlockCustomCauldron();
		//workshop = new BlockWorkshop();
		
		customcauldron.setRegistryName("customcauldron");
		customcauldron.setUnlocalizedName("Cauldron");
		event.getRegistry().register(customcauldron);
		/*
		workshop.setRegistryName("Workshop");
		workshop.setUnlocalizedName("workshop");
		event.getRegistry().register(workshop);
	}
	
	@SubscribeEvent
	public void registerBlockItems(RegistryEvent.Register<Item> event)
	{
		ItemBlock itemworkshop = new ItemBlock(workshop);
		itemworkshop.setRegistryName(workshop.getRegistryName());
		itemworkshop.setUnlocalizedName(workshop.getUnlocalizedName());
		itemworkshop.setCreativeTab(ModItems.siegeweapons);
		event.getRegistry().register(itemworkshop);*/
	}
	
	public static void registerRenders()
	{
		registerRender(customcauldron);
		//registerRender(workshop);
	}
	
	private static void registerRender(Block block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

}
