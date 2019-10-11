package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.IsConfigEnabled;

import net.minecraft.world.storage.loot.conditions.LootConditionManager;

public class ModLootManager {
	
	//This is still WIP. It does not work in this version.
	public static void register()
	{
		LootConditionManager.registerCondition(new IsConfigEnabled.Serializer());
	}

}
