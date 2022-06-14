package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentRapidFire extends Enchantment {
	public EnchantmentRapidFire() {
		super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("rapidfire");
		this.setName(Reference.MOD_ID + ".rapidfire");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 15 + (enchantmentLevel - 1) * 9;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.rapidfirelevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.rapidfire;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.rapidfirelevel != 0;
    }
}
