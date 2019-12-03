package com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class NunchakuCombo implements INunchakuCombo{

	private float comboPower;
	
	private int comboTime;
	
	private boolean isSpinning;
	
	@Override
	public float getComboPower() {
		return comboPower;
	}

	@Override
	public int getComboTime() {
		return comboTime;
	}

	@Override
	public void setComboPower(float power) {
		this.comboPower = power < 1 ? power : 1;
		this.comboTime = 30;
		
	}

	@Override
	public void countDown() {
		this.comboTime --;
		if (this.comboTime <= 0)
		{
			this.comboTime = 0;
			this.comboPower = 0;
		}
	}

	@Override
	public void setComboTime(int time)
	{
		this.comboTime = time;
		
	}@Override
	public boolean isSpinning()
	{
		return this.isSpinning;
	}

	@Override
	public void setSpinning(boolean b)
	{
		this.isSpinning = b;
	}
	
	public static void Register()
	{
		CapabilityManager.INSTANCE.register(INunchakuCombo.class, new Storage(), new Factory());
	}
	
	private static class Storage implements IStorage<INunchakuCombo>
	{
		@Override
		public NBTBase writeNBT(Capability<INunchakuCombo> capability, INunchakuCombo instance, EnumFacing side) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setFloat("Power", instance.getComboPower());
			compound.setInteger("Time", instance.getComboTime());
			return compound;
		}

		@Override
		public void readNBT(Capability<INunchakuCombo> capability, INunchakuCombo instance, EnumFacing side, NBTBase nbt) {
			instance.setComboPower(((NBTTagCompound)nbt).getFloat("Power"));
			instance.setComboTime(((NBTTagCompound)nbt).getInteger("Time"));
		}
	}
	
	private static class Factory implements Callable<INunchakuCombo>
	{

		@Override
		public INunchakuCombo call() throws Exception {
			return new NunchakuCombo();
		}
	}

}
