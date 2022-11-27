package com.mujmajnkraft.bettersurvival.eventhandlers;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.mujmajnkraft.bettersurvival.EntityAIDoNothing;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesProvider;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProwider;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.ISpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsinentity.SpearsInProvider;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.WeaponEffectProvider;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentAgility;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentArrowRecovery;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentBlast;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentDiamonds;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentEducation;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentHighJump;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentMultishot;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentPenetration;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentRange;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentRapidFire;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentTunneling;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVersatility;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVitality;
import com.mujmajnkraft.bettersurvival.enchantments.EnchatnmentSmelting;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

public class CommonEventHandler {
	
public static final ResourceLocation ARROWPROPERTIES_CAP = new ResourceLocation(Reference.MOD_ID, "ArrowProperties");
public static final ResourceLocation SPEARSIN_CAP = new ResourceLocation(Reference.MOD_ID, "spearsin");
public static final ResourceLocation WEAPONEWWECT_CAP = new ResourceLocation(Reference.MOD_ID, "weaponeff");
public static final ResourceLocation NUNCHAKUCOMBO_CAP = new ResourceLocation(Reference.MOD_ID, "nunchakucombo");
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(AttachCapabilitiesEvent<Entity> event)
	{
		if (!event.getObject().hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)&& event.getObject() instanceof EntityArrow)
		{
			event.addCapability(ARROWPROPERTIES_CAP, new ArrowPropertiesProvider());
		}
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
	
