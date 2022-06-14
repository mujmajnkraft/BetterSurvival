package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentVersatility extends Enchantment {
	
	public EnchantmentVersatility() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("versatility");
		this.setName(Reference.MOD_ID + ".versatility");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 15;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.versatilitylevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.versatility;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.versatilitylevel != 0;
    }
}
