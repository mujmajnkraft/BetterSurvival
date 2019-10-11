package com.mujmajnkraft.bettersurvival.capabilities.entityspeed;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EntitySpeedStorage implements IStorage<IEntitySpeed>{

	@Override
	public NBTTagCompound writeNBT(Capability<IEntitySpeed> capability, IEntitySpeed instance, EnumFacing side) {
		
		NBTTagCompound compound = new NBTTagCompound();
		compound.setDouble("prevX", instance.getPrevX());
		compound.setDouble("prevY", instance.getPrevY());
		compound.setDouble("prevZ", instance.getPrevZ());
		return compound;
	}

	@Override
	public void readNBT(Capability<IEntitySpeed> capability, IEntitySpeed instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound) nbt;
		instance.setPrevX(compound.getDouble("prevX"));
		instance.setPrevY(compound.getDouble("prevY"));
		instance.setPrevZ(compound.getDouble("prevZ"));
	}

}
