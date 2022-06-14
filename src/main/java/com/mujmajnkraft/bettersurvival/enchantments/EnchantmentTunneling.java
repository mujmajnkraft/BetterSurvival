package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentTunneling extends Enchantment {

	public EnchantmentTunneling() {
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("tunneling");
		this.setName(Reference.MOD_ID + ".tunneling");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 25 + (enchantmentLevel - 1) * 15;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.tunnelinglevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.tunneling;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.tunnelinglevel != 0;
    }
}
