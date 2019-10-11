package com.mujmajnkraft.bettersurvival.items;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemSpear extends Item implements ICustomWeapon{

    private final float attackDamage;
	private ToolMaterial mat;
	
	public ItemSpear(ToolMaterial material) {
		this.setRegistryName("Item"+material.name().toLowerCase()+"Spear");
		this.setUnlocalizedName(material.name().toLowerCase()+"spear");
		this.maxStackSize = 16;
		this.attackDamage = (float) (0.75 * (3.0F + material.getDamageVsEntity()));
		this.setCreativeTab(CreativeTabs.COMBAT);
		mat = material;
	}
	
	public ToolMaterial getMaterial()
	{
		return mat;
	}
	
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        boolean flag = playerIn.capabilities.isCreativeMode;
        ItemStack itemstack = playerIn.getHeldItemMainhand();

        if (!itemstack.isEmpty() || flag)
        {
            float f = 1.0f;
            
            boolean flag1 = playerIn.capabilities.isCreativeMode;

            if (!worldIn.isRemote)
            {
                EntityFlyingSpear entityspear = new EntityFlyingSpear(worldIn, playerIn);
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(1);
                entityspear.setSpear(itemstack1);
                entityspear.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, f * 2.0F, 1.0F);
                
                ItemSpear spear =(ItemSpear) itemstack.getItem();
                entityspear.setDamage((spear.getMaterial().getDamageVsEntity()+3)*0.75);

                if (flag1)
                {
                	entityspear.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                }
                else{
                entityspear.pickupStatus = EntityArrow.PickupStatus.ALLOWED;}

                worldIn.spawnEntity(entityspear);
            }

            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (!flag1)
            {
                playerIn.getHeldItem(handIn).shrink(1);
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
	
	@Override
	public float getReach() {
		return 7.0F;
	}

	@Override
	public boolean isTwoHand() 
	{
		return false;
	}

	@Override
	public boolean noSweepAttack() {
		return false;
	}
	
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
	
	public float breakChance()
	{
		return 32.0f / this.mat.getMaxUses();
	}
}