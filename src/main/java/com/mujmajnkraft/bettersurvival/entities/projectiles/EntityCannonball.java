package com.mujmajnkraft.bettersurvival.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCannonball extends EntityThrowable {
	
	private int type;
	
	public EntityCannonball(World worldIn) {
		super(worldIn);
	}

	public EntityCannonball(World worldIn, EntityLivingBase shooter, int type) {
		super(worldIn, shooter);
		this.type = type;
	}
	
	public EntityCannonball(World worldIn, double x, double y, double z, int type)
    {
        super(worldIn, x, y, z);
        this.type = type;
    }

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (!this.world.isRemote)
		{
			int power = this.type == 2 ? 3:1;
			boolean flaming = this.type == 3;
			this.world.newExplosion(this, this.posX, this.posY, this.posZ, power, flaming, true);
			this.setDead();
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) 
	{
		super.readEntityFromNBT(compound);
		this.type = compound.getInteger("Type");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) 
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("Type", this.type);
	}

}
