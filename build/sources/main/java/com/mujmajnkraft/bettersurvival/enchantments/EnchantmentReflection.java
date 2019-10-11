package com.mujmajnkraft.bettersurvival.enchantments;

import java.util.Random;

import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentReflection extends Enchantment {
	
	public EnchantmentReflection() {
		super(Rarity.RARE, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND});
		this.setRegistryName("reflection");
		this.setName("reflection");
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
        return ConfigHandler.reflectionlevel;
    }
    
    public static boolean shouldHit(int level, Random rnd)
    {
        return level <= 0 ? false : rnd.nextFloat() < 0.15F * (float)level;
    }

    public static int getDamage(int level, Random rnd)
    {
        return level > 10 ? level - 10 : 1 + rnd.nextInt(4);
    }
    
    public boolean isTreasureEnchantment()
    {
    	if (ConfigHandler.reflection)
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
		if (ConfigHandler.reflectionlevel == 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
