package com.mujmajnkraft.bettersurvival.eventhandlers;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.IArrowProperties;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
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
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesProvider;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;
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
	public static final ResourceLocation WEAPONEFFECT_CAP = new ResourceLocation(Reference.MOD_ID, "weaponeff");
	public static final ResourceLocation NUNCHAKU_CAP = new ResourceLocation(Reference.MOD_ID, "nunchakucombo");

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityArrow && !event.getObject().hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
			event.addCapability(ARROWPROPERTIES_CAP, new ArrowPropertiesProvider());
		}
		if(event.getObject() instanceof EntityLivingBase) {
			if(!event.getObject().hasCapability(SpearsInProvider.SPEARSIN_CAP, null)) {
				event.addCapability(SPEARSIN_CAP, new SpearsInProvider());
			}
			if(!event.getObject().hasCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null)) {
				event.addCapability(NUNCHAKU_CAP, new NunchakuComboProvider());
			}
			if(!event.getObject().hasCapability(WeaponEffectProvider.WEAPONEFFECT_CAP, null)) {
				event.addCapability(WEAPONEFFECT_CAP, new WeaponEffectProvider());
			}
		}
	}
	
	@SubscribeEvent
	public void onArrowLoose(ArrowLooseEvent event)
	{
		//Resolves multishot enchantment
		if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.multishot, event.getBow()) !=0)
			EnchantmentMultishot.shootMoreArrows(event.getEntity().getEntityWorld(), event.getEntityPlayer(), event.getBow(), event.getCharge());
	}
	
	@SubscribeEvent(priority=EventPriority.LOW)
	public void onBlockBreak(BreakEvent event)
	{
		//Resolves tunneling enchantment, don't recursively tunnel
		if(event.getPlayer().getHeldItemMainhand().hasTagCompound() && event.getPlayer().getHeldItemMainhand().getTagCompound().getBoolean("tunnelCooldown")) return;
		if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.tunneling, event.getPlayer().getHeldItemMainhand()) !=0)
			EnchantmentTunneling.mineManyBlocks(event.getPlayer(), event.getState(), event.getPos());
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onBreakingStart(BreakSpeed event)
	{
		//Resolves versatility enchantment
		if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.versatility, event.getEntityPlayer().getHeldItemMainhand()) !=0)
			event.setNewSpeed(event.getOriginalSpeed() * EnchantmentVersatility.getSpeedModifier(event.getEntityPlayer(), event.getState()));
	}
	
	//Prevents teleportation for entities with antiwarp effect
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEvent(EnderTeleportEvent event)
	{
		if(event.getEntityLiving().getActivePotionEffect(ModPotions.antiwarp) != null) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityArrow && !event.getWorld().isRemote) {
			EntityArrow arrow = (EntityArrow)event.getEntity();
			if(arrow.shootingEntity instanceof EntityLivingBase) {
				EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
				//Resolves arrow recovery enchantment
				if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.arrowrecovery, shooter.getHeldItemMainhand()) > 0)
					EnchantmentArrowRecovery.modifyArrow(shooter, arrow);
				
				//Resolves blast enchantment
				if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.blast, shooter.getHeldItemMainhand()) > 0)
					EnchantmentBlast.modifyArrow(arrow, shooter);
				
				//Resolves range enchantment
				if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.range, shooter.getHeldItemMainhand()) > 0)
					EnchantmentRange.modifyArrow(arrow);
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onAttack(LivingAttackEvent event)
	{
		Entity source = event.getSource().getImmediateSource();
		EntityLivingBase target = event.getEntityLiving();
		
		if(source instanceof EntityArrow) {
			//Makes custom shields block arrows
			if(target.getActiveItemStack().getItem() instanceof ItemCustomShield && ItemCustomShield.blockArrow(event.getSource(), target)) {
				event.setCanceled(true);
				return;
			}
			//Makes custom arrow effects happen 
			IArrowProperties cap = source.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
			if(cap != null) cap.hitEntity((EntityArrow) source);
		}
		
		//Resolves penetration
		if(source instanceof EntityLivingBase && !source.world.isRemote) {
			if(EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.penetration, (EntityLivingBase) source) > 0 && !event.getSource().isMagicDamage())
				EnchantmentPenetration.dealPiercingDamage((EntityLivingBase)source, target, event.getAmount());
		}
		
		//Applies weapon venom and spear break chance (Handled in RLCombatCompatEventHandler if RLCombat is loaded and a player)
		if(!(Bettersurvival.isRLCombatLoaded && event.getSource().getImmediateSource() instanceof EntityPlayer) && event.getSource().getImmediateSource() instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase)event.getSource().getImmediateSource();
			if(attacker.getHeldItemMainhand().getItem() instanceof ItemSpear && attacker instanceof EntityPlayer) {
				if(!((EntityPlayer)attacker).capabilities.isCreativeMode && ((ItemSpear)attacker.getHeldItemMainhand().getItem()).breakChance() >= attacker.getRNG().nextFloat()) {
					attacker.getHeldItemMainhand().shrink(1);
				}
			}
			if(attacker.getHeldItemMainhand().getItem() instanceof ItemSword && !attacker.world.isRemote) {
				if(attacker.getHeldItemMainhand().hasTagCompound()) {
					NBTTagCompound compound = attacker.getHeldItemMainhand().getTagCompound();
					int h = compound.getInteger("remainingHits");

					if(h > 0 && event.getEntityLiving().hurtResistantTime<10) {
						for(PotionEffect effect : PotionUtils.getEffectsFromStack(attacker.getHeldItemMainhand())) {
							if(effect.getPotion().isInstant()) {
								effect.getPotion().affectEntity(null, event.getSource().getImmediateSource(), event.getEntityLiving(), effect.getAmplifier(), 1/6D);
								event.getEntityLiving().hurtResistantTime = 0;
							}
							else {
								event.getEntityLiving().addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()/8, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
							}
						}

						if(attacker instanceof EntityPlayer) {
							if(!((EntityPlayer)attacker).capabilities.isCreativeMode) {
								compound.setInteger("remainingHits", h-1);
							}
						}
						if(h-1 <= 0)
						{
							compound.removeTag("Potion");
							compound.removeTag("CustomPotionEffects");
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onDamage(LivingHurtEvent event) {
		//Makes custom shields block direct attacks
		EntityLivingBase target = event.getEntityLiving();
		if(target.getActiveItemStack().getItem() instanceof ItemCustomShield)
			event.setAmount(((ItemCustomShield)target.getActiveItemStack().getItem()).getDamageBlocked(target, event.getSource(), event.getAmount()));
		
		//Applies backstab and nunchuku multiplier (Handle in RLCombatCompatEventHandler if RLCombat is loaded)
		Entity entitysource = event.getSource().getImmediateSource();
		if(!Bettersurvival.isRLCombatLoaded && entitysource instanceof EntityPlayer && !entitysource.world.isRemote) {
			EntityPlayer player = (EntityPlayer)entitysource;
			if(player.getHeldItemMainhand().getItem() instanceof ItemDagger) {
				event.setAmount(event.getAmount() * ((ItemDagger)player.getHeldItemMainhand().getItem()).getBackstabMultiplyer(player, target));
			}
			if(player.getHeldItemMainhand().getItem() instanceof ItemNunchaku) {
				INunchakuCombo combo = player.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
				if(combo != null) {
					event.setAmount(event.getAmount() * (combo.getComboPower() + 1.0F));
				}
			}
		}
	}

	//Drops spears stuck in an entity on death
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onDeath(LivingDeathEvent event)
	{
		ISpearsIn spearsIn = event.getEntity().getCapability(SpearsInProvider.SPEARSIN_CAP, null);
		if(spearsIn != null) {
			ArrayList<ItemStack> spears = spearsIn.getSpearsIn();
			if(!spears.isEmpty()) {
				for(ItemStack spear : spears) {
					event.getEntity().entityDropItem(spear, 0);
				}
			}
		}
	}

	//Processes education enchantment
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onExpReward(LivingExperienceDropEvent event) {
		EntityPlayer player = event.getAttackingPlayer();
		if(player != null) {
			if(EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.education, player) > 0)
				event.setDroppedExperience((int)(((float)event.getDroppedExperience()) * EnchantmentEducation.getExpMultiplyer(player, event.getEntityLiving())));
		}
	}
	
	//Increases jump height for player with high jump enchantment, and handle stun
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onJump(LivingJumpEvent event) {
		//Processes high jump enchantment
		EntityLivingBase entity = event.getEntityLiving();
		int j = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.highjump, entity);
		if(j > 0) EnchantmentHighJump.boostJump(entity, j);
		
		//Stops entity from jumping when stunned
		if(entity.getActivePotionEffect(ModPotions.stun)!=null) {
			if(entity.motionY > 0) entity.motionY = 0;
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onItemUsing(Tick event) 
	{
		EntityLivingBase entity = event.getEntityLiving();
		Item item = event.getItem().getItem();
		
		//Processes rapid fire enchantment
		if(item instanceof ItemBow && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.rapidfire, entity.getHeldItemMainhand()) > 0)
			event.setDuration(event.getDuration() - EnchantmentRapidFire.getChargeTimeReduction(entity, event.getDuration()));
	}
	
	@SubscribeEvent
	public void onEvent(HarvestDropsEvent event)
	{
		if(event.getHarvester() != null && !event.getDrops().isEmpty()) {
			EntityPlayer player = event.getHarvester();
			
			//Processes smelting enchantment
			if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.smelting, player.getHeldItemMainhand()) > 0)
				EnchatnmentSmelting.smeltDrops(event.getDrops(), event.getFortuneLevel(), player);
			
			//Processes diamonds everywhere enchantment
			if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.diamonds, player.getHeldItemMainhand()) > 0)
				EnchantmentDiamonds.conjureDiamonds(event.getDrops(), event.getState().getBlock(), player);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();

		//Trigger nunchaku combo countdown
		if(entity.getHeldItemMainhand().getItem() instanceof ItemNunchaku) {
			INunchakuCombo combo = entity.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
			if(combo != null && combo.getComboTime() > 0) {
				combo.countDown();
			}
		}

		//Processes agility speed modifier
		if(EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity) > 0) {
			EnchantmentAgility.applySpeedModifier(entity);
		}
		else {
			entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(EnchantmentAgility.speedModifier);
		}
		
		//Processes extra regen from vitality
		if(EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.vitality, entity) != 0 && entity instanceof EntityPlayer) {
			EnchantmentVitality.healPlayer((EntityPlayer) entity);
		}
		
		//Processes custom shield speed modifier
		if(entity.getActiveItemStack().getItem() instanceof ItemCustomShield) {
			((ItemCustomShield)entity.getActiveItemStack().getItem()).applyModifiers(entity);
		}
		else {
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(ItemCustomShield.knockbackModifierUUID);
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(ItemCustomShield.weightModifierUUID);
		}

		//Handle stun effect
		if(entity.getActivePotionEffect(ModPotions.stun) != null) {
			entity.motionX = 0;
			if(entity.motionY > 0) entity.motionY = 0;
			entity.motionZ = 0;
		}

		//Makes blindness affect mobs
		if(entity instanceof EntityLiving) {
			entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27"));
			if(!entity.getActivePotionEffects().isEmpty()) {
				Collection<PotionEffect> effects = entity.getActivePotionEffects();
				for(PotionEffect effect : effects) {
					if(effect.getPotion() == MobEffects.BLINDNESS) {
						AttributeModifier modifier = new AttributeModifier(UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27"), "blind",-0.8, 1);
						entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(modifier);
						break;
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
	@SubscribeEvent(priority=EventPriority.NORMAL)
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