package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentBlast extends Enchantment {

	public EnchantmentBlast() {
		super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("blast");
		this.setName(Reference.MOD_ID + ".blast");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 15 + (enchantmentLevel - 1) * 20;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.blastlevel;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.PUNCH && ench != ModEnchantments.multishot;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.blast;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.blastlevel != 0;
    }
}
