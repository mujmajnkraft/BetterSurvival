package com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo;

public interface INunchakuCombo {
	float getComboPower();
	int getComboTime();
	void setComboPower(float power);
	void countDown();
	void setComboTime(int time);
	boolean isSpinning();
	void setSpinning(boolean b);
}