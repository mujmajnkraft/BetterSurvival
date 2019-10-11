package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EnumWeaponType;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemBlueprint extends Item{
	
	public ItemBlueprint()
	{
		this.setRegistryName("Blueprint");
		this.setUnlocalizedName("blueprint");
		this.maxStackSize = 1;
	}
	
	public static EnumWeaponType getType(ItemStack stack)
	{
		if (stack.getItem() == ModItems.blueprint && stack.hasTagCompound())
		{
			String name = stack.getTagCompound().getString("Type");
			return EnumWeaponType.getTypeFromName(name); 
		}
		return null;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
    {
        if (ItemBlueprint.getType(stack) != null)
        {
            return I18n.translateToLocal(this.getUnlocalizedName() + ItemBlueprint.getType(stack) + ".name");
        }
        else
        {
            return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }

}
