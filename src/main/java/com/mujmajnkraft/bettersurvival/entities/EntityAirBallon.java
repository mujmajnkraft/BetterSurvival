package com.mujmajnkraft.bettersurvival.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAirBallon extends Entity implements IEntityMultiPart{

	public float durability;
	private MultiPartEntityPart String1 = new MultiPartEntityPart(this, "String", 0.25F, 5);
	private MultiPartEntityPart String2 = new MultiPartEntityPart(this, "String", 0.25F, 5);
	private MultiPartEntityPart String3 = new MultiPartEntityPart(this, "String", 0.25F, 5);
	private MultiPartEntityPart String4 = new MultiPartEntityPart(this, "String", 0.25F, 5);
	
	public EntityAirBallon(World worldIn) {
		super(worldIn);
		this.setSize(9, 5);
		this.preventEntitySpawning = true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.isEntityInvulnerable(source))
		{
			this.durability -= amount;
			System.out.println(amount);
			System.out.println(this.durability);
			if (durability <= 0)
			{
				this.setDead();
			}
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	public void readEntityFromNBT(NBTTagCompound compound)
	{
		
	}

	public void writeEntityToNBT(NBTTagCompound compound)
	{
		
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onEntityUpdate()
	{
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		
		String1.width = 0.25F;
		String1.height = 5;
		String2.width = 0.25F;
		String2.height = 5;
		String3.width = 0.25F;
		String3.height = 5;
		String4.width = 0.25F;
		String4.height = 5;
		
		String1.setPosition(this.posX-4.5F, this.posY-5, this.posZ+4.5F);
		String2.setPosition(this.posX+4.5F, this.posY-5, this.posZ+4.5F);
		String3.setPosition(this.posX-4.5F, this.posY-5, this.posZ-4.5F);
		String4.setPosition(this.posX+4.5F, this.posY-5, this.posZ-4.5F);
		this.motionX *= 0.9F;
		this.motionZ *= 0.9F;
		super.onEntityUpdate();
	}
}
