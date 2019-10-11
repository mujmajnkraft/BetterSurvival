package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
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
	
	/**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 5 + (enchantmentLevel - 1) * 9;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
    	switch(this.weapon)
    	{
    	case DAGGER: 	return ConfigHandler.assassinatelevel;
    	case NUNCHAKU: 	return ConfigHandler.combolevel;
    	case HAMMER: 	return ConfigHandler.bashlevel;
    	case BATTLEAXE: return ConfigHandler.disarmlevel;
    	}
    	return 3;
    }
    
    @Override
    public boolean isAllowedOnBooks() {
    	switch(this.weapon)
    	{
    	case DAGGER: 	return !(ConfigHandler.assassinatelevel == 0);
    	case NUNCHAKU: 	return !(ConfigHandler.combolevel == 0);
    	case HAMMER: 	return !(ConfigHandler.bashlevel == 0);
    	case BATTLEAXE: return !(ConfigHandler.disarmlevel == 0);
    	}
    	return super.isAllowedOnBooks();
    }
    
    @Override
    public boolean isTreasureEnchantment()
    {
    	switch(this.weapon)
    	{
    	case DAGGER: 	return ConfigHandler.assassinate;
    	case NUNCHAKU: 	return ConfigHandler.combo;
    	case HAMMER: 	return ConfigHandler.bash;
    	case BATTLEAXE: return ConfigHandler.disarm;
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
