package com.mujmajnkraft.bettersurvival;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BetterSurvivalPacketHandler {
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("bettersurvival");
	
	public static void init()
	{
		int id = 0;
		NETWORK.registerMessage(MessageExtendedReachAttack.Handler.class, MessageExtendedReachAttack.class, id++, Side.SERVER);
		NETWORK.registerMessage(MessageNunchakuSpinClient.Handler.class, MessageNunchakuSpinClient.class, id++, Side.SERVER);
		NETWORK.registerMessage(MessageNunchakuSpinServer.Handler.class, MessageNunchakuSpinServer.class, id++, Side.CLIENT);
	}

}
