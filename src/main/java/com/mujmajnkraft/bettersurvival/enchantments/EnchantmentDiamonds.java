package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class EnchantmentDiamonds extends Enchantment {
	
	public EnchantmentDiamonds() {
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("diamonds");
		this.setName(Reference.MOD_ID + ".diamonds");
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
        return ConfigHandler.diamondslevel;
    }

    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.SILK_TOUCH && ench != Enchantments.FORTUNE;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
    	
    	if (stack.getItem() instanceof ItemPickaxe)
		{
			return true;
		}
   		else
   		{
   			return false;
   		}
	}
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.diamonds;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.diamondslevel != 0;
    }
}
