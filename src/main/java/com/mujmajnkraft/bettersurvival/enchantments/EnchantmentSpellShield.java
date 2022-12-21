package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentSpellShield extends Enchantment {
	public EnchantmentSpellShield () {
		super(Rarity.UNCOMMON, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND});
		this.setRegistryName("spellshield");
		this.setName(Reference.MOD_ID + ".spellshield");
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != ModEnchantments.blockpower;
    }

    public int getMaxLevel()
    {
        return ForgeConfigHandler.enchantments.spellShieldLevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ForgeConfigHandler.enchantments.spellShieldTreasure;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
    	
    	return stack.getItem() instanceof ItemCustomShield;
	}
	
	public boolean isAllowedOnBooks()
    {
		return ForgeConfigHandler.enchantments.spellShieldLevel != 0;
    }
}
