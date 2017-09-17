package com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties;

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

}
