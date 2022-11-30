package com.mujmajnkraft.bettersurvival.enchantments;

import java.util.Random;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.integration.SwitchBowCompat;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.items.ItemCrossbow;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EnchantmentMultishot extends Enchantment {
	
	static ResourceLocation switchBow = new ResourceLocation("switchbow", "SwitchBow");
	
	public EnchantmentMultishot() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("multishot");
		this.setName(Reference.MOD_ID + ".multishot");
	}
	
	//Called during ArrowLooseEvent if the bow is enchanted
	public static void shootMoreArrows(World worldIn, EntityPlayer shooter, ItemStack bow, int charge) 
	{
		int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.multishot, bow);
		for (int x=0; x<l; x++)
		{
			float Yaw = new Random().nextFloat()*30 - 15;
			float Pitch = new Random().nextFloat()*30 - 15;
			boolean flag = shooter.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
			ItemStack ammo = ItemStack.EMPTY;
			if (bow.getItem() instanceof ItemCrossbow)
			{
				ItemCrossbow crossbow = (ItemCrossbow) bow.getItem();
				ammo = crossbow.loadedAmmo(bow);
			}
			if (bow.getItem().getRegistryName().equals(switchBow))
			{
				ammo = SwitchBowCompat.findAmmo(shooter, bow);
			}
			else ammo = findAmmo(shooter);
			if (!ammo.isEmpty() || flag)
	        {
	            if (ammo.isEmpty())
	            {
	            	ammo = new ItemStack(Items.ARROW);
	            }
	
	            float f = ItemBow.getArrowVelocity(charge);
	            if ((double)f >= 0.1D)
	            {
	                if (!worldIn.isRemote)
	                {
	                	ItemArrow itemarrow = (ItemArrow)((ItemArrow)(ammo.getItem() instanceof ItemArrow ? ammo.getItem() : Items.ARROW));
	                	EntityArrow entityarrow = itemarrow.createArrow(worldIn, ammo, shooter);
	                	entityarrow.shoot(shooter, shooter.rotationPitch + Pitch, shooter.rotationYaw + Yaw, 0.0F, f * 3.0F, 1.0F);
	
	                	if (f == 1.0F)
	                	{
	                		entityarrow.setIsCritical(true);
	                	}
	
	                	int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
	
	                	if (j > 0)
	                	{
	                	entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
	                	}
	
	                	int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);
	
	                	if (k > 0)
	                	{
	                		entityarrow.setKnockbackStrength(k);
	                	}
	
	                	if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0)
	                	{
	                		entityarrow.setFire(100);
	                	}
	
	                	bow.damageItem(1, shooter);
	
	                	entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	
	                	worldIn.spawnEntity(entityarrow);
	                }
	            }
	        }
		}
	}
	
	static ItemStack findAmmo(EntityPlayer player)
    {
        if (player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemArrow)
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemArrow)
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (itemstack.getItem() instanceof ItemArrow)
                {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20*(enchantmentLevel-1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.multishotlevel;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != ModEnchantments.blast;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.multishot;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.multishotlevel != 0;
    }
}
