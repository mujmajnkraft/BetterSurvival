package com.mujmajnkraft.bettersurvival.enchantments;

import java.util.Random;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentReflection extends Enchantment {
	
	public EnchantmentReflection() {
		super(Rarity.RARE, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND});
		this.setRegistryName("reflection");
		this.setName(Reference.MOD_ID + ".reflection");
	}
	
	public static void reflectDamage(Entity attacker, EntityLivingBase defender)
	{
		int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.reflection, defender.getActiveItemStack());
		if(defender.getRNG().nextFloat() < 0.15F * (float)level)
		{
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(defender), level > 10 ? level - 10 : 1 + defender.getRNG().nextInt(4));
			defender.getActiveItemStack().damageItem(1, defender);
		}
	};
	
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
        return ForgeConfigHandler.enchantments.reflectionLevel;
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
    	return ForgeConfigHandler.enchantments.reflectionTreasure;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
    	return stack.getItem() instanceof ItemCustomShield;
	}
	
	public boolean isAllowedOnBooks()
    {
		return ForgeConfigHandler.enchantments.reflectionLevel != 0;
    }
}
