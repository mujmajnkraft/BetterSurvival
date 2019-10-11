package com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ArrowProperties implements IArrowProperties{
	
	private float ExplosionPower;
	private boolean CanDestroyBLocks;
	private boolean NoDrag;
	private boolean CanRecover;

	@Override
	public void setExplosion(float power, boolean canDestroyBlocks) {
		this.ExplosionPower = power;
		this.CanDestroyBLocks = canDestroyBlocks;
		
	}

	@Override
	public void setNoDrag(boolean noDrag) {
		this.NoDrag = noDrag;
		
	}

	@Override
	public float getExplosionPower() {
		return this.ExplosionPower;
	}

	@Override
	public boolean getCanDestroyBlocks() {
		return this.CanDestroyBLocks;
	}

	@Override
	public boolean getNoDrag() {
		return this.NoDrag;
	}

	@Override
	public void setCanRecover(boolean canRecover) {
		this.CanRecover = canRecover;
	}

	@Override
	public boolean getCanRecover() {
		return this.CanRecover;
		
	}
	
	public static void Register()
	{
		CapabilityManager.INSTANCE.register(IArrowProperties.class, new Storage(), new Factory());
	}
	
	private static class Storage implements IStorage<IArrowProperties>{

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
	
	private static class Factory implements Callable<IArrowProperties>
	{

		@Override
		public IArrowProperties call() throws Exception
		{
			return new ArrowProperties();
		}
	}

}
