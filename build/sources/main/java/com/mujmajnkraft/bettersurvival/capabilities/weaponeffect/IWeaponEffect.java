package com.mujmajnkraft.bettersurvival.capabilities.weaponeffect;

public interface IWeaponEffect {
	
	public int getHitsRemaining();
	
	public String getPotionType();
	
	public void setHitsRemaining(int hits);
	
	public void setPotionType(String type);

}
