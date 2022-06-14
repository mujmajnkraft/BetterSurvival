package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowRecovery extends Enchantment {
	
	public EnchantmentArrowRecovery() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("arrowrecovery");
		this.setName(Reference.MOD_ID + ".arrowrecovery");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + (enchantmentLevel - 1) * 9;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

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
    	return ConfigHandler.arrowrecovery;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.arrowrecoverylevel != 0;
    }
}
