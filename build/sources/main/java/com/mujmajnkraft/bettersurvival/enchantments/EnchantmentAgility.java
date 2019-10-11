package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentAgility extends Enchantment {

	public EnchantmentAgility() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
		this.setRegistryName("agility");
		this.setName("agility");
	}
	
	@Override
	public int getMaxLevel()
    {
        return ConfigHandler.agilitylevel;
    }
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }
	
	public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
	
	public boolean isTreasureEnchantment()
    {
    	if (ConfigHandler.agility)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
	
	public boolean isAllowedOnBooks()
    {
		if (ConfigHandler.agilitylevel == 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
