package com.mujmajnkraft.bettersurvival.items;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.CrushingRecipe;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.init.ModPotions;

import net.minecraft.block.BlockGlass;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("deprecation")
public class ItemHammer extends ItemSword implements ICustomWeapon{
	
	private int stunduration;
	private ToolMaterial mat;
	public ItemHammer(ToolMaterial material) {
		super(material);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Hammer");
		this.setUnlocalizedName(material.name().toLowerCase()+"hammer");
		mat = material;
		stunduration = 50;
	}
	
	public ToolMaterial getMaterial()
	{
		return mat;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 1.2);
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 1.35);
		}

		return modifiers;
	}

	/**
	 * Replace a modifier in the {@link Multimap} with a copy that's had {@code multiplier} applied to its value.
	 *
	 * @param modifierMultimap The MultiMap
	 * @param attribute        The attribute being modified
	 * @param id               The ID of the modifier
	 * @param multiplier       The multiplier to apply
	 */
	private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier) {
		// Get the modifiers for the specified attribute
		final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

		// Find the modifier with the specified ID, if any
		final java.util.Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		if (modifierOptional.isPresent()) { // If it exists,
			final AttributeModifier modifier = modifierOptional.get();
			modifiers.remove(modifier); // Remove it
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation())); // Add the new modifier
		}
	}
	
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(hand == EnumHand.OFF_HAND)
		{
			return EnumActionResult.FAIL;
		}
		
		float d = Math.min(this.getAttackDamage(), 9) + 1;
		
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
				for(float y = -d ; y < 5 + 1; y++)
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
		    for (ItemHammer hammer : ModItems.hammers)
		    {
			    playerIn.getCooldownTracker().setCooldown(hammer, 200);
		    }
	    }
	    return EnumActionResult.SUCCESS;
	}
	
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
		if (enchantment instanceof EnchantmentSweepingEdge)
		{
			return false;
		}
		else
		{
			return enchantment.type.canEnchantItem(stack.getItem());
		}
    }

	@Override
	public float getReach() {
		return 0;
	}

	@Override
	public boolean noSweepAttack() {
		return false;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (player.getCooledAttackStrength(0.5F) > 0.9 && entity instanceof EntityLivingBase)
		{
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.bash, stack);
			if (player.getRNG().nextInt(20)<(2+l) && !entity.getIsInvulnerable())
			{
				EntityLivingBase living = (EntityLivingBase) entity;
				PotionEffect potioneffectIn = new PotionEffect(ModPotions.stun, stunduration);
				living.addPotionEffect(potioneffectIn);
			}
			
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("a6107045-134f-4c04-a645-75c3ae5c7a27"), "compensation", 4, 2);
			
			if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(modifier))
			{
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(modifier);
			}
			player.resetCooldown();
		}
		return false;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		target.knockBack(attacker,0.5F, (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
        
		if (this.mat == ModItems.SILVER && Bettersurvival.isIafLoaded)
		{
            if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
            {
                target.attackEntityFrom(DamageSource.MAGIC, ((3.0F + mat.getAttackDamage() + 6.0F) * 1.2F));
            }
        }
		else if (this.mat == ModItems.DESERT_CHITIN || this.mat == ModItems.JUNGLE_CHITIN)
		{
            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD)
            {
                target.attackEntityFrom(DamageSource.GENERIC, ((3.0F + mat.getAttackDamage() + 6.0F) * 1.2F));
            }
        }
		return super.hitEntity(stack, target, attacker);
    }
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		if(ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+this.getMaterial().name()))
		{
			for (ItemStack stack :OreDictionary.getOres("ingot"+this.getMaterial().name()))
			{
				if (net.minecraftforge.oredict.OreDictionary.itemMatches(stack, repair, false))
				{
					return true;
				}
			}
		}
		return super.getIsRepairable(toRepair, repair);
	}
	
	@Override
	public CreativeTabs getCreativeTab()
	{
		if (this.getMaterial() == ToolMaterial.DIAMOND ||this.getMaterial() == ToolMaterial.GOLD ||this.getMaterial() == ToolMaterial.IRON ||this.getMaterial() == ToolMaterial.STONE||this.getMaterial() == ToolMaterial.WOOD)
		{
			return super.getCreativeTab();
		}
		else if ((this.getMaterial() == ModItems.JUNGLE_CHITIN || this.getMaterial() == ModItems.DESERT_CHITIN || this.getMaterial() == ModItems.DRAGON_BONE) && Bettersurvival.isIafLoaded && ConfigHandler.integration)
		{
			return super.getCreativeTab();
		}
		else if (ConfigHandler.integration && !OreDictionary.getOres("ingot"+this.getMaterial().name()).isEmpty())
		{
			return super.getCreativeTab();
		}
		return null;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (this.mat == ModItems.SILVER && Bettersurvival.isIafLoaded)
		{
			String s = I18n.translateToLocal("silvertools.hurt");
			tooltip.add(TextFormatting.GREEN + s);
		}
		else if (this.mat == ModItems.JUNGLE_CHITIN || this.mat == ModItems.DESERT_CHITIN)
		{
			String s = I18n.translateToLocal(Reference.MOD_ID + ".chitintools.hurt");
			tooltip.add(TextFormatting.GREEN + s);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
	

