package com.mujmajnkraft.bettersurvival.init;

import java.util.List;

import com.google.common.base.Predicate;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemBattleAxe;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemHammer;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Iterator;

public class ModCrafting {
	
	public static void register() {
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.crossbow), "TIW", " TI", "S T", 'I', Items.IRON_INGOT, 'T', Items.STRING, 'W', Blocks.PLANKS, 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(ModItems.smallshield), " W ", "WIW", " W ", 'W', Blocks.PLANKS, 'I', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(ModItems.bigshield), " WI", "SWI", " WI", 'W', Blocks.PLANKS, 'I', Items.IRON_INGOT, 'S', Items.STICK);
		
		for (ItemHammer item:ModItems.hammers)
		{
			if(item.getMaterial()==ToolMaterial.DIAMOND||item.getMaterial()==ToolMaterial.GOLD||item.getMaterial()==ToolMaterial.IRON||item.getMaterial()==ToolMaterial.STONE||item.getMaterial()==ToolMaterial.WOOD)
			{
					GameRegistry.addShapedRecipe(new ItemStack(item), " II", "SII", " II", 'S', Items.STICK, 'I', item.getMaterial().getRepairItemStack());
			}
			else if (ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+item.getMaterial().name()))
			{
					GameRegistry.addRecipe(new ShapedOreRecipe(item, " II", "SII", " II", 'S', Items.STICK, 'I', "ingot"+item.getMaterial().name()));
			}
			else
			{
				item.setCreativeTab(null);
			}	
		}
		
		for (ItemSpear item:ModItems.spears)
		{
			if(item.getMaterial()==ToolMaterial.DIAMOND||item.getMaterial()==ToolMaterial.GOLD||item.getMaterial()==ToolMaterial.IRON||item.getMaterial()==ToolMaterial.STONE||item.getMaterial()==ToolMaterial.WOOD)
			{
				GameRegistry.addShapedRecipe(new ItemStack(item), "  I", " S ", "S  ", 'S', Items.STICK, 'I', item.getMaterial().getRepairItemStack());
			}
			else if (ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+item.getMaterial().name()))
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(item, "  I", " S ", "S  ", 'S', Items.STICK, 'I', "ingot"+item.getMaterial().name()));
			}
			else
			{
				item.setCreativeTab(null);
			}
		}
		
		for (ItemBattleAxe item:ModItems.battleaxes)
		{
			if(item.getMaterial()==ToolMaterial.DIAMOND||item.getMaterial()==ToolMaterial.GOLD||item.getMaterial()==ToolMaterial.IRON||item.getMaterial()==ToolMaterial.STONE||item.getMaterial()==ToolMaterial.WOOD)
			{
				GameRegistry.addShapedRecipe(new ItemStack(item), "III", "ISI", " S ", 'S', Items.STICK, 'I', item.getMaterial().getRepairItemStack());
			}
			else if (ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+item.getMaterial().name()))
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(item, "III", "ISI", " S ", 'S', Items.STICK, 'I', "ingot"+item.getMaterial().name()));
			}
			else
			{
				item.setCreativeTab(null);
			}
		}
		
		for (ItemDagger item:ModItems.daggers)
		{
			if(item.getMaterial()==ToolMaterial.DIAMOND||item.getMaterial()==ToolMaterial.GOLD||item.getMaterial()==ToolMaterial.IRON||item.getMaterial()==ToolMaterial.STONE||item.getMaterial()==ToolMaterial.WOOD)
			{
				GameRegistry.addShapedRecipe(new ItemStack(item), "   ", " I ", " S ", 'S', Items.STICK, 'I', item.getMaterial().getRepairItemStack());
			}
			else if (ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+item.getMaterial().name()))
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(item, "   ", " I ", " S ", 'S', Items.STICK, 'I', "ingot"+item.getMaterial().name()));
			}
			else
			{
				item.setCreativeTab(null);
			}
		}
		
		for (ItemNunchaku item:ModItems.nunchakus)
		{
			if(item.getMaterial()==ToolMaterial.DIAMOND||item.getMaterial()==ToolMaterial.GOLD||item.getMaterial()==ToolMaterial.IRON||item.getMaterial()==ToolMaterial.STONE||item.getMaterial()==ToolMaterial.WOOD)
			{
				GameRegistry.addShapedRecipe(new ItemStack(item), "   ", " S ", "I I", 'S', Items.STRING, 'I', item.getMaterial().getRepairItemStack());
			}
			else if (OreDictionary.doesOreNameExist("ingot"+item.getMaterial().name()))
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(item, "   ", " S ", "I I", 'S', Items.STRING, 'I', "ingot"+item.getMaterial().name()));
			}
			else
			{
				item.setCreativeTab(null);
			}
		}
		
		if (!ConfigHandler.allowvanillashields)
		{
			List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
			Iterator<IRecipe> itr = recipes.iterator();
	        
			while (itr.hasNext()) {
				ItemStack is = itr.next().getRecipeOutput();
				if (is != null)
				{
					if (is.getItem() == Items.SHIELD)
						itr.remove();
				}
			};
		}
		
		Predicate<ItemStack> ink = new PotionHelper.ItemPredicateInstance(Items.DYE, 0);
		Predicate<ItemStack> chorus = new PotionHelper.ItemPredicateInstance(Items.CHORUS_FRUIT);
		Predicate<ItemStack> redstone = new PotionHelper.ItemPredicateInstance(Items.REDSTONE);
		Predicate<ItemStack> glowstone = new PotionHelper.ItemPredicateInstance(Items.GLOWSTONE_DUST);
		Predicate<ItemStack> fermentedeye = new PotionHelper.ItemPredicateInstance(Items.FERMENTED_SPIDER_EYE);
		Predicate<ItemStack> apple = new PotionHelper.ItemPredicateInstance(Items.GOLDEN_APPLE);
		
		PotionHelper.registerPotionTypeConversion(PotionTypes.AWKWARD, ink, ModPotionTypes.blindness);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.blindness, redstone, ModPotionTypes.longblindness);
		PotionHelper.registerPotionTypeConversion(PotionTypes.REGENERATION, fermentedeye, ModPotionTypes.decay);
		PotionHelper.registerPotionTypeConversion(PotionTypes.LONG_REGENERATION, fermentedeye, ModPotionTypes.longdecay);
		PotionHelper.registerPotionTypeConversion(PotionTypes.STRONG_REGENERATION, fermentedeye, ModPotionTypes.strongdecay);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.decay, redstone, ModPotionTypes.longdecay);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.decay, glowstone, ModPotionTypes.strongdecay);
		PotionHelper.registerPotionTypeConversion(PotionTypes.AWKWARD, chorus, ModPotionTypes.warp);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.warp, glowstone, ModPotionTypes.strongwarp);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.warp, fermentedeye, ModPotionTypes.antiwarp);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.antiwarp, redstone, ModPotionTypes.longantiwarp);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.milk, fermentedeye, ModPotionTypes.dispell);
		PotionHelper.registerPotionTypeConversion(ModPotionTypes.milk, apple, ModPotionTypes.cure);
	}
}
