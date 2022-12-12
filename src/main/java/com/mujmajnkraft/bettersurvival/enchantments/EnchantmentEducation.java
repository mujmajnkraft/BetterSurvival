package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentEducation extends Enchantment {
	public EnchantmentEducation() {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("education");
		this.setName(Reference.MOD_ID + ".education");
	}
	
	// Called during LivingExperienceDropEvent if enemy is killed by an attacker with enchanted weapon
	public static float getExpMultiplyer(EntityPlayer killer, EntityLivingBase killed)
	{
		if(killed.isNonBoss()) {
			float lvl = (float)EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.education, killer);
			return (lvl / 2.0F) + 1.0F;
		}
		return 1.0F;
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
