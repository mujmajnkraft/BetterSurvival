package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesProvider;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowRecovery extends Enchantment {
	
	public EnchantmentArrowRecovery() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("arrowrecovery");
		this.setName(Reference.MOD_ID + ".arrowrecovery");
	}
	
	//Called during EntityJoinWorldEvent if an arrow is fired from an enchanted bow
	public static void modifyArrow(EntityLivingBase shooter, EntityArrow arrow)
	{
		int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.arrowrecovery, shooter);
		if (shooter.getRNG().nextInt(4) < l)
		{
			arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null).setCanRecover(true);
		}
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + (enchantmentLevel - 1) * 9;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.arrowrecoverylevel;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.INFINITY;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.arrowrecovery;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.arrowrecoverylevel != 0;
    }
}
