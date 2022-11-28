package com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class NunchakuComboProvider implements ICapabilitySerializable<NBTTagCompound>{
	@CapabilityInject(INunchakuCombo.class)

	 public static final Capability<INunchakuCombo> NUNCHAKUCOMBO_CAP = null;

	 private INunchakuCombo instance = NUNCHAKUCOMBO_CAP.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == NUNCHAKUCOMBO_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == NUNCHAKUCOMBO_CAP ? NUNCHAKUCOMBO_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) NUNCHAKUCOMBO_CAP.getStorage().writeNBT(NUNCHAKUCOMBO_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		NUNCHAKUCOMBO_CAP.getStorage().readNBT(NUNCHAKUCOMBO_CAP, this.instance, null, nbt);
	}
}