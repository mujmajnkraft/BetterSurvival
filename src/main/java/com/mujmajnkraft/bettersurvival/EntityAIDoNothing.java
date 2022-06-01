package com.mujmajnkraft.bettersurvival;

import com.mujmajnkraft.bettersurvival.init.ModPotions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIDoNothing extends EntityAIBase {
	
	EntityLiving entity;
	
	public EntityAIDoNothing(EntityLiving entity){
		this.entity = entity;
		this.setMutexBits(7);
	}

	@Override
	public boolean shouldExecute()
	{
		if (entity.getActivePotionEffect(ModPotions.stun) != null) System.out.println("Stun activated");
		return entity.getActivePotionEffect(ModPotions.stun) != null;
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		if (entity.getActivePotionEffect(ModPotions.stun) != null) System.out.println("Stun continues");
		return entity.getActivePotionEffect(ModPotions.stun) != null;
	}
	
	@Override
	public boolean isInterruptible()
	{
		return false;
	}

}
