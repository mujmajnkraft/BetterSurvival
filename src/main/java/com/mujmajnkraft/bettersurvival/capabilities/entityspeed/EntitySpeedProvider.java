package com.mujmajnkraft.bettersurvival.capabilities.entityspeed;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EntitySpeedProvider implements ICapabilitySerializable<NBTTagCompound> {
	
	@CapabilityInject(IEntitySpeed.class)
	
	public static final Capability<IEntitySpeed> ENTITYSPEED_CAP = null;
	
	private IEntitySpeed instance = ENTITYSPEED_CAP.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ENTITYSPEED_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == ENTITYSPEED_CAP ? ENTITYSPEED_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {

		 return (NBTTagCompound) ENTITYSPEED_CAP.getStorage().writeNBT(ENTITYSPEED_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		ENTITYSPEED_CAP.getStorage().readNBT(ENTITYSPEED_CAP, instance, null, nbt);
		
	}

}
