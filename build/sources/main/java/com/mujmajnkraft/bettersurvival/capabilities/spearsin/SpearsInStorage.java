package com.mujmajnkraft.bettersurvival.capabilities.spearsin;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SpearsInStorage implements IStorage<ISpearsIn>{
	
	@Override
	public NBTTagList writeNBT(Capability<ISpearsIn> capability, ISpearsIn instance, EnumFacing side) 
	{
		NBTTagList taglist = new NBTTagList();
		ArrayList<ItemStack> list = instance.getSpearsIn();
		if (!list.isEmpty())
		{
			for (ItemStack spear:list)
			{
				NBTTagCompound nbt = spear.serializeNBT();
				taglist.appendTag(nbt);
			}
		}
		return taglist;
	}
	
	@Override
	public void readNBT(Capability<ISpearsIn> capability, ISpearsIn instance, EnumFacing side, NBTBase nbt)
	{
		for (int i=0;i<((NBTTagList)nbt).tagCount();i++)
		{
			NBTTagCompound compound = ((NBTTagList)nbt).getCompoundTagAt(i);
			if (compound!=null)
			{
				ItemStack spear = new ItemStack(compound);
				instance.addSpear(spear);
			}
		}
	}

}
