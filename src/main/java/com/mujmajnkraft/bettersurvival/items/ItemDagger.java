package com.mujmajnkraft.bettersurvival.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemDagger extends ItemCustomWeapon{
	
	public ItemDagger(ToolMaterial material) {
		super(material, 0.7F, 0.8F);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Dagger");
		this.setUnlocalizedName(material.name().toLowerCase()+"dagger");
	}
		
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		return super.hitEntity(stack, target, attacker);
	}

}
