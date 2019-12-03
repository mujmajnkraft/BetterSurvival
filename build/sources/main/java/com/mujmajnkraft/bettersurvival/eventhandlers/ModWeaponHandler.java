package com.mujmajnkraft.bettersurvival.eventhandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProwider;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.ISpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.SpearsInProvider;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.IWeaponEffect;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.WeaponEffectProvider;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityPotionThrower;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntitySiegeWeapon;
import com.mujmajnkraft.bettersurvival.init.ModBlocks;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModWeaponHandler {
	
	public static final ResourceLocation SPEARSIN_CAP = new ResourceLocation(Reference.MOD_ID, "spearsin");
	public static final ResourceLocation WEAPONEWWECT_CAP = new ResourceLocation(Reference.MOD_ID, "weaponeff");
	public static final ResourceLocation NUNCHAKUCOMBO_CAP = new ResourceLocation(Reference.MOD_ID, "nunchakucombo");
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(RightClickItem event)
	{
		if (event.getEntityPlayer().getRidingEntity() instanceof EntityPotionThrower)
		{
			EntityPotionThrower thrower = (EntityPotionThrower) event.getEntityPlayer().getRidingEntity();
			if (event.getItemStack().getItem() == Items.SPLASH_POTION || event.getItemStack().getItem() == Items.LINGERING_POTION)
			{
				if (thrower.load(event.getItemStack()))
				{
					//event.getItemStack().shrink(1);
					//event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(PlayerInteractEvent.EntityInteract event)
	{
		if (event.getItemStack().getItem() == Items.LEAD && event.getTarget() instanceof EntitySiegeWeapon)
		{
			EntitySiegeWeapon entity = (EntitySiegeWeapon) event.getTarget();
			if (entity.isPullable)
			{
		        double i = entity.posX;
		        double j = entity.posY;
		        double k = entity.posZ;
				for (EntityLiving entityliving : event.getWorld().getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D)))
		        {
		            if (entityliving.getLeashed() && entityliving.getLeashHolder() == event.getEntityPlayer() && entityliving instanceof EntityHorse)
		            {
		                entityliving.clearLeashed(true, false);
		                entityliving.setLeashHolder(entity, true);
		            }
		        }
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		if (living instanceof EntityPlayer)
		{	
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(UUID.fromString("a6107045-134f-4c04-a645-75c3ae5c7a27"));
			
			if (player.hasCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null))
			{
				INunchakuCombo combo = player.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null);
				if (player.getHeldItemMainhand().getItem() instanceof ItemNunchaku)
				{
					if (combo.getComboPower() > 0) combo.countDown();
				}
				else
				{
					combo.setComboTime(0);
				}
			}
		}
		
		//Makes blindness effect mobs as well
		else
		{
			if (event.getEntityLiving() instanceof EntityLiving)
			{
				event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27"));
				if (!event.getEntityLiving().getActivePotionEffects().isEmpty())
				{
					Collection<PotionEffect> effects = event.getEntityLiving().getActivePotionEffects();
					for (PotionEffect effect : effects)
					{
						if (effect.getEffectName() == "effect.blindness")
						{
							AttributeModifier modifier = new AttributeModifier(UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27"), "blind",-0.8, 1);
							event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(modifier);
						}
					}
				}
			}
		}
	}
	
	//Applies poison from weapon to target upon hitting it
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingAttackEvent event)
	{	
		if (event.getSource().getImmediateSource() !=null)
		{
			if (event.getSource().getImmediateSource() instanceof EntityLivingBase)
			{
				EntityLivingBase attacker = (EntityLivingBase) event.getSource().getImmediateSource();
				if (attacker.getHeldItemMainhand().getItem() instanceof ItemSpear && attacker instanceof EntityPlayer)
				{
					if (!((EntityPlayer)attacker).capabilities.isCreativeMode && ((ItemSpear)attacker.getHeldItemMainhand().getItem()).breakChance() >= attacker.getRNG().nextFloat())
					{
						attacker.getHeldItemMainhand().shrink(1);
					}
				}
				if (attacker.getHeldItemMainhand().getItem() instanceof ItemSword && !attacker.world.isRemote)
				{
					IWeaponEffect poison = attacker.getHeldItemMainhand().getCapability(WeaponEffectProvider.WEAPONEFFECT_CAP, null);
					int h = 0;
					if (attacker.getHeldItemMainhand().hasTagCompound())
					{
						NBTTagCompound compound = attacker.getHeldItemMainhand().getTagCompound();
						if (compound.hasKey("remainingHits"));
						{
							h = compound.getInteger("remainingHits");
						}
						
						if (h > 0 && event.getEntityLiving().hurtResistantTime<10)
						{
							for (PotionEffect effect : PotionUtils.getEffectsFromStack(attacker.getHeldItemMainhand()))
							{
								if (effect.getPotion().isInstant())
								{
									effect.getPotion().affectEntity(null, event.getSource().getImmediateSource(), event.getEntityLiving(), effect.getAmplifier(), 1/6D);
									event.getEntityLiving().hurtResistantTime = 0;
								}
								else
								{
									event.getEntityLiving().addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()/8, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
								}
							}
							
							if (attacker instanceof EntityPlayer)
							{
								if (!((EntityPlayer) attacker).capabilities.isCreativeMode)
								{
									compound.setInteger("remainingHits", h-1);
								}
							}
							if (h-1 <= 0)
							{
								compound.removeTag("Potion");
								compound.removeTag("CustomPotionEffects");
							}
						}
					}
				}
			}
			
			//Prevents sweep attack for some weapons
			if (event.getSource().getImmediateSource() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();
				if (player.getHeldItemMainhand().getItem() instanceof ICustomWeapon)
				{
					ICustomWeapon weapon = (ICustomWeapon) player.getHeldItemMainhand().getItem();
					if (event.getAmount() == 1 && weapon.noSweepAttack())
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	//Prevents stunt entity from jumping
	@SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
	public void onEvent(LivingJumpEvent event)
	{
		if(event.getEntityLiving().getActivePotionEffect(ModPotions.stun)!=null)
		{
			event.getEntityLiving().motionY=0;
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onAttachCapabilityEntityEvent(AttachCapabilitiesEvent<Entity> event)
	{
		if ((!event.getObject().hasCapability(SpearsInProvider.SPEARSIN_CAP, null))&&(event.getObject() instanceof EntityLivingBase))
		{
			event.addCapability(SPEARSIN_CAP, new SpearsInProvider());
		}
		
		if ((!event.getObject().hasCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null))&&(event.getObject() instanceof EntityLivingBase))
		{
			event.addCapability(NUNCHAKUCOMBO_CAP, new NunchakuComboProwider());
		}
		
		if ((!event.getObject().hasCapability(WeaponEffectProvider.WEAPONEFFECT_CAP, null))&&(event.getObject() instanceof EntityLivingBase))
		{
			event.addCapability(WEAPONEWWECT_CAP, new WeaponEffectProvider());
		}
	}
	
	//Allows daggers and nunchakus to do additional damage
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingHurtEvent event)
	{	
		Entity entitysource = event.getSource().getImmediateSource();
		if (entitysource instanceof EntityLivingBase && !entitysource.world.isRemote)
		{
			EntityLivingBase living = (EntityLivingBase) entitysource;
			if (living.getHeldItemMainhand()!=null)
			{
				if (living.getHeldItemMainhand().getItem() instanceof ItemDagger)
				{
					double attackerYaw = Math.toRadians(living.rotationYaw);
					double targetYaw = Math.toRadians(event.getEntity().rotationYaw);
					if (Math.abs(Math.sin(attackerYaw)-Math.sin(targetYaw))< 0.5D && Math.abs(Math.cos(attackerYaw)-Math.cos(targetYaw))< 0.5D)
					{
						int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.assassinate, living.getHeldItemMainhand());
						float newdamage = (float) (event.getAmount()*(2+l/3));
						event.setAmount(newdamage);
					}
				}
				if (entitysource instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();
					if (player.getHeldItemMainhand().getItem() instanceof ItemNunchaku)
					{
						if (player.hasCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null))
						{
							INunchakuCombo combo = player.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null);
							event.setAmount(event.getAmount() * (1+combo.getComboPower()));
							int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.combo, player);
							combo.setComboPower(combo.getComboPower() + 0.1F + l/20F);
						}
					}
				}
			}
		}
	}
	
	//Makes entity with spears inside t drop them upon it's death
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingDeathEvent event)
	{
		ISpearsIn spearsIn = event.getEntity().getCapability(SpearsInProvider.SPEARSIN_CAP, null);
		ArrayList<ItemStack> spears = spearsIn.getSpearsIn();
		if (!spears.isEmpty())
		{
		for (ItemStack spear : spears)
			{
				event.getEntity().entityDropItem(spear, 0);
			}
		}
	}
	
	//Prevents teleportation for entities with antiwarp effect
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(EnderTeleportEvent event)
	{
		if (event.getEntityLiving().getActivePotionEffect(ModPotions.antiwarp)!=null)
		{
			event.setCanceled(true);
		}
	}
	
	//Allows player to put potion or milk into cauldron
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(RightClickBlock event)
	{
		if (event.getItemStack() != ItemStack.EMPTY)
		{
			Item item = event.getItemStack().getItem();
			IBlockState state = event.getWorld().getBlockState(event.getPos());
			if (state.getBlock() == Blocks.CAULDRON && !event.getWorld().isRemote)
			{
				if ((Integer)state.getValue(BlockCauldron.LEVEL).intValue() == 0)
				{
					if (item == Items.MILK_BUCKET || (item == Items.POTIONITEM && (!PotionUtils.getEffectsFromStack(event.getItemStack()).isEmpty() || PotionUtils.getPotionFromItem(event.getItemStack()) != PotionTypes.WATER)))
					{
						event.getWorld().setBlockState(event.getPos(), ModBlocks.customcauldron.getDefaultState());
					}
				}
			}
		}
	}
	
	

}
