package com.mujmajnkraft.bettersurvival.config;

import com.mujmajnkraft.bettersurvival.Reference;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigEventHandler {
	
	@SubscribeEvent
	public void OnConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID()==Reference.MOD_ID)
		{
			ConfigHandler.syncFromGui();
		}
	}

}
