package com.mujmajnkraft.bettersurvival.proxy;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.MessageButtonPressed;
import com.mujmajnkraft.bettersurvival.MessageExtendedReachAttack;
import com.mujmajnkraft.bettersurvival.MessageUseWeapon;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.client.ModClientHandler;
import com.mujmajnkraft.bettersurvival.client.render.CustomBlockColor;
import com.mujmajnkraft.bettersurvival.client.render.RenderBallista;
import com.mujmajnkraft.bettersurvival.client.render.RenderBallistaBolt;
import com.mujmajnkraft.bettersurvival.client.render.RenderCannon;
import com.mujmajnkraft.bettersurvival.client.render.RenderCannonball;
import com.mujmajnkraft.bettersurvival.client.render.RenderFlyingSpear;
import com.mujmajnkraft.bettersurvival.client.render.RenderPotionThrower;
import com.mujmajnkraft.bettersurvival.client.render.RenderZeppelin;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityBallistaBolt;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityCannonball;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityBallista;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityCannon;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityPotionThrower;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityZeppelin;
import com.mujmajnkraft.bettersurvival.init.ModBlocks;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy implements CommonProxy{

	@Override
	public void init() {
		
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

		Bettersurvival.network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID + "network");

		int packetId = 0;
		// register messages from client to server
		Bettersurvival.network.registerMessage(MessageExtendedReachAttack.Handler.class, MessageExtendedReachAttack.class, packetId++, Side.SERVER);
		/*Bettersurvival.network.registerMessage(MessageUseWeapon.Handler.class, MessageUseWeapon.class, packetId++, Side.SERVER);
		Bettersurvival.network.registerMessage(MessageButtonPressed.Handler.class, MessageButtonPressed.class, packetId++, Side.SERVER);*/
	}
}
