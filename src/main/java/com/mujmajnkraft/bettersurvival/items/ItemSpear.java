package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSpear extends ItemCustomWeapon implements ICustomWeapon {
	
	public ItemSpear(ToolMaterial material) {
		super(material, 0.75F, 1);
		this.setRegistryName(Reference.MOD_ID,"item"+material.name().toLowerCase()+"spear");
		this.setTranslationKey(material.name().toLowerCase()+"spear");
		this.maxStackSize = 16;
		this.setMaxDamage(0);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        boolean flag = playerIn.capabilities.isCreativeMode;
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(itemstack.getItem() instanceof ItemSpear || flag) {
            float f = 1.0f;

            if(!worldIn.isRemote) {
                EntityFlyingSpear entityspear = new EntityFlyingSpear(worldIn, playerIn);
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(1);
                entityspear.setSpear(itemstack1);
                entityspear.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, f * 2.0F, 1.0F);
                
                ItemSpear spear = (ItemSpear)itemstack.getItem();
                entityspear.setDamage(spear.getAttackDamage());

                if(flag) entityspear.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                else entityspear.pickupStatus = EntityArrow.PickupStatus.ALLOWED;

                worldIn.spawnEntity(entityspear);
            }

            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if(!flag)  playerIn.getHeldItem(handIn).shrink(1);

			StatBase stats = StatList.getObjectUseStats(this);
            if(stats != null) playerIn.addStat(stats);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		return super.hitEntity(stack, target, attacker);
	}
	public float breakChance()
	{
		return 32.0f / this.getMaterial().getMaxUses();
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		return enchantment.type.canEnchantItem(stack.getItem());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String s = net.minecraft.client.resources.I18n.format(Reference.MOD_ID + ".spear.desc");
		tooltip.add(TextFormatting.AQUA + s);
	}

	@Override
	public float getReach() {
		return 7.0F;
	}
}