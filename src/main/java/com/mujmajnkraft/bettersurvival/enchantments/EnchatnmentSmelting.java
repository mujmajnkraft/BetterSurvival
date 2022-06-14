package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchatnmentSmelting extends Enchantment {
	public EnchatnmentSmelting() {
		super(Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("smelting");
		this.setName(Reference.MOD_ID + ".smelting");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 30;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.smeltinglevel;
    }

    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.SILK_TOUCH;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.smelting;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.smeltinglevel != 0;
    }
}
