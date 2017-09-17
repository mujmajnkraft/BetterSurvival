package com.mujmajnkraft.bettersurvival.capabilities.entityspeed;

public interface IEntitySpeed {
	
	public double getHorizontalSpeed();
	
	public double getVerticalSpeed();
	
	public double getPrevX();
	
	public double getPrevY();
	
	public double getPrevZ();
	
	public void setX(double X);
	
	public void setY(double Y);
	
	public void setZ(double Z);
	
	public void setPrevX(double prevX);
	
	public void setPrevY(double prevY);
	
	public void setPrevZ(double prevZ);
	
	public void update();

}
