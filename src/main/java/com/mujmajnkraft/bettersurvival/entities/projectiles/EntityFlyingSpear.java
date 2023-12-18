package com.mujmajnkraft.bettersurvival.entities.projectiles;

import com.google.common.base.Optional;
import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.integration.InFCompat;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.ISpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.SpearsInProvider;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityFlyingSpear extends EntityArrow {

	private static DataParameter<ItemStack> SPEAR = EntityDataManager.createKey(EntityFlyingSpear.class, DataSerializers.ITEM_STACK);
	private static DataParameter<Optional<UUID>> SHOOTER = EntityDataManager.createKey(EntityFlyingSpear.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public EntityFlyingSpear(World worldIn) {
		super(worldIn);
	}
	public EntityFlyingSpear(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter.posX, shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);

        if(shooter instanceof EntityPlayer) this.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
    }

	public void setSpear(ItemStack spear) {
		dataManager.set(SPEAR, spear);
	}
	
	public ItemStack getSpear() {
		return dataManager.get(SPEAR);
	}

	//Arrows don't actually sync the shooter, which is why arrows can hit the player if they're moving too fast, have to sync it ourselves
	public void setShooter(Entity entity) {
		this.shootingEntity = entity;
		UUID id = entity == null ? null : entity.getUniqueID();
		dataManager.set(SHOOTER, Optional.fromNullable(id));
	}

	@Nullable
	public Entity getShooter() {
		UUID id = (UUID)((Optional<?>)this.dataManager.get(SHOOTER)).orNull();
		this.shootingEntity = id == null ? null : this.world.getPlayerEntityByUUID(id);
		return this.shootingEntity;
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		Entity entity = raytraceResultIn.entityHit;
		if(entity != null) {
			boolean flag = true;
			if(entity instanceof EntityPlayer) flag = !((EntityPlayer)entity).capabilities.isCreativeMode;
			if(entity instanceof EntityLivingBase && !entity.getIsInvulnerable() && flag) {
				if(!(entity instanceof EntityEnderman) && this.getSpear().getItem() instanceof ItemSpear) {
					float matModifier = 0;
					if(BetterSurvival.isIafLoaded) matModifier = InFCompat.getMaterialModifier(getSpear(), (EntityLivingBase)entity, null, false);
					this.setDamage(this.getDamage() + matModifier);
				}
			}
		}
		super.onHit(raytraceResultIn);
    }

	@Nullable
	@Override
	protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
		Entity entity = super.findEntityOnPath(start, end);
		if(entity == this.getShooter() && this.ticksExisted <= 10) return null;//Don't hit self if moving forward
		if(entity instanceof EntityLivingBase && BetterSurvival.isIafLoaded && InFCompat.isStoned((EntityLivingBase)entity)) return null;//Don't target stoned entities, causes bugs/dupes
		return entity;
	}

	@Override
	protected void arrowHit(EntityLivingBase living) {
		if(!living.world.isRemote && this.getSpear().getItem() instanceof ItemSpear) {
			if(BetterSurvival.isIafLoaded) {
				InFCompat.getMaterialModifier(getSpear(), living, null, true);
			}
			if(this.pickupStatus == PickupStatus.ALLOWED) {
				if(((ItemSpear)this.getSpear().getItem()).breakChance() < this.rand.nextFloat()) {
					ISpearsIn spearsin = living.getCapability(SpearsInProvider.SPEARSIN_CAP, null);
					if(spearsin != null && living.isEntityAlive()) spearsin.addSpear(this.getSpear());
					else this.entityDropItem(this.getSpear(), 0.1F);//Manually drop it if capability is null or entity is dead, otherwise they can just get voided
				}
			}
		}
	}

	@Override
	protected ItemStack getArrowStack() {
		if(this.getSpear() != null) return this.getSpear();
		return ItemStack.EMPTY;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(SPEAR, ItemStack.EMPTY);
		this.dataManager.register(SHOOTER, Optional.absent());
		super.entityInit();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
        compound.setTag("Spear", this.getSpear().serializeNBT());
		if(this.getShooter()!=null) {
			compound.setString("shooter", this.getShooter().getUniqueID().toString());
		}
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagCompound tag = (NBTTagCompound) compound.getTag("Spear");
        this.setSpear(new ItemStack(tag));
		if(compound.hasKey("shooter")) {
			this.setShooter(this.world.getPlayerEntityByUUID(UUID.fromString(compound.getString("shooter"))));
		}
    }
}