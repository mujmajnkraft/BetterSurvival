package com.mujmajnkraft.bettersurvival.entities.siegeweapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityPotionThrower extends EntitySiegeWeapon {

	private ItemStack Ammo = ItemStack.EMPTY;
	private boolean isLoaded;
	private float progress;
	private static DataParameter<ItemStack> POTION = EntityDataManager.createKey(EntityPotionThrower.class, DataSerializers.ITEM_STACK);
	private static final DataParameter<Float> PROGRESS = EntityDataManager.<Float>createKey(EntityPotionThrower.class, DataSerializers.FLOAT);
	
	public EntityPotionThrower(World worldIn) {
		super(worldIn);
		this.setSize(2, 1.5F);
		this.entityCollisionReduction = 1;
	}
	
	@Override
	public void updatePassenger(Entity passenger) {
		if (this.isPassenger(passenger))
        {
			double x = Math.sin(this.rotationYaw * 0.017453292F);
			double y = 0.4D + Math.sin((this.rotationPitch) * 0.017453292F) * 1.2D;
			double z = -Math.cos(this.rotationYaw * 0.017453292F);
			passenger.setPosition(this.posX + x,this.posY + y,this.posZ + z);
        }
	}
	
	@Override
	public void onEntityUpdate()
	{
		BlockPos pos = this.getPosition().add(0, -1, 0);
		if (!world.getBlockState(pos).isSideSolid(world, pos, EnumFacing.UP) && !this.getIsInvulnerable())
		{
			this.setDead();
		}
		if (this.getControllingPassenger() != null)
		{
			float Pitch = this.getControllingPassenger().rotationPitch;
			float rotationPitch = Pitch > 0 ? Math.min(45, Pitch) : Math.max(Pitch, -45);
			this.rotationPitch = rotationPitch - 10;
			this.rotationYaw = this.getControllingPassenger().rotationYaw;
		}
		if (this.isLoaded && this.progress <1)
		{
			this.progress -= 0.25F;
			this.dataManager.set(PROGRESS, this.progress);
		}
		if (!this.isLoaded && this.progress > 0)
		{
			this.progress += 0.025F;
			this.dataManager.set(PROGRESS, this.progress);
		}
		if (this.progress >= 1.0F) this.isLoaded = true;
		if (progress <=0 && this.isLoaded)
		{
			this.isLoaded = false;
			System.out.println(Ammo);
			if (this.Ammo.getItem() == Items.SPLASH_POTION || this.Ammo.getItem() == Items.LINGERING_POTION)
			{
				if (!this.world.isRemote)
				{
					if (this.getControllingPassenger() instanceof EntityLivingBase)
					{
						EntityPotion potion = new EntityPotion(this.world, (EntityLivingBase)this.getControllingPassenger(), this.Ammo);
						potion.setHeadingFromThrower(this, this.rotationPitch, this.rotationYaw, 0, 3.0F, 0.1F);
						double x = Math.sin(this.rotationYaw * 0.017453292F);
						double y = 0.4D + Math.sin((this.rotationPitch) * 0.017453292F) * 1.2D;
						double z = -Math.cos(this.rotationYaw * 0.017453292F);
						potion.setPosition(this.posX - x,this.posY,this.posZ - z);
						world.spawnEntity(potion);
					}
					else
					{
						EntityPotion potion = new EntityPotion(this.world, this.posX, this.posY, this.posZ, this.Ammo);
						potion.setHeadingFromThrower(this, this.rotationPitch, this.rotationYaw, 0, 3.0F, 0.1F);
						world.spawnEntity(potion);
					}
				}
			}
			this.Ammo= ItemStack.EMPTY;
			this.progress = 0;
			this.dataManager.set(PROGRESS, 0.0F);
			this.dataManager.set(POTION, ItemStack.EMPTY);
		}
	}
	
	public float loadingProgress()
	{
		return this.dataManager.get(PROGRESS);
	}
	
	public boolean load (ItemStack stack)
	{
		System.out.println(this.Ammo);
		if ((stack.getItem() == Items.SPLASH_POTION || stack.getItem() == Items.LINGERING_POTION) && this.Ammo.isEmpty())
		{
			this.Ammo = stack;
			System.out.println(this.Ammo);
			this.dataManager.set(POTION, stack);
			this.progress = 0.025F;
			return true;
		}
		return false;
	}
	
	public ItemStack getPotion()
	{
		return this.dataManager.get(POTION);
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(PROGRESS, Float.valueOf(0.0F));
		this.dataManager.register(POTION, ItemStack.EMPTY);
		
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("Ammo")) this.Ammo = new ItemStack(compound.getCompoundTag("Ammo"));
		this.isLoaded = compound.getBoolean("Loaded");
		this.progress = compound.getFloat("Progress");
		
	}
	
	@Override
	public void performAction() {
		
		if (this.progress >= 1.0F && this.Ammo != ItemStack.EMPTY)
		{
			this.progress -= 0.25F;
			this.dataManager.set(PROGRESS, this.progress);
		}
		
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		if (this.Ammo != ItemStack.EMPTY)
		{
			NBTTagCompound nbt = Ammo.serializeNBT();
			compound.setTag("Ammo", nbt);
		}
		compound.setBoolean("Loaded", this.isLoaded);
		compound.setFloat("Progress", this.progress);
		
	}

}
