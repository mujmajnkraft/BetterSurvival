package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentWeightless extends Enchantment {
	public EnchantmentWeightless () {
		super(Rarity.UNCOMMON, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND});
		this.setRegistryName("weightless");
		this.setName("weightless");
	}
	
	/**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
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
        return ConfigHandler.weightlesslevel;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != ModEnchantments.heavy;
    }
    
    public boolean isTreasureEnchantment()
    {
    	if (ConfigHandler.weightless)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
    	
    	if (stack.getItem() instanceof ItemCustomShield)
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
		if (ConfigHandler.weightlesslevel == 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
