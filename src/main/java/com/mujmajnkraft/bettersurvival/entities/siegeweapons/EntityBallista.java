package com.mujmajnkraft.bettersurvival.entities.siegeweapons;

import java.util.Random;

import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityBallistaBolt;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityBallista extends EntitySiegeWeapon{

	private boolean isLoaded;
	private float progress;
	private static final DataParameter<Float> PROGRESS = EntityDataManager.<Float>createKey(EntityMinecart.class, DataSerializers.FLOAT);
	
	public EntityBallista(World worldIn) {
		super(worldIn);
		this.setSize(2, 1.5F);
		this.entityCollisionReduction = 1;
		this.stepHeight = 0.6F;
		this.isPullable = true;
		this.durability = 100;
	}
	
	public EntityBallista(World worldIn, double x, double y, double z) {
		super(worldIn);
		this.setPosition(x, y, z);
	}
	
	@Override
	public void updatePassenger(Entity passenger) {
		super.updatePassenger(passenger);
		
		double x = Math.sin(this.rotationYaw * 0.017453292F);
		double y = 0.4D + Math.sin((this.rotationPitch) * 0.017453292F) * 1.2D;
		double z = -Math.cos(this.rotationYaw * 0.017453292F);
		passenger.setPosition(this.posX + x,this.posY + y,this.posZ + z);
	}
	
	@Override
	public Entity getControllingPassenger() {
		if (!this.getPassengers().isEmpty())
		{
			return this.getPassengers().get(0);
		}
		return super.getControllingPassenger();
	}
	
	public float loadingProgress()
	{
		return this.dataManager.get(PROGRESS);
	}
	
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (player.isSneaking())
        {
            return false;
        }
        else if (this.isBeingRidden())
        {
            return true;
        }
        else
        {
            if (!this.world.isRemote)
            {
                player.startRiding(this);
            }

            return true;
        }
    }

	protected void entityInit() {
		this.dataManager.register(PROGRESS, Float.valueOf(0.0F));
	}

	
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.progress = compound.getFloat("Progress");
		this.isLoaded = compound.getBoolean("Loaded");
		
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("Progress", this.progress);
		compound.setBoolean("Loaded", this.isLoaded);
		
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
		
		this.progress = Math.min(this.progress, 1);
		if (this.progress < 1.0F && this.isLoaded) 
		{
			this.progress -= 0.25;
        	this.dataManager.set(PROGRESS, this.progress);
		}
		if (this.progress <= 0.0F && this.isLoaded)
		{
			EntityBallistaBolt bolt = new EntityBallistaBolt(this.world, this);
			bolt.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 6.0F, 1.0F);
			bolt.setDamage(20.0D);
			this.world.spawnEntity(bolt);
			
			this.isLoaded = false;
		}
		
		if (this.progress > 0.0F && !this.isLoaded)
		{
			this.progress += 0.025F;
        	this.dataManager.set(PROGRESS, this.progress);
		}
		if (this.progress >= 1.0F && !this.isLoaded)
		{
			this.isLoaded = true;
		}
		
		super.onEntityUpdate();
	}
	
	@Override
	public void setDead() {

		if (!world.isRemote && world.getGameRules().getBoolean("doEntityDrops"))
		{
			this.dropItem(Item.getItemFromBlock(Blocks.PLANKS), new Random().nextInt(3) + 1);
			this.dropItem(Items.IRON_INGOT, new Random().nextInt(7) + 1);
			this.dropItem(Items.STRING, new Random().nextInt(9) + 1);
		}
		super.setDead();
	}
	
	public boolean load()
	{
		if(!this.isLoaded && this.progress == 0.0F)
		{
			this.progress += 0.025F;
			this.dataManager.set(PROGRESS, this.progress);
			return true;
		}
		return false;
	}

	@Override
	public void performAction() {
		if (this.progress >= 1.0F && this.isLoaded)
        {
        	this.progress -= 0.25F;
			this.dataManager.set(PROGRESS, this.progress);
        }
	}
}
