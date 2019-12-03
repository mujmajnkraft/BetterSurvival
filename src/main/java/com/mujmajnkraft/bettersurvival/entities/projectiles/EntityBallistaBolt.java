package com.mujmajnkraft.bettersurvival.entities.projectiles;

import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBallistaBolt extends EntityArrow{

	public EntityBallistaBolt(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}
	
	public EntityBallistaBolt(World worldIn, Entity shooter)
    {
        super(worldIn, shooter.posX - 2 * MathHelper.sin(shooter.rotationYaw * 0.017453292F), shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ + 2 * MathHelper.cos(shooter.rotationYaw * 0.017453292F));
        if (shooter.getControllingPassenger() instanceof EntityLivingBase)
        {
        	EntityLivingBase entity = (EntityLivingBase) shooter.getControllingPassenger();
        	this.shootingEntity = entity;

            if (entity instanceof EntityPlayer)
            {
                this.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
            }
        }
    }

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(ModItems.ballistabolt);
	}
	
	@Override
	public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy)
	{
		float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F) * 2;
        float f1 = -MathHelper.sin(pitch * 0.017453292F) * 2;
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F) * 2;
        super.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;

        if (!shooter.onGround)
        {
            this.motionY += shooter.motionY;
        }
	}

}
