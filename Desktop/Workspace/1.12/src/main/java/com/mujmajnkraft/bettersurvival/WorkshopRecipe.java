package com.mujmajnkraft.bettersurvival;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EnumWeaponType;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WorkshopRecipe {

	private static final WorkshopRecipe WORKSHOP_BASE = new WorkshopRecipe();
	private final Map<List<ItemStack>, EnumWeaponType> workshopList = Maps.<List<ItemStack>, EnumWeaponType>newHashMap();
	
	public static WorkshopRecipe instance()
	{
		return WORKSHOP_BASE;
	}
	
	public void addWorkshopRecipe(EnumWeaponType weapon, List<ItemStack> ingredients)
	{
		if (weapon != null && ingredients.size() == 9)
		{
			for (ItemStack stack :ingredients)
			{
				if (stack != ItemStack.EMPTY)
				{
					workshopList.put(ingredients, weapon);
					return;
				}
			}
		}
	}
	
	public List<ItemStack> getIngredients(EnumWeaponType weapon)
	{
		for (Entry<List<ItemStack>, EnumWeaponType> entry : this.workshopList.entrySet())
		{
			if (weapon == entry.getValue())
			{
				return entry.getKey();
			}
		}
		return new ArrayList<ItemStack>();
	}
	
	private WorkshopRecipe()
	{
		ItemStack[] ballista = new ItemStack[] {new ItemStack(Blocks.PLANKS, 20), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT, 8), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.STRING, 6),	ItemStack.EMPTY };
		ItemStack[] cannon = new ItemStack[] {new ItemStack(Blocks.PLANKS, 12), new ItemStack(Items.FLINT, 8), ItemStack.EMPTY, ItemStack.EMPTY,ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT, 8), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY };
		ItemStack[] potionthrower = new ItemStack[] {new ItemStack(Blocks.PLANKS, 20), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT, 8),	new ItemStack(Items.LEATHER, 8), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.STRING, 6),	ItemStack.EMPTY };
		ItemStack[] zeppelin = new ItemStack[] {new ItemStack(Blocks.PLANKS, 64), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT, 8), ItemStack.EMPTY, new ItemStack(Blocks.WOOL, 40),	ItemStack.EMPTY, new ItemStack(Items.STRING, 20),ItemStack.EMPTY, new ItemStack(Items.REDSTONE, 6)};
		addWorkshopRecipe(EnumWeaponType.BALLISTA, new ArrayList<>(Arrays.asList(ballista)));
		addWorkshopRecipe(EnumWeaponType.CANNON, new ArrayList<>(Arrays.asList(cannon)));
		addWorkshopRecipe(EnumWeaponType.POTION_THROWER, new ArrayList<>(Arrays.asList(potionthrower)));
		addWorkshopRecipe(EnumWeaponType.ZEPPELIN, new ArrayList<>(Arrays.asList(zeppelin)));
	}
	
	public List<ItemStack> getBlueprints()
	{
		ArrayList<ItemStack> list = new ArrayList<>();
		for (Entry<List<ItemStack>, EnumWeaponType> entry : this.workshopList.entrySet())
		{
			System.out.println(entry.getValue().getName());
			ItemStack stack = new ItemStack(ModItems.blueprint);
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("Type", entry.getValue().getName());
			stack.setTagCompound(compound);
			list.add(stack);
		}
		return list;
	}
}
