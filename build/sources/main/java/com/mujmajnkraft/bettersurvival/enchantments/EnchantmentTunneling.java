package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentTunneling extends Enchantment {

	public EnchantmentTunneling() {
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("tunneling");
		this.setName("tunneling");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 25 + (enchantmentLevel - 1) * 15;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return ConfigHandler.tunnelinglevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	if (ConfigHandler.tunneling)
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
		if (ConfigHandler.tunnelinglevel == 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
