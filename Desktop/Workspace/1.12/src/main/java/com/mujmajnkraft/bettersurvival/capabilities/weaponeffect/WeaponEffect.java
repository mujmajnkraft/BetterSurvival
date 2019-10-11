package com.mujmajnkraft.bettersurvival.capabilities.weaponeffect;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WeaponEffect implements IWeaponEffect{
	
	private int HitsRemaining;
	private String type;

	@Override
	public String getPotionType() {
		return this.type;
	}
	
	@Override
	public int getHitsRemaining() {
		
		return this.HitsRemaining;
	}

	@Override
	public void setHitsRemaining(int hits) {
		
		this.HitsRemaining = hits;
	}
	
	@Override
	public void setPotionType(String type) {
		this.type = type;
	}
	
	public static void Register()
	{
		CapabilityManager.INSTANCE.register(IWeaponEffect.class, new Storage(), new Factory());
	}
	
	private static class Storage implements IStorage<IWeaponEffect>
	{
		@Override
		public NBTTagCompound writeNBT(Capability<IWeaponEffect> capability, IWeaponEffect instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();
			compound.setInteger("Hits", instance.getHitsRemaining());
			
			return compound;
		}

		@Override
		public void readNBT(Capability<IWeaponEffect> capability, IWeaponEffect instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;
			if (compound.hasKey("Hits")) 
			{
				instance.setHitsRemaining(compound.getInteger("Hits"));
			}
		}
	}
	
	private static class Factory implements Callable<IWeaponEffect>
	{

		@Override
		public IWeaponEffect call() throws Exception
		{
			return new WeaponEffect();
		}
	}

}
