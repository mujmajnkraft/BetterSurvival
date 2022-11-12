package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentRange extends Enchantment {

	public EnchantmentRange() {
		super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("range");
		this.setName(Reference.MOD_ID + ".range");
	}
	
	public static void modifyArrow(EntityArrow arrow)
	{
		arrow.motionX *= 2;
		arrow.motionY *= 2;
		arrow.motionZ *= 2;
		//Reduces damage to compensate for increased speed
		arrow.setDamage(arrow.getDamage()/2.0D);
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 20;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.rangelevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.range;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.rangelevel != 0;
    }
}
