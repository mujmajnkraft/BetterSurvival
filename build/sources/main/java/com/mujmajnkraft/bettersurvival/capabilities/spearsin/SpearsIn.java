package com.mujmajnkraft.bettersurvival.capabilities.spearsin;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class SpearsIn implements ISpearsIn{
	
	private ArrayList<ItemStack> SpearsIn = new ArrayList<ItemStack>();

	@Override
	public void addSpear(ItemStack spear) 
	{
		SpearsIn.add(spear);
	}

	@Override
	public ArrayList<ItemStack> getSpearsIn() 
	{
		return SpearsIn;
	}

}
