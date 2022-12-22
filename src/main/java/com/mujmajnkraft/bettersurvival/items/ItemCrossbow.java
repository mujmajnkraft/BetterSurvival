package com.mujmajnkraft.bettersurvival.items;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrossbow extends ItemBow {
	
	public ItemCrossbow()
	{
		this.setMaxDamage(768);
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn == null ? 0.0F : (entityIn.getActiveItemStack().getItem() != ModItems.crossbow ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / ForgeConfigHandler.weapons.crossbowSpd);
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
		this.addPropertyOverride(new ResourceLocation("loaded"), new IItemPropertyGetter()
        {
			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				return loadedAmmo(stack) == null ? 0 : 1;
			}
        });
	}
	
	public void setLoadedAmmo(ItemStack arrow, ItemStack crossbow)
	{
		if (arrow != null)
		{
			NBTTagCompound nbt = arrow.serializeNBT();
			if (crossbow.hasTagCompound())
			{
				NBTTagCompound compound = crossbow.getTagCompound();
				compound.setTag("AmmoIn", nbt);
				compound.setBoolean("Ready", false);
			}
			else
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setTag("AmmoIn", nbt);
				compound.setBoolean("Ready", false);
				crossbow.setTagCompound(compound);
			}
		}
		else
		{
			NBTTagCompound compound = crossbow.getTagCompound();
			compound.removeTag("AmmoIn");
		}
	}
	
	public ItemStack loadedAmmo(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("AmmoIn"))
			{
				NBTTagCompound tag = compound.getCompoundTag("AmmoIn");
				ItemStack ArrowIn = new ItemStack(tag);
				return ArrowIn;
			}
			else return null;
		}
		else return null;
	}
	
	protected ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack))
                {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);
            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }
                boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                if (!worldIn.isRemote)
                {
                    ItemStack arrowstack = ((itemstack.getItem() instanceof ItemArrow ? itemstack : new ItemStack(Items.ARROW)));
                    arrowstack.setCount(1);
                    this.setLoadedAmmo(arrowstack, entityLiving.getActiveItemStack());
                }
                if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                {
                    itemstack.shrink(1);

                    if (itemstack.isEmpty())
                    {
                        entityplayer.inventory.deleteStack(itemstack);
                    }
                }
            }
        }
        return stack;
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
		if (entityLiving instanceof EntityPlayer && (this.getMaxItemUseDuration(stack)-timeLeft > ForgeConfigHandler.weapons.crossbowSpd))
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);
            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }
                boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                if (!worldIn.isRemote)
                {
                    ItemStack arrowstack = ((itemstack.getItem() instanceof ItemArrow ? itemstack.copy() : new ItemStack(Items.ARROW)));
                    arrowstack.setCount(1);
                    this.setLoadedAmmo(arrowstack, stack);
                    
                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }
                }
            }
        }
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (this.loadedAmmo(itemstack) != null)
        {
        	ItemStack ammo = this.loadedAmmo(itemstack);
        	boolean flag1 = playerIn.capabilities.isCreativeMode || (ammo.getItem() instanceof ItemArrow && ((ItemArrow) ammo.getItem()).isInfinite(ammo, itemstack, playerIn));
        	
        	if (!worldIn.isRemote)
        	{
        		ItemArrow itemarrow = (ItemArrow) this.loadedAmmo(itemstack).getItem();
        		EntityArrow entityarrow = itemarrow.createArrow(worldIn, this.loadedAmmo(itemstack), playerIn);
        		entityarrow.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F * ForgeConfigHandler.weapons.crossbowDmgMod, 1.0F);

        		int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemstack);

        		if (j > 0)
        		{
        			entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
        		}

        		int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemstack);

        		if (k > 0)
        		{
        			entityarrow.setKnockbackStrength(k);
        		}

        		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemstack) > 0)
        		{
        			entityarrow.setFire(100);
        		}

        		itemstack.damageItem(1, playerIn);

        		if (flag1 || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0 || playerIn.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
        		{
        			entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
        		}
        		net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemstack, playerIn.world, playerIn, 20, true);
        		worldIn.spawnEntity(entityarrow);
        		this.setLoadedAmmo(null, itemstack);
        	}

        	worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
        	playerIn.getCooldownTracker().setCooldown(this, 10);
        	
        	return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
        else
        {
        	boolean flag = !this.findAmmo(playerIn).isEmpty();

        	ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        	if (ret != null) return ret;

        	if (!playerIn.capabilities.isCreativeMode && !flag)
        	{
        		return flag ? new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack) : new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        	}
        	else
        	{
        		playerIn.setActiveHand(handIn);
        		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        	}
        }
    }
}
