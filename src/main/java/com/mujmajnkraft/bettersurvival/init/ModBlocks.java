package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.blocks.BlockCustomCauldron;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static Block customcauldron;
	
	public static void init()
	{
		customcauldron = new BlockCustomCauldron();
		
		customcauldron.setRegistryName("customcauldron");
		customcauldron.setUnlocalizedName("Cauldron");
	}
	
	public static void register()
	{
		registerBlock(customcauldron);
	}
	
	private static void registerBlock(Block block)
	{
		GameRegistry.register(block);
		ItemBlock item = new ItemBlock(customcauldron);
		item.setRegistryName(block.getRegistryName());
		item.setUnlocalizedName(block.getUnlocalizedName());
		GameRegistry.register(item);
	}
	
	public static void registerRenders()
	{
		registerRender(customcauldron);
	}
	
	private static void registerRender(Block block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

}
