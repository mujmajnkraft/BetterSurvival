package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentEducation extends Enchantment {
	public EnchantmentEducation() {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("education");
		this.setName(Reference.MOD_ID + ".education");
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
        return ConfigHandler.educationlevel;
    }

    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.LOOTING;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.education;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.educationlevel != 0;
    }
}
