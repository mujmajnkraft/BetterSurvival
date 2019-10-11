package com.mujmajnkraft.bettersurvival.entities.siegeweapons;

import com.mujmajnkraft.bettersurvival.entities.EntityAirBallon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityZeppelin extends EntitySiegeWeapon {
	private EntityAirBallon boundTo;
	
	public EntityZeppelin(World worldIn) {
		super(worldIn);
		this.setSize(9, 5);
		this.isPullable = false;
		this.setNoGravity(true);
	}
	
	@Override
	public void onEntityUpdate()
	{
		if (this.boundTo != null)
		{
			if (this.getDistanceToEntity(this.boundTo) >= 12)
			{
				this.boundTo.setDead();
				this.setNoGravity(false);
			}
			else
			{
				this.boundTo.move(MoverType.SELF, this.posX - this.boundTo.posX, this.posY - this.boundTo.posY + 10, this.posZ - this.boundTo.posZ);
			}
		}
		else
		{
			this.setNoGravity(false);
		}
        super.onEntityUpdate();
	}
	
	public void bind(EntityAirBallon entity)
	{
		this.boundTo = entity;
	}
	
	@Override
	public void updatePassenger(Entity passenger)
	{
		double x = -Math.sin(this.rotationYaw * 0.017453292F) * 3;
		double z = Math.cos(this.rotationYaw * 0.017453292F) * 3;
		passenger.setPosition(this.posX + x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ + z);
	}
	
	@Override
	public Entity getControllingPassenger()
	{
		for (Entity passenger : this.getPassengers())
		{
			if (passenger instanceof EntityPlayer) return passenger;
		}
		return null;
	}
	
	@Override
	public void setDead()
	{
		if (this.boundTo!=null)
		{
			this.boundTo.setDead();
		}
		super.setDead();
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		System.out.println("Trying to load");
		if (compound.hasUniqueId("BoundTo") && world instanceof WorldServer)
		{
			System.out.println("Loading");
			for (Entity entity: this.world.loadedEntityList)
			{
				System.out.println(entity.getUniqueID());
			}
			System.out.println(compound.getUniqueId("BoundTo"));
			if (this.world.getMinecraftServer().getEntityFromUuid(compound.getUniqueId("BoundTo")) instanceof EntityAirBallon)
			{
				this.boundTo = (EntityAirBallon)this.world.getEntityByID(compound.getInteger("BoundTo"));
			};
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		if (this.boundTo != null)
		{
			compound.setUniqueId("BoundTo", boundTo.getUniqueID());
		}
	}
}
