package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowRecovery extends Enchantment {
	
	public EnchantmentArrowRecovery() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("arrowrecovery");
		this.setName("arrowrecovery");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + (enchantmentLevel - 1) * 9;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return ConfigHandler.arrowrecoverylevel;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.INFINITY;
    }
    
    public boolean isTreasureEnchantment()
    {
    	if (ConfigHandler.arrowrecovery)
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
		if (ConfigHandler.arrowrecoverylevel == 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }

}
