package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentVitality extends Enchantment {
	public EnchantmentVitality() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
		this.setRegistryName("vitality");
		this.setName(Reference.MOD_ID + ".vitality");
	}
	
	public static void healPlayer(EntityPlayer player)
	{
		if (player.getEntityWorld().getGameRules().getBoolean("naturalRegeneration")
				&& player.getFoodStats().getFoodLevel() >= 18
				&& player.getHealth() < player.getMaxHealth()
				&& player.ticksExisted % (40/EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.vitality, player)) == 0)
			player.heal(1.0F);
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 20 + 15 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 40;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.vitalitylevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.vitality;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.vitalitylevel != 0;
    }
}
