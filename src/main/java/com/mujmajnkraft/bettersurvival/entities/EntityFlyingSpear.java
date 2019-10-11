package com.mujmajnkraft.bettersurvival.entities;

import com.mujmajnkraft.bettersurvival.capabilities.spearsin.ISpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsin.SpearsInProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityFlyingSpear extends EntityArrow {

	
	public ItemStack spear;
	
	public EntityFlyingSpear(World worldIn) {
		super(worldIn);
	}
	
	public EntityFlyingSpear(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter.posX, shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
        this.shootingEntity = shooter;

        if (shooter instanceof EntityPlayer)
        {
            this.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
        }
    }
	
	public boolean isInGround()
	{
		return super.inGround;
	}
	
	public void setSpear(ItemStack spear)
	{
		this.spear = spear;
	}
	
	public ItemStack getSpear()
	{
		return this.spear;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn)
	{
		Entity entity = raytraceResultIn.entityHit;
		if (entity != null)
		{
			boolean flag = true;
			if (entity instanceof EntityPlayer)
			{
				flag = !((EntityPlayer)entity).capabilities.isCreativeMode;
			}
			if (entity instanceof EntityLivingBase && !entity.getIsInvulnerable() && flag)
			{
				if (!(entity instanceof EntityEnderman))
				{
					if (!world.isRemote && this.pickupStatus == PickupStatus.ALLOWED)
					{
						ISpearsIn spearsin = entity.getCapability(SpearsInProvider.SPEARSIN_CAP, null);
						spearsin.addSpear(this.spear);
					}
				}
			}
			else
			{
				if (!world.isRemote && this.pickupStatus == PickupStatus.ALLOWED)
				{
					this.entityDropItem(this.getArrowStack(), 0);
				}
			}
		}
		super.onHit(raytraceResultIn);
    }

	@Override
	protected ItemStack getArrowStack() 
	{
		if (this.spear != null)
		{
			return this.spear;
		}
		else
		{
			return new ItemStack(net.minecraft.init.Items.ARROW);
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setTag("Spear", this.spear.serializeNBT());
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        NBTTagCompound tag = (NBTTagCompound) compound.getTag("Spear");
        this.spear = new ItemStack(tag);
    }
}
