package com.mujmajnkraft.bettersurvival.capabilities.weaponeffect;

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

}
