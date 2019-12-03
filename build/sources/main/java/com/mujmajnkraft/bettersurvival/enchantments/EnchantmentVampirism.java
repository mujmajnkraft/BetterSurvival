package com.mujmajnkraft.bettersurvival.enchantments;

import java.util.Random;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentVampirism extends Enchantment {

	public EnchantmentVampirism() {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("vampirism");
		this.setName(Reference.MOD_ID + ".vampirism");
	}
	
	@Override
	public int getMaxLevel()
    {
        return ConfigHandler.vampirismlevel;
    }
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 15 * (enchantmentLevel - 1);
    }
	
	public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
	
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
    {
		if (level <=5)
		{
			int rnd = new Random().nextInt(5);
			if (rnd < level)
			{
				user.heal(1.0F);
			}
		}
		else
		{
			user.heal(1.0F+0.2F*level);
		}
    }
	
	public boolean isTreasureEnchantment()
    {
    	if (ConfigHandler.vampirism)
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
		if (ConfigHandler.vampirismlevel == 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
