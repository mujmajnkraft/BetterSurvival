package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesProvider;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.IArrowProperties;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentBlast extends Enchantment {

	public EnchantmentBlast() {
		super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("blast");
		this.setName(Reference.MOD_ID + ".blast");
	}
	
	//Called during EntityJoinWorldEvent if an arrow is fired from an enchanted bow
	public static void modifyArrow(EntityArrow arrow, EntityLivingBase shooter)
	{
		float power = (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.blast, shooter.getHeldItemMainhand())+1)/4.0F;
		boolean canDestroyBlocks;
		if(shooter instanceof EntityPlayer) {
			canDestroyBlocks = ((EntityPlayer)shooter).capabilities.allowEdit;
		}
		else {
			canDestroyBlocks = shooter.getEntityWorld().getGameRules().getBoolean("mobGriefing");
		}
		IArrowProperties cap = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(cap != null) cap.setExplosion(power, canDestroyBlocks);
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
