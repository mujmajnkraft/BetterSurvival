package com.mujmajnkraft.bettersurvival.entities.siegeweapons;

import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityCannonball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityCannon extends EntitySiegeWeapon{
	
	private int ammoType;
	private float progress;
	private static DataParameter<Boolean> SHOOTING = EntityDataManager.<Boolean>createKey(EntityCannon.class, DataSerializers.BOOLEAN);

	public EntityCannon(World worldIn) {
		super(worldIn);
		this.setSize(2, 1.5F);
		this.entityCollisionReduction = 1;
		this.stepHeight = 1.5F;
		this.isPullable = true;
		this.durability = 100;
	}
	
	@Override
	public void onEntityUpdate()
	{
		if (this.getControllingPassenger() != null)
		{
			float Pitch = this.getControllingPassenger().rotationPitch;
			float rotationPitch = Pitch > 0 ? Math.min(45, Pitch) : Math.max(Pitch, -45);
			this.rotationPitch = rotationPitch - 10;
		}
		
		if (this.progress >= 1)
		{
			dataManager.set(SHOOTING, false);
			if (!this.world.isRemote)
			{
				if (this.getControllingPassenger() instanceof EntityLivingBase)
				{
					EntityCannonball cannonball = new EntityCannonball(this.world, (EntityLivingBase)this.getControllingPassenger(), this.ammoType);
					cannonball.setHeadingFromThrower(this, this.rotationPitch, this.rotationYaw, 0, 3.0F, 0.1F);
					double x = Math.sin(this.rotationYaw * 0.017453292F);
					double y = 0.4D + Math.sin((this.rotationPitch) * 0.017453292F) * 1.2D;
					double z = -Math.cos(this.rotationYaw * 0.017453292F);
					cannonball.setPosition(this.posX - 2*x,this.posY+y,this.posZ - 2*z);
					world.spawnEntity(cannonball);
				}
				else
				{
					EntityCannonball cannonball = new EntityCannonball(this.world, this.posX, this.posY, this.posZ, this.ammoType);
					cannonball.setHeadingFromThrower(this, this.rotationPitch, this.rotationYaw, 0, 3.0F, 0.1F);
					world.spawnEntity(cannonball);
				}
			}
			this.ammoType = 0;
			this.progress = 0;
		}
		if (this.progress > 0)
		{
			this.progress += 0.05;
			{
				WorldServer worldserver = (WorldServer) this.world;
	            worldserver.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY+Math.sin(Math.toRadians(this.rotationPitch))+1, this.posZ-1, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
		super.onEntityUpdate();
	}
	
	@Override
	public void performAction() {
		if ((this.ammoType > 0 ) && this.progress == 0)
		{
			dataManager.set(SHOOTING, true);
			this.progress = 0.05F;
		}
		super.performAction();
	}
	
	public boolean isShooting()	{
		return dataManager.get(SHOOTING);
	}

	@Override
	protected void entityInit() {
		dataManager.register(SHOOTING, Boolean.valueOf(false));
		
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		this.ammoType = compound.getInteger("AmmoType");
		this.progress = compound.getFloat("Progress");
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("Progress", this.progress);
		compound.setInteger("AmmoType", this.ammoType);
	}
	
	public boolean load(int ammoType)
	{
		if ((this.ammoType< 1 || this.ammoType > 3) && this.progress == 0)
		{
			this.ammoType = ammoType;
			System.out.println("loaded");
			return true;
		}
		return false;
	}
	
	@Override
	public void updatePassenger(Entity passenger) {
		super.updatePassenger(passenger);
		if (this.isPassenger(passenger))
        {
			double x = 1.2 * Math.sin(this.rotationYaw * 0.017453292F);
			double y = 0;
			double z = -1.2 * Math.cos(this.rotationYaw * 0.017453292F);
			passenger.setPosition(this.posX + x,this.posY + y,this.posZ + z);
        }
	}
	
	@Override
	public void setDead() {
		if (this.ammoType > 0 && !world.isRemote)
		{
			int power = this.ammoType == 2 ? 2:1;
			boolean flaming = this.ammoType == 3;
			this.world.newExplosion(this, this.posX, this.posY, this.posZ, power, flaming, true);
		}
		super.setDead();
	}

}
