package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.integration.SoManyEnchantmentsCompat;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBattleAxe extends ItemCustomWeapon {
	
	public ItemBattleAxe(ToolMaterial material)
	{
		super(material, 1.6f, 1.25f);
		this.setRegistryName(Reference.MOD_ID,"item"+material.name().toLowerCase()+"battleaxe");
		this.setTranslationKey(material.name().toLowerCase()+"battleaxe");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String s = net.minecraft.client.resources.I18n.format(Reference.MOD_ID + ".battleaxe.desc");
		tooltip.add(TextFormatting.AQUA + s);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
		if(BetterSurvival.isSMELoaded && SoManyEnchantmentsCompat.isCombatAxeSMEEnchant(enchantment.type))
		{
			return true;
		}
		else
		{
			return super.canApplyAtEnchantingTable(stack, enchantment);
		}
	}
}