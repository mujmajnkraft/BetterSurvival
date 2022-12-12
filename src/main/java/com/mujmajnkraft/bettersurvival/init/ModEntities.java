package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	
	public static void registerEntities()
	{
		int id = 0;
		
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "EntityFlyingSpear"), EntityFlyingSpear.class, "EntityFlyingSpear", id++, BetterSurvival.instance, 64, 20, true);
	}
	
}
