package com.mujmajnkraft.bettersurvival.entities.projectiles;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.ISpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.SpearsInProvider;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityFlyingSpear extends EntityArrow {

	public ItemStack spear;
	private static DataParameter<ItemStack> SPEAR = EntityDataManager.createKey(EntityFlyingSpear.class, DataSerializers.ITEM_STACK);
	
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
		dataManager.set(SPEAR, spear);
	}
	
	public ItemStack getSpear()
	{
		return dataManager.get(SPEAR);
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
					EntityLivingBase target = (EntityLivingBase) entity;
					if (this.getSpear().getItem() instanceof ItemSpear)
					{
						ItemSpear spear = (ItemSpear) this.getSpear().getItem();
						if (spear.getMaterial() == ModItems.SILVER && Bettersurvival.isIafLoaded)
						{
				            if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
				            {
				                target.attackEntityFrom(DamageSource.MAGIC, ((3.0F + spear.getMaterial().getAttackDamage() + 6.0F) * 0.75F));
				            }
				        }
						else if (spear.getMaterial() == ModItems.DESERT_CHITIN || spear.getMaterial() == ModItems.JUNGLE_CHITIN)
						{
				            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD)
				            {
				                target.attackEntityFrom(DamageSource.GENERIC, ((3.0F + spear.getMaterial().getAttackDamage() + 6.0F) * 0.75F));
				            }
				        }
					}
					if (!world.isRemote && this.pickupStatus == PickupStatus.ALLOWED && this.spear.getItem() instanceof ItemSpear)
					{
						if (((ItemSpear)this.spear.getItem()).breakChance() < this.rand.nextFloat())
						{
							ISpearsIn spearsin = entity.getCapability(SpearsInProvider.SPEARSIN_CAP, null);
							spearsin.addSpear(this.spear);
						}
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
	protected void entityInit() {
		this.dataManager.register(SPEAR, ItemStack.EMPTY);
		super.entityInit();
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
