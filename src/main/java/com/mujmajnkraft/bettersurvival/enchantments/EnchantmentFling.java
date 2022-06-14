package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentFling extends Enchantment {
	public EnchantmentFling () {
	super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	this.setRegistryName("fling");
	this.setName(Reference.MOD_ID + ".fling");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 5 + 20 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.flinglevel;
    }
    
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
    {
    	target.motionY += 0.15D*level;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.KNOCKBACK;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.fling;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.flinglevel != 0;
    }
}
