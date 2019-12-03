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
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingSpear.class, new RenderFlyingSpear(manager));/*
		RenderingRegistry.registerEntityRenderingHandler(EntityBallista.class, new RenderBallista(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityBallistaBolt.class, new RenderBallistaBolt(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityZeppelin.class, new RenderZeppelin(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityPotionThrower.class, new RenderPotionThrower(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, new RenderCannon(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityCannonball.class, new RenderCannonball(manager));*/

		
		/*Bettersurvival.network.registerMessage(MessageUseWeapon.Handler.class, MessageUseWeapon.class, packetId++, Side.SERVER);
		Bettersurvival.network.registerMessage(MessageButtonPressed.Handler.class, MessageButtonPressed.class, packetId++, Side.SERVER);*/
	}
}
