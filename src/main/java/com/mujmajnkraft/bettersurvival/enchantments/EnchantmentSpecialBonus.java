package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemBattleAxe;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemHammer;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentSpecialBonus extends Enchantment {
	
	private EnumWeaponType weapon;

	public EnchantmentSpecialBonus(EnumWeaponType class1) {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.weapon = class1;
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 5 + (enchantmentLevel - 1) * 9;
    }

	public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    public int getMaxLevel()
    {
    	switch(this.weapon)
    	{
    	case DAGGER: 	return ForgeConfigHandler.enchantments.assassinteLevel;
    	case NUNCHAKU: 	return ForgeConfigHandler.enchantments.comboLevel;
    	case HAMMER: 	return ForgeConfigHandler.enchantments.bashLevel;
    	case BATTLEAXE: return ForgeConfigHandler.enchantments.disarmLevel;
    	}
    	return 3;
    }
    
    @Override
    public boolean isAllowedOnBooks() {
    	switch(this.weapon)
    	{
    	case DAGGER: 	return ForgeConfigHandler.enchantments.assassinteLevel !=0;
    	case NUNCHAKU: 	return ForgeConfigHandler.enchantments.comboLevel !=0;
    	case HAMMER: 	return ForgeConfigHandler.enchantments.bashLevel !=0;
    	case BATTLEAXE: return ForgeConfigHandler.enchantments.disarmLevel !=0;
    	}
    	return super.isAllowedOnBooks();
    }
    
    @Override
    public boolean isTreasureEnchantment()
    {
    	switch(this.weapon)
    	{
    	case DAGGER: 	return ForgeConfigHandler.enchantments.assassinteTreasure;
    	case NUNCHAKU: 	return ForgeConfigHandler.enchantments.comboTreasure;
    	case HAMMER: 	return ForgeConfigHandler.enchantments.bashTreasure;
    	case BATTLEAXE: return ForgeConfigHandler.enchantments.disarmTreasure;
    	}
    	return false;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
    	switch(this.weapon)
    	{
    	case DAGGER: 	return (stack.getItem() instanceof ItemDagger);
    	case NUNCHAKU: 	return (stack.getItem() instanceof ItemNunchaku);
    	case HAMMER: 	return (stack.getItem() instanceof ItemHammer);
    	case BATTLEAXE: return (stack.getItem() instanceof ItemBattleAxe);
    	}
    	return false;
	}
    
    public enum EnumWeaponType
    {
    	DAGGER,
    	NUNCHAKU,
    	BATTLEAXE,
    	HAMMER
    }

}
