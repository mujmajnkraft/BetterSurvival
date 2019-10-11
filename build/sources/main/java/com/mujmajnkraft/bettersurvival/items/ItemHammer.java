package com.mujmajnkraft.bettersurvival.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.CrushingRecipe;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.init.ModPotions;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHammer extends ItemSword implements ICustomWeapon{
	
	private int stunduration;
	private ToolMaterial mat;
	public ItemHammer(ToolMaterial material) {
		super(material);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Hammer");
		this.setUnlocalizedName(material.name().toLowerCase()+"hammer");
		mat = material;
		stunduration = 15 + material.getHarvestLevel()*5;
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
		if(hand == EnumHand.OFF_HAND || playerIn.getHeldItemOffhand() != ItemStack.EMPTY)
		{
			return EnumActionResult.FAIL;
		}
		
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
		
	    for (EntityLivingBase entitylivingbase :playerIn.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(xd, yd, zd).expand(-xd, -yd, -zd)))
	    {
	    	if (entitylivingbase != playerIn && !playerIn.isOnSameTeam(entitylivingbase) && entitylivingbase.getDistanceSq(pos) < 20.0D && entitylivingbase.onGround)
            {
                 entitylivingbase.knockBack(playerIn, 0.4F, (double)MathHelper.sin(playerIn.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(playerIn.rotationYaw * 0.017453292F)));
                 entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), (float) ((this.getDamageVsEntity()+3.0F)*(1-entitylivingbase.getDistanceSq(pos)/20)));
                 PotionEffect potioneffectIn = new PotionEffect(ModPotions.stun, (int) (stunduration*(1-entitylivingbase.getDistanceSq(pos)/20)));
                 entitylivingbase.addPotionEffect(potioneffectIn);
            }
        }
	    
	    ArrayList<BlockPos> positions = new ArrayList<>();
	    
	    for(int x = (int) -5; x < 5 + 1; x++)
		{
			if((!(facing == EnumFacing.WEST) && !(facing == EnumFacing.EAST) || x==0) && playerIn.capabilities.allowEdit)
			{
				for(int y = (int) -5; y < 5 + 1; y++)
				{
					if((!(facing == EnumFacing.UP) && !(facing == EnumFacing.DOWN)) || y==0)
					{
						for(int z = (int) -5; z < 5 + 1; z++)
						{
							if ((!(facing == EnumFacing.NORTH) && !(facing == EnumFacing.SOUTH)) || z==0)
							{
								if (x*x+y*y+z*z <= 20.0F)
								{
									BlockPos position = pos.add(x, y, z);
									
									if (worldIn.getBlockState(position.offset(facing)).isSideSolid(worldIn, position, EnumFacing.UP))
									{
										BlockPos position2 = position.offset(facing);
										if (!(worldIn.getBlockState(position2.offset(facing)).isSideSolid(worldIn, position2, EnumFacing.UP)))
										{
											positions.add(position.offset(facing));
										}
									}
									else if(!worldIn.getBlockState(position).isSideSolid(worldIn, position, EnumFacing.UP))
									{
										positions.add(position.offset(facing.getOpposite()));
									}
									else
									{
										positions.add(position);
									}
								}
							}
						}
					}
				}
			}
		}
	    
	    for (BlockPos position : positions)
	    {
	    	IBlockState result = CrushingRecipe.instance().getCrushingResult(worldIn.getBlockState(position));
	    	if (pos.distanceSq(position.getX(), position.getY(), position.getZ()) > 17)
	    	{
	    		if (worldIn instanceof WorldServer)
                {
                    WorldServer worldserver = (WorldServer)worldIn;
                    worldserver.spawnParticle(EnumParticleTypes.CRIT, position.getX(), position.getY()+1.0D, position.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
                }
	    	}
	    	if (result != null)
	    	{
	    		worldIn.setBlockState(position, result);
	    	}
	    }
	    
	    if (!playerIn.isCreative())
	    {
	    	playerIn.getHeldItem(hand).damageItem(10, playerIn);
	    }
	    	
	    worldIn.playSound(null, pos, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
	    worldIn.spawnParticle(EnumParticleTypes.CRIT, pos.getX(), pos.getY()+1, pos.getZ(), 1, 1, 1, 1);
	    
	    for (ItemHammer hammer : ModItems.hammers)
	    {
		    playerIn.getCooldownTracker().setCooldown(hammer, 200);
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
	public boolean isTwoHand() {
		return true;
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
			if (new Random().nextInt(10)==0)
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
        stack.damageItem(1, attacker);
        return true;
    }
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		System.out.println(this.getMaterial());
		if (this.getMaterial() == ToolMaterial.DIAMOND ||this.getMaterial() == ToolMaterial.GOLD ||this.getMaterial() == ToolMaterial.IRON ||this.getMaterial() == ToolMaterial.STONE||this.getMaterial() == ToolMaterial.WOOD)
		{
			return super.getIsRepairable(toRepair, repair);
		}
		else if(ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+this.getMaterial().name()))
		{
			for (ItemStack stack :OreDictionary.getOres("ingot"+this.getMaterial().name()))
			{
				System.out.println(stack);
				if (net.minecraftforge.oredict.OreDictionary.itemMatches(stack, repair, false))
				{
					return true;
				}
			}
		}
		return false;
	}
}
	

