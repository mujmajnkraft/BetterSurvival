package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentHighJump extends Enchantment {

	public EnchantmentHighJump() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
		this.setRegistryName("highjump");
		this.setName(Reference.MOD_ID + ".highjump");
	}
	
	@Override
	public int getMaxLevel()
    {
        return ConfigHandler.highjumplevel;
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
    	if (ConfigHandler.highjump)
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
		if (ConfigHandler.highjumplevel == 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
