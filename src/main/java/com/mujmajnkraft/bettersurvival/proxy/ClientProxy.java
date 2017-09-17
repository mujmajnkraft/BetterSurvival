package com.mujmajnkraft.bettersurvival.proxy;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.MessageExtendedReachAttack;
import com.mujmajnkraft.bettersurvival.client.render.CustomBlockColor;
import com.mujmajnkraft.bettersurvival.client.render.RenderFlyingSpear;
import com.mujmajnkraft.bettersurvival.entities.EntityFlyingSpear;
import com.mujmajnkraft.bettersurvival.init.ModBlocks;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy implements CommonProxy{

	@Override
	public void init() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
		CustomBlockColor.registerBlockColors();
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingSpear.class, new RenderFlyingSpear(Minecraft.getMinecraft().getRenderManager()));

	Bettersurvival.network = NetworkRegistry.INSTANCE.newSimpleChannel("mujmajnkraftsnetwork");

	int packetId = 0;
	// register messages from client to server
	Bettersurvival.network.registerMessage(MessageExtendedReachAttack.Handler.class, 
      MessageExtendedReachAttack.class, packetId++, Side.SERVER);
	}
}
