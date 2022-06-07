package com.mujmajnkraft.bettersurvival.proxy;

import com.mujmajnkraft.bettersurvival.client.ModClientHandler;
import com.mujmajnkraft.bettersurvival.client.render.CustomBlockColor;
import com.mujmajnkraft.bettersurvival.client.render.RenderFlyingSpear;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;
import com.mujmajnkraft.bettersurvival.init.ModBlocks;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy implements CommonProxy{
	
	@Override
	public void preInit()
	{
		ConfigHandler.clientPreInit();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init()
	{
		
		ModItems.registerRenders();
		ModBlocks.registerRenders();
		CustomBlockColor.registerBlockColors();

		FMLCommonHandler.instance().bus().register(new ModClientHandler());
		
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingSpear.class, new RenderFlyingSpear(manager));
	}
}
