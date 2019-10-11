package com.mujmajnkraft.bettersurvival.init;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.entities.EntityAirBallon;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityBallistaBolt;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityCannonball;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityBallista;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityCannon;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityPotionThrower;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityZeppelin;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	
	public static void registerEntities()
	{
		int id = 0;
		
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "EntityFlyingSpear"), EntityFlyingSpear.class, "EntityFlyingSpear", id++, Bettersurvival.instance, 64, 20, true);
		/*EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "EntityBallista"), EntityBallista.class, "EntityBallista", id++, Bettersurvival.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "EntityBallistaBolt"), EntityBallistaBolt.class, "EntityBallistaBolt", id++, Bettersurvival.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "EntityZeppelin"), EntityZeppelin.class, "EntityZeppelin", id++, Bettersurvival.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "EntityPotionThrower"), EntityPotionThrower.class, "EntityPotionThrower", id++, Bettersurvival.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "EntityCannon"), EntityCannon.class, "EntityCannon", id++, Bettersurvival.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "AirBallon"), EntityAirBallon.class, "airballon", id++, Bettersurvival.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "Cannonball"), EntityCannonball.class, "Cannonball", id++, Bettersurvival.instance, 64, 20, true);*/
	}
	
}
