package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentPenetration extends Enchantment {
	
	public EnchantmentPenetration() {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("penetration");
		this.setName(Reference.MOD_ID + ".penetration");
	}
	
	public static void dealPiercingDamage(EntityLivingBase attacker, EntityLivingBase target, float amount)
	{
		int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.penetration, (EntityLivingBase) attacker);
		float min = amount * Math.min(((l + 2) / 10.0f), 1);
		target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(attacker, attacker), min);
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 3 + 3 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 20;
    }

    public int getMaxLevel()
    {
        return ForgeConfigHandler.enchantments.penetrationLevel;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != net.minecraft.init.Enchantments.SHARPNESS && ench != net.minecraft.init.Enchantments.SMITE && ench != net.minecraft.init.Enchantments.BANE_OF_ARTHROPODS;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ForgeConfigHandler.enchantments.penetrationTreasure;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ForgeConfigHandler.enchantments.penetrationLevel != 0;
    }
}