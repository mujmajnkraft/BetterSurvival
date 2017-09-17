package com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ArrowPropertiesStorage implements IStorage<IArrowProperties>{

	@Override
	public NBTBase writeNBT(Capability<IArrowProperties> capability, IArrowProperties instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setFloat("ExplosionPower", instance.getExplosionPower());
		compound.setBoolean("CanDestroyBlocks", instance.getCanDestroyBlocks());
		compound.setBoolean("NoDrag", instance.getNoDrag());
		compound.setBoolean("CanRecover", instance.getCanRecover());
		return compound;
	}

	@Override
	public void readNBT(Capability<IArrowProperties> capability, IArrowProperties instance, EnumFacing side,NBTBase nbt) {
		instance.setExplosion(((NBTTagCompound)nbt).getFloat("ExplosionPower"), ((NBTTagCompound)nbt).getBoolean("CanDestroyBlocks"));
		instance.setNoDrag(((NBTTagCompound)nbt).getBoolean("NoDrag"));
		instance.setCanRecover(((NBTTagCompound)nbt).getBoolean("CanRecover"));
	}

}
