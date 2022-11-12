package com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ArrowProperties implements IArrowProperties{
	
	private float ExplosionPower;
	private boolean CanDestroyBLocks;
	private boolean NoDrag;
	private boolean CanRecover;

	@Override
	public void setExplosion(float power, boolean canDestroyBlocks) {
		this.ExplosionPower = power;
		this.CanDestroyBLocks = canDestroyBlocks;
		
	}

	@Override
	public void setNoDrag(boolean noDrag) {
		this.NoDrag = noDrag;
		
	}

	@Override
	public float getExplosionPower() {
		return this.ExplosionPower;
	}

	@Override
	public boolean getCanDestroyBlocks() {
		return this.CanDestroyBLocks;
	}

	@Override
	public boolean getNoDrag() {
		return this.NoDrag;
	}

	@Override
	public void setCanRecover(boolean canRecover) {
		this.CanRecover = canRecover;
	}

	@Override
	public boolean getCanRecover() {
		return this.CanRecover;
		
	}
	
	public static void Register()
	{
		CapabilityManager.INSTANCE.register(IArrowProperties.class, new Storage(), new Factory());
	}
	
	private static class Storage implements IStorage<IArrowProperties>{

		@Override
		public NBTBase writeNBT(Capability<IArrowProperties> capability, IArrowProperties instance, EnumFacing side) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setFloat("ExplosionPower", instance.getExplosionPower());
			compound.setBoolean("CanDestroyBlocks", instance.getCanDestroyBlocks());
			compound.setBoolean("NoDrag", instance.getNoDrag());
			compound.setBoolean("CanRecover", instance.getCanRecover());
			return compound;
		}

		@Override
		public void readNBT(Capability<IArrowProperties> capability, IArrowProperties instance, EnumFacing side,NBTBase nbt) {
			instance.setExplosion(((NBTTagCompound)nbt).getFloat("ExplosionPower"), ((NBTTagCompound)nbt).getBoolean("CanDestroyBlocks"));
			instance.setNoDrag(((NBTTagCompound)nbt).getBoolean("NoDrag"));
			instance.setCanRecover(((NBTTagCompound)nbt).getBoolean("CanRecover"));
		}

	}
	
	private static class Factory implements Callable<IArrowProperties>
	{

		@Override
		public IArrowProperties call() throws Exception
		{
			return new ArrowProperties();
		}
	}
	
	public void hitEntity(EntityArrow arrow)
	{
		//Allows arrow recovery to happen
		if (this.getCanRecover() && arrow.shootingEntity != null && arrow.pickupStatus == PickupStatus.ALLOWED)
		{
			if (!arrow.shootingEntity.isDead && arrow.shootingEntity instanceof EntityPlayer)
			{
				EntityPlayer shooter = (EntityPlayer) arrow.shootingEntity;
				ItemStack stack = new ItemStack(Items.ARROW);
				boolean flag = shooter.inventory.addItemStackToInventory(stack);
				if (flag)
				{
					shooter.inventoryContainer.detectAndSendChanges();
				}
				else
				{
					EntityItem entityitem = shooter.dropItem(stack, false);
					entityitem.setNoPickupDelay();
                    entityitem.setOwner(shooter.getName());
				}
			}
		}
		
		//Makes arrow fired from blast bow explode
		if (this.getExplosionPower() > 0)
		{
			arrow.world.newExplosion(arrow.shootingEntity, arrow.posX, arrow.posY, arrow.posZ, this.getExplosionPower(), arrow.isBurning(), false);
			arrow.setDead();
			
			if (arrow instanceof EntityTippedArrow)
			{
				NBTTagCompound compound =  arrow.writeToNBT(new NBTTagCompound());
				ArrayList<PotionEffect> effects = new ArrayList<>();
				if (compound.hasKey("Potion", 8))
		        {
		            PotionType potion = PotionUtils.getPotionTypeFromNBT(compound);
		            for(PotionEffect potioneffect : potion.getEffects())
		            {
		            	effects.add(new PotionEffect(potioneffect.getPotion(), potioneffect.getDuration()/8, potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
		            }
		        }
				for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromTag(compound))
		        {
		            effects.add(potioneffect);
		        }
				
				if (!effects.isEmpty())
		        {
		            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(arrow.world, arrow.posX, arrow.posY, arrow.posZ);
		            entityareaeffectcloud.setRadius(2.5f * this.getExplosionPower());
		            entityareaeffectcloud.setRadiusOnUse(-0.5F);
		            entityareaeffectcloud.setWaitTime(10);
		            entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
		            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

		            for (PotionEffect potioneffect : effects)
		            {
		                entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
		            }

		            arrow.world.spawnEntity(entityareaeffectcloud);
		        }
			}
		}
	}

}
