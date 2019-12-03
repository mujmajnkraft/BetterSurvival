package com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties;

public interface IArrowProperties {
	
	public void setExplosion(float power, boolean canDestroyBlocks);
	
	public void setNoDrag(boolean noDrag);
	
	public void setCanRecover(boolean canRecover);
	
	public float getExplosionPower();
	
	public boolean getCanDestroyBlocks();
	
	public boolean getNoDrag();
	
	public boolean getCanRecover();

}
