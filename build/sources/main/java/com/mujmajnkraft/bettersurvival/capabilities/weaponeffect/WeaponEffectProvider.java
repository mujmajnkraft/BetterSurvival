package com.mujmajnkraft.bettersurvival.capabilities.weaponeffect;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WeaponEffectProvider implements ICapabilitySerializable<NBTTagCompound>{
	
@CapabilityInject(IWeaponEffect.class)
	
	public static final Capability<IWeaponEffect> WEAPONEFFECT_CAP = null;
	
	private IWeaponEffect instance = WEAPONEFFECT_CAP.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == WEAPONEFFECT_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == WEAPONEFFECT_CAP ? WEAPONEFFECT_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {

		 return (NBTTagCompound) WEAPONEFFECT_CAP.getStorage().writeNBT(WEAPONEFFECT_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		WEAPONEFFECT_CAP.getStorage().readNBT(WEAPONEFFECT_CAP, instance, null, nbt);
		
	}

}
