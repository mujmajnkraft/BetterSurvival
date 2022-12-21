package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;

public class EnchantmentVersatility extends Enchantment {
	
	public EnchantmentVersatility() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("versatility");
		this.setName(Reference.MOD_ID + ".versatility");
	}
	
	public static float getSpeedModifier(EntityPlayer miner, IBlockState state)
	{
		if (miner.inventory.getDestroySpeed(state) <= 1.0F)
		{
			Item item = miner.getHeldItemMainhand().getItem();
			if(item instanceof ItemTool)
			{
				ItemTool tool =(ItemTool) item;
				return ToolMaterial.valueOf(tool.getToolMaterialName()).getEfficiency() / 2.0F;
			}
		}
		return 1.0F;
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 15;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ForgeConfigHandler.enchantments.versatilityLevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ForgeConfigHandler.enchantments.versatilityTreasure;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ForgeConfigHandler.enchantments.versatilityLevel != 0;
    }
}
