package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.CrushingRecipe;
import com.mujmajnkraft.bettersurvival.InFCompat;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.init.ModPotions;

import net.minecraft.block.BlockGlass;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemHammer extends ItemCustomWeapon{
	
	public final int stunduration;
	public ItemHammer(ToolMaterial material) {
		super(material, 1.2F, 1.35F);
		this.setRegistryName(Reference.MOD_ID,"item"+material.name().toLowerCase()+"hammer");
		this.setTranslationKey(material.name().toLowerCase()+"hammer");
		stunduration = 50;
	}
	
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(hand == EnumHand.OFF_HAND)
		{
			return EnumActionResult.FAIL;
		}
		
		float d = Math.min(super.getAttackDamage(), 9) + 1;
		
		double xd = 1;
		double yd = 1;
		double zd = 1;
		
		if (facing == EnumFacing.UP || facing == EnumFacing.DOWN)
		{
			xd += 9;
			zd += 9;
		}else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
		{
			yd += 9;
			zd += 9;
		}else if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
		{
			xd += 9;
			yd += 9;
		}
		
	    for (EntityLivingBase entitylivingbase :playerIn.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(xd, yd, zd)))
	    {
	    	if (entitylivingbase != playerIn && entitylivingbase.getDistanceSq(pos) < 2*d && entitylivingbase.onGround)
            {
                 entitylivingbase.knockBack(playerIn, 0.4F, (double)MathHelper.sin(playerIn.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(playerIn.rotationYaw * 0.017453292F)));
                 entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), (float) ((this.getAttackDamage()/2.0F + 1.5F)*(1-entitylivingbase.getDistanceSq(pos)/20.0F)));
                 PotionEffect potioneffectIn = new PotionEffect(ModPotions.stun, stunduration);
                 entitylivingbase.addPotionEffect(potioneffectIn);
            }
        }
	    
	    for(int x = (int) -5; x < 5 + 1; x++)
		{
			if((!(facing == EnumFacing.WEST) && !(facing == EnumFacing.EAST) || x==0) && playerIn.capabilities.allowEdit)
			{
				for(float y = -5 ; y < 5 + 1; y++)
				{
					if((!(facing == EnumFacing.UP) && !(facing == EnumFacing.DOWN)) || y==0)
					{
						for(int z = (int) -5; z < 5 + 1; z++)
						{
							if ((!(facing == EnumFacing.NORTH) && !(facing == EnumFacing.SOUTH)) || z==0)
							{
								if (x*x+y*y+z*z <= (float) 2.0f * d)
								{
									boolean particles = x*x+y*y+z*z >= (float) 2.0f * (d - 2.0f);
									BlockPos position = pos.add(x, y, z);
									
									if (worldIn.getBlockState(position.offset(facing)).getBlock() instanceof BlockGlass)
									{
										worldIn.destroyBlock(position.offset(facing), true);
									}
									
									if (worldIn.getBlockState(position.offset(facing.getOpposite())).getBlock() instanceof BlockGlass)
									{
										worldIn.destroyBlock(position.offset(facing.getOpposite()), true);
									}
									
									if (worldIn.getBlockState(position).getBlock() instanceof BlockGlass)
									{
										worldIn.destroyBlock(position, true);
									}
									
									if (worldIn.getBlockState(position.offset(facing)).isSideSolid(worldIn, position, EnumFacing.UP))
									{
										BlockPos pos2 = position.offset(facing);
										if (!(worldIn.getBlockState(pos2.offset(facing)).isSideSolid(worldIn, pos2, EnumFacing.UP)))
										{
											CrushingRecipe.Crush(playerIn, pos2, particles);
										}
									}
									else if(!worldIn.getBlockState(position).isSideSolid(worldIn, position, EnumFacing.UP))
									{
										BlockPos pos2 = position.offset(facing.getOpposite());
										CrushingRecipe.Crush(playerIn, pos2, particles);
									}
									else
									{
										CrushingRecipe.Crush(playerIn, position, particles);
									}
								}
							}
						}
					}
				}
			}
		}
	    
	    if (!playerIn.isCreative())
	    {
	    	playerIn.getHeldItem(hand).damageItem(10, playerIn);
	    }
	    
	    worldIn.playSound(null, pos, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
	    
	    if (!playerIn.capabilities.isCreativeMode)
	    {
			playerIn.getCooldownTracker().setCooldown(this, 200);
	    }
	    return EnumActionResult.SUCCESS;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String s = net.minecraft.client.resources.I18n.format(Reference.MOD_ID + ".hammer.desc");
		tooltip.add(TextFormatting.AQUA + s);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		target.knockBack(attacker,0.5F, (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
        return super.hitEntity(stack, target, attacker);
    }
}