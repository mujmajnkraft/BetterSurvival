package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
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
		arrow.motionX *= ForgeConfigHandler.enchantments.rangeVelocity;
		arrow.motionY *= ForgeConfigHandler.enchantments.rangeVelocity;
		arrow.motionZ *= ForgeConfigHandler.enchantments.rangeVelocity;
		//arrow.setDamage(arrow.getDamage()/2.0D);//Don't reduce damage, damage from velocity is not linearly scaled, just reduce multiplier to non-ridiculous amount
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
        return ForgeConfigHandler.enchantments.rangeLevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ForgeConfigHandler.enchantments.rangeTreasure;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ForgeConfigHandler.enchantments.rangeLevel != 0;
    }
}
