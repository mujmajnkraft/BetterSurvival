package com.mujmajnkraft.bettersurvival.entities.siegeweapons;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntitySiegeWeapon extends Entity{
	
	protected boolean leftInputDown;
	protected boolean rightInputDown;
	protected boolean forwardInputDown;
	protected boolean backwardInputDown;
	protected boolean upInputDown;
	protected boolean downInputDown;
	protected boolean actionInputPressed;
	private float deltaRotation;
	public boolean isPullable;
	public float durability;

	public EntitySiegeWeapon(World worldIn) {
		super(worldIn);
		this.preventEntitySpawning = true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public void updatePassenger(Entity passenger) {

		passenger.rotationYaw += this.deltaRotation;
        passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
        this.applyYawToEntity(passenger);
        
		super.updatePassenger(passenger);
	}
	
	@Override
	public Entity getControllingPassenger() {
		if (!this.getPassengers().isEmpty())
		{
			return this.getPassengers().get(0);
		}
		return super.getControllingPassenger();
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
        	boolean leashed = false;
        	List<EntityLiving> list = this.world.<EntityLiving>getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - 7.0D, this.posY - 7.0D, this.posZ - 7.0D, this.posX + 7.0D, this.posY + 7.0D, this.posZ + 7.0D));
        	for (EntityLiving entityliving1 : list)
            {
                if (entityliving1.getLeashed() && entityliving1.getLeashHolder() == this)
                {
                    //entityliving1.clearLeashed(true, false);
                    leashed = true;
                }
            }
        	if (!this.world.isRemote && !leashed)
            {
                player.startRiding(this);
            }
        }
        return true;
    }
	
	@SideOnly(Side.CLIENT)
    public void updateInputs(boolean left, boolean right, boolean forward, boolean backward, boolean up, boolean down)
    {
		this.leftInputDown = left;
        this.rightInputDown = right;
        this.forwardInputDown = forward;
        this.backwardInputDown = backward;
        this.upInputDown = up;
        this.downInputDown = down;
    }
	
	public void performAction()
	{
		
	}
	
	@Override
	public boolean shouldRiderSit() {
		return false;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.isEntityInvulnerable(source))
		{
			this.durability -= amount;
			System.out.println(amount);
			System.out.println(this.durability);
			if (durability <= 0)
			{
				this.setDead();
			}
		}
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public void onEntityUpdate() 
	{
		this.motionY += this.hasNoGravity() ? 0.0D : -0.03999999910593033D;
		if (this.getControllingPassenger() != null)
		{
			if (world.isRemote)
			{
				Control();
			}
		}
		List<EntityLiving> list = this.world.<EntityLiving>getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - 7.0D, this.posY - 7.0D, this.posZ - 7.0D, this.posX + 7.0D, this.posY + 7.0D, this.posZ + 7.0D));
    	for (EntityLiving entityliving1 : list)
        {
            if (entityliving1.getLeashed() && entityliving1.getLeashHolder() == this)
            {
                double x = Math.max(this.getDistanceSq(entityliving1) -2D, 0);
                double dx = entityliving1.posX - this.posX;
                double dz = entityliving1.posZ - this.posZ;
                this.motionX = dx*(x/10);
                this.motionZ = dz*(x/10);
                if (dz < 0)
                	this.rotationYaw = (float) -Math.toDegrees(Math.atan((this.posX-entityliving1.posX)/(this.posZ-entityliving1.posZ)));
                else
                	this.rotationYaw = (float) -Math.toDegrees(Math.atan((this.posX-entityliving1.posX)/(this.posZ-entityliving1.posZ))) + 180;
            }
        }
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9F;
		this.motionY *= 0.9F;
		this.motionZ *= 0.9F;
		this.deltaRotation *= 0.5F; 
		super.onEntityUpdate();
	}
	
	private void Control()
	{
		//System.out.println(this.upInputDown);
		float f = 0.0F;

        if (this.leftInputDown)
        {
            this.deltaRotation += -1.0F;
        }

        if (this.rightInputDown)
        {
            ++this.deltaRotation;
        }

        if (this.forwardInputDown)
        {
            f += 0.005F;
        }
        
        if (this.backwardInputDown)
        {
            f += -0.003F;
        }
        
        if (this instanceof EntityZeppelin)
        {
	        if (this.upInputDown && this.forwardInputDown && this.posY < 120)
	        {
	        	this.motionY += 0.005F;
	        }
	        
	        if (this.downInputDown && this.forwardInputDown)
	        {
	        	this.motionY -= 0.005F;
	        }
        }
        
        this.rotationYaw += this.deltaRotation;
        this.getControllingPassenger().rotationYaw = this.rotationYaw;
        this.motionX += (double)(MathHelper.sin(-this.rotationYaw * 0.017453292F) * f);
        this.motionZ += (double)(MathHelper.cos(this.rotationYaw * 0.017453292F) * f);
	}
	
	protected void applyYawToEntity(Entity entityToUpdate)
    {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }
	
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setFloat("Durability", this.durability);
	}
	
	public void readEntityToNBT(NBTTagCompound compound)
	{
		this.durability = compound.getFloat("Durability");
	}
	
}
