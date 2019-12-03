package com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo;

public interface INunchakuCombo {
	
	public float getComboPower();
	
	public int getComboTime();
	
	public void setComboPower(float power);
	
	public void countDown();

	public void setComboTime(int time);
	
	public boolean isSpinning();
	
	public void setSpinning(boolean b);
}
