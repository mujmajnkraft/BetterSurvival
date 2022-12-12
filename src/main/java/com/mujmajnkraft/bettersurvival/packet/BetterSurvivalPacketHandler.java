package com.mujmajnkraft.bettersurvival.packet;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BetterSurvivalPacketHandler {
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("bettersurvival");
	
	public static void init()
	{
		NETWORK.registerMessage(MessageNunchakuSpinClient.Handler.class, MessageNunchakuSpinClient.class, 0, Side.SERVER);
		NETWORK.registerMessage(MessageNunchakuSpinServer.Handler.class, MessageNunchakuSpinServer.class, 1, Side.CLIENT);
	}

}