	//Stores on which side of a block the player clicked when they started mining, so tunneling can pick it up
	@SubscribeEvent
	public void onEvent(LeftClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.tunneling, player) !=0)
		{
			String facing = event.getFace().toString();
			player.removeTag("north");player.removeTag("south");player.removeTag("east");player.removeTag("west");player.removeTag("up");player.removeTag("down");
			player.addTag(facing);
		}
	}
	
	@SubscribeEvent
	public void onArrowLoose(ArrowLooseEvent event)
	{
		//Resolves multishot enchantment
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.multishot, event.getEntityPlayer()) !=0)
			EnchantmentMultishot.shootMoreArrows(event.getEntity().getEntityWorld(), event.getEntityPlayer(), event.getBow(), event.getCharge());
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onBlockBreak(BreakEvent event)
	{
		//Resolves tunneling enchantment
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.tunneling, event.getPlayer()) !=0)
			EnchantmentTunneling.mineManyBlocks(event.getPlayer(), event.getState(), event.getPos());
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onBreakingStart(BreakSpeed event)
	{
		//Resolves versatility enchantment
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.versatility, event.getEntityPlayer()) !=0)
			event.setNewSpeed(event.getOriginalSpeed() * EnchantmentVersatility.getSpeedModifier(event.getEntityPlayer(), event.getState()));
	}
	
	//Prevents teleportation for entities with antiwarp effect
	@SubscribeEvent
	public void onEvent(EnderTeleportEvent event)
	{
		if (event.getEntityLiving().getActivePotionEffect(ModPotions.antiwarp)!=null)
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		//EntityAIDoNothing takes over entity when it is stunned
		if (event.getEntity() instanceof EntityLiving)
			((EntityLiving) event.getEntity()).tasks.addTask(0, new EntityAIDoNothing((EntityLiving) event.getEntity()));
		
		if (event.getEntity() instanceof EntityArrow && !event.getWorld().isRemote)
		{
			EntityArrow arrow = (EntityArrow) event.getEntity();
			if (arrow.shootingEntity instanceof EntityLivingBase)
			{
				EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
				//Resolves arrow recovery enchantment
				if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.arrowrecovery, shooter) > 0)
					EnchantmentArrowRecovery.modifyArrow(shooter, arrow);
				
				//Resolves blast enchantment
				if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.blast, shooter) > 0)
					EnchantmentBlast.modifyArrow(arrow, shooter);
				
				//Resolves range enchantment
				if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.range, shooter) > 0)
					EnchantmentRange.modifyArrow(arrow);
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onAttack(LivingAttackEvent event)
	{
		Entity source = event.getSource().getImmediateSource();
		EntityLivingBase target = event.getEntityLiving();
		
		if (source instanceof EntityArrow)
		{
			//Makes custom shields block arrows
			if (target.getActiveItemStack().getItem() instanceof ItemCustomShield) 
			{
				if (ItemCustomShield.blockArrow(event.getSource(), target)) {event.setCanceled(true); return;}
			}
			
			//Makes custom arrow effects happen 
			source.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null).hitEntity((EntityArrow) source);
		}
		
		//Resolves penetration
		if (source instanceof EntityLivingBase && !source.world.isRemote)
		{
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.penetration, (EntityLivingBase) source) > 0 && !event.getSource().isMagicDamage())
				EnchantmentPenetration.dealPiercingDamage((EntityLivingBase)source, target, event.getAmount());
		}
		
		//Applies weapon venom
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
		}
	}
	
	@SubscribeEvent
	public void onDamage(LivingHurtEvent event)
	{
		//Makes custom shields block direct attacks
		EntityLivingBase target = event.getEntityLiving();
		if (target.getActiveItemStack().getItem() instanceof ItemCustomShield)
			event.setAmount(((ItemCustomShield)target.getActiveItemStack().getItem()).getDamageBlocked(target, event.getSource(), event.getAmount()));
		
		//Applies backstab damage multiplier
		Entity entitysource = event.getSource().getImmediateSource();
		if (entitysource instanceof EntityLivingBase && !entitysource.world.isRemote)
		{
			EntityLivingBase living = (EntityLivingBase) entitysource;
			if (living.getHeldItemMainhand().getItem() instanceof ItemDagger)
			{
				event.setAmount(event.getAmount() * ((ItemDagger)living.getHeldItemMainhand().getItem()).getBackstabMultiplyer(living, target));
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onDeath(LivingDeathEvent event)
	{
		//Drops spears stuck in an entity on death
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
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onExpReward(LivingExperienceDropEvent event)
	{
		EntityPlayer player = event.getAttackingPlayer();
		if (player != null)
		{
			//Processes education enchantment
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.education, player) > 0)
				event.setDroppedExperience((int) (event.getDroppedExperience() * EnchantmentEducation.getExpMultiplyer(player, event.getEntityLiving())));
		}
	}
	
	//Increases jump height for player with high jump enchantment
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onJump(LivingJumpEvent event)
	{
		//Processes high jump enchantment
		EntityLivingBase entity = event.getEntityLiving();
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.highjump, entity) > 0)
			EnchantmentHighJump.boostJump(entity);
		
		//Stops player from jumping when stunned
		if (entity instanceof EntityPlayer && event.getEntityLiving().getActivePotionEffect(ModPotions.stun)!=null)
			event.getEntityLiving().motionY=0;
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onItemUsing(Tick event) 
	{
		EntityLivingBase entity = event.getEntityLiving();
		Item item = event.getItem().getItem();
		
		//Processes rapid fire enchantment
		if (item instanceof ItemBow && EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, entity) > 0)
			event.setDuration(event.getDuration() - EnchantmentRapidFire.getChargeTimeReduction(entity, event.getDuration()));
	}
	
	@SubscribeEvent
	public void onEvent(HarvestDropsEvent event)
	{
		if (event.getHarvester() instanceof EntityPlayer && !event.getDrops().isEmpty())
		{
			EntityPlayer player = (EntityPlayer) event.getHarvester();
			
			//Processes smelting enchantment
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.smelting, player) > 0)
				EnchatnmentSmelting.smeltDrops(event.getDrops(), event.getFortuneLevel(), player);
			
			//Processes diamonds everywhere enchantment
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.diamonds, player) > 0)
				EnchantmentDiamonds.conjureDiamonds(event.getDrops(), event.getState().getBlock(), player);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		
		//Processes agility speed modifier
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity) > 0)
			EnchantmentAgility.applySpeedModifier(entity);
		else if (entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(EnchantmentAgility.speedModifier) != null)
			entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(EnchantmentAgility.speedModifier);
		
		//Processes extra regen from vitality
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.vitality, entity) !=0 && entity instanceof EntityPlayer)
			EnchantmentVitality.healPlayer((EntityPlayer) entity);
		
		//Processes custom shield speed modifier
		if (entity.getActiveItemStack().getItem() instanceof ItemCustomShield)
			((ItemCustomShield)entity.getActiveItemStack().getItem()).applyModifiers(entity);
		else
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(ItemCustomShield.knockbackModifierUUID);
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(ItemCustomShield.weightModifierUUID);
		}
		
		//Makes blindness affect mobs
		if (event.getEntityLiving() instanceof EntityLiving)
		{
			event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27"));
			if (!event.getEntityLiving().getActivePotionEffects().isEmpty())
			{
				Collection<PotionEffect> effects = event.getEntityLiving().getActivePotionEffects();
				for (PotionEffect effect : effects)
				{
					if (effect.getPotion() == MobEffects.BLINDNESS)
					{
						AttributeModifier modifier = new AttributeModifier(UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27"), "blind",-0.8, 1);
						event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(modifier);
					}
				}
			}
		}
	}

	/*
	@SubscribeEvent
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
	 */
	
	//Insert weapons from my mod into dungeon loot
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onLootLoad(LootTableLoadEvent event)
	{
		if (event.getName().toString().equals("minecraft:chests/end_city_treasure"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/end_city_treasure_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/stronghold_corridor"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/stronghold_corridor_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/village_blacksmith"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/village_blacksmith_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/nether_bridge"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/nether_bridge_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/spawn_bonus_chest"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/spawn_bonus_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
	}
}
