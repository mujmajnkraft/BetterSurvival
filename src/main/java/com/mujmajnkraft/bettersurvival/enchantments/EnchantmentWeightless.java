package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
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
		this.setName(Reference.MOD_ID + ".weightless");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

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
    	return ConfigHandler.weightless;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
    	
    	return stack.getItem() instanceof ItemCustomShield;
	}
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.weightlesslevel != 0;
    }
}
