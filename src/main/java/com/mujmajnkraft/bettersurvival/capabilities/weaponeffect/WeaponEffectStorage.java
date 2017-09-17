package com.mujmajnkraft.bettersurvival.capabilities.weaponeffect;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WeaponEffectStorage implements IStorage<IWeaponEffect>{

	@Override
	public NBTTagCompound writeNBT(Capability<IWeaponEffect> capability, IWeaponEffect instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("Hits", instance.getHitsRemaining());
		
		return compound;
	}

	@Override
	public void readNBT(Capability<IWeaponEffect> capability, IWeaponEffect instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound) nbt;
		if (compound.hasKey("Hits")) 
		{
			instance.setHitsRemaining(compound.getInteger("Hits"));
		}
		if (compound.hasKey("Type")) 
		{
			instance.setPotionType(compound.getString("Type"));
		}
	}

}
