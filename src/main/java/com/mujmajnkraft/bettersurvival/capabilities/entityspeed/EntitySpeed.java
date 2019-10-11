package com.mujmajnkraft.bettersurvival.capabilities.entityspeed;

public class EntitySpeed implements IEntitySpeed{
	
	private double X;
	private double Y;
	private double Z;
	
	private double prevX;
	private double prevY;
	private double prevZ;

	@Override
	public double getHorizontalSpeed() {
		return Math.sqrt((this.X - this.prevX) * (this.X - this.prevX) + (this.Z - this.prevZ) * (this.Z - this.prevZ)) * 20;
	}

	@Override
	public double getVerticalSpeed() {
		return Math.abs(this.Y - this.prevY) * 20;
	}

	@Override
	public void setX(double X) {
		this.X = X;
	}

	@Override
	public void setY(double Y) {
		this.Y = Y;
	}

	@Override
	public void setZ(double Z) {
		this.Z = Z;
	}

	@Override
	public void update() {
		this.prevX = this.X;
		this.prevY = this.Y;
		this.prevZ = this.Z;
	}

	@Override
	public double getPrevX() {
		return this.prevX;
	}

	@Override
	public double getPrevY() {
		return this.prevY;
	}

	@Override
	public double getPrevZ() {
		return this.prevZ;
	}

	@Override
	public void setPrevX(double prevX) {
		this.prevX = prevX;
	}

	@Override
	public void setPrevY(double prevY) {
		this.prevX = prevY;
		
	}

	@Override
	public void setPrevZ(double prevZ) {
		this.prevX = prevZ;
		
	}

}
