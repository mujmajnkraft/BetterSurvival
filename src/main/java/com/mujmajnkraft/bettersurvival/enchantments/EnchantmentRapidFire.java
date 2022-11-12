package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentRapidFire extends Enchantment {
	public EnchantmentRapidFire() {
		super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("rapidfire");
		this.setName(Reference.MOD_ID + ".rapidfire");
	}
	
	//Called during LivingEntityUseItemEvent.Tick if the item used is an enchanted bow
	public static int getChargeTimeReduction(EntityLivingBase shooter, int charge)
	{
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, shooter) < 4)
		{
			return (charge%(5-EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, shooter)) == 0) ? 1 : 0;
		}
		else
		{
			return EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, shooter) - 3;
		}
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
