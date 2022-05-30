package com.mujmajnkraft.bettersurvival.eventhandlers;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesProvider;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.IArrowProperties;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentAgility;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.items.ItemCrossbow;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEnchantmentHandler {
	
	public static final ResourceLocation ARROWPROPERTIES_CAP = new ResourceLocation(Reference.MOD_ID, "ArrowProperties");
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(AttachCapabilitiesEvent<Entity> event)
	{
		if (!event.getObject().hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)&& event.getObject() instanceof EntityArrow)
		{
			event.addCapability(ARROWPROPERTIES_CAP, new ArrowPropertiesProvider());
		}
	}
	
	//Increases jump height for player with high jump enchantment
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onJump(LivingJumpEvent event)
	{
	    if (event.getEntity() instanceof EntityPlayer)
	    {
	        EntityPlayer entity = (EntityPlayer) event.getEntity();
		    if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.highjump, entity) !=0)
		    {
		    	entity.motionY += EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.highjump, entity) / 10.0D;
		    }
	    }
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(HarvestDropsEvent event)
	{
		if (event.getHarvester() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getHarvester();
			
			//Smelts drops from blocks
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.smelting, player) !=0)
			{
				java.util.List<ItemStack> drops = event.getDrops();
				java.util.List<ItemStack> dropsCopy = new ArrayList<>(drops);
				drops.clear();
				for (ItemStack drop : dropsCopy)
					if (drop != null) {
						ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(drop);
						if (smeltingResult != ItemStack.EMPTY) {
							smeltingResult = smeltingResult.copy();
							smeltingResult.setCount(drop.getCount()); 
							int fortuneLevel = event.getFortuneLevel();
							if (!(smeltingResult.getItem() instanceof ItemBlock))
								smeltingResult.setCount(new Random().nextInt(fortuneLevel + 1) + 1);
							drops.add(smeltingResult);
						} else
							drops.add(drop);
					}
			}
			
			//Gives player a chance to get diamond from every ore
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.diamonds, player) !=0)
			{
				Block block = (Block) event.getState().getBlock();
				java.util.List<ItemStack> drops = event.getDrops();
				java.util.List<Item> itemdrops = new ArrayList<>();
				for (ItemStack itemstackdrop : drops)
				{
					if (drops != null){
						Item itemdrop = itemstackdrop.getItem();
						itemdrops.add(itemdrop);
					}
				}
				Item drop = Item.getItemFromBlock(block);
				if ((block instanceof BlockOre || block instanceof BlockRedstoneOre) && !itemdrops.contains(drop))
				{
					if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.diamonds, player) > new Random().nextInt(50))
					{
						ItemStack itemStackToDrop = new ItemStack(Items.DIAMOND, 1);
						event.getDrops().add(itemStackToDrop);
					}
		
				}

			}
		
		}
		
	}
	
	/*
	private java.util.List<ItemStack> copyList(java.util.List<ItemStack> drops) {
		try {
			Constructor constructor = drops.getClass().getConstructor(Collection.class);
			return (java.util.List<ItemStack>) constructor.newInstance(drops);
		} catch (Exception exception) {
			return new ArrayList<>(drops);
		}
	}*/
	
	//Saves information about player facing while is he mining a block
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
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
	
	//Attaches additional info to arrow
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityArrow && !event.getWorld().isRemote)
		{
			EntityArrow arrow = (EntityArrow) event.getEntity();
			if (arrow.shootingEntity instanceof EntityLivingBase)
			{
				EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
				if (arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null))
				{
					IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
					//allows arrow recovery
					if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.arrowrecovery, shooter) !=0)
					{
						int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.arrowrecovery, shooter);
						if (new Random().nextInt(4) < l)
						{
							properties.setCanRecover(true);
						}
					}
					
					//Makes arrow explode
					if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.blast, shooter) !=0)
					{
						if (arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null))
						{
							float power = (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.blast, shooter)+1)/4.0F;
							boolean canDestroyBlocks = false;
							if (shooter instanceof EntityPlayer)
							{
								canDestroyBlocks = ((EntityPlayer)shooter).capabilities.allowEdit;
							}
							else
							{
							canDestroyBlocks = event.getWorld().getGameRules().getBoolean("mobGriefing");
							}
							properties.setExplosion(power, canDestroyBlocks);
						}
					};
					
					//Allows arrow to fly further
					if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.range, shooter) !=0)
					{
						arrow.motionX *= 2;
						arrow.motionY *= 2;
						arrow.motionZ *= 2;
					}
				
				}
			}
		}
	}
	
	//Allows tools with tunneling ench to break multiple blocks at once
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(BreakEvent event)
	{
		EntityPlayer player = event.getPlayer();
		World world = event.getWorld();
		IBlockState state = event.getState();
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.tunneling, player) !=0)
		{
			Set<String> t = player.getTags();
			int dir = 0;
			if (t.contains("west")) {dir = 1; t.remove("west");}
			else if (t.contains("east")) {dir = 2; t.remove("east");}
			else if (t.contains("down")) {dir = 3; t.remove("down");}
			else if (t.contains("up")) {dir = 4; t.remove("up");}
			else if (t.contains("south")) {dir = 5; t.remove("south");}
			else if (t.contains("north")) {dir = 6; t.remove("north");}
			if (dir != 0 && canMineEffectively(player, state))
			{
				int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.tunneling, player);
				for(int x = (int) -l; x < l + 1; x++)
				{
					if((dir !=1 && dir !=2) || x==0)
					{
						for(int y = (int) -l; y < l + 1; y++)
						{
							if((dir != 3 && dir != 4) || y==0)
							{
								for(int z = (int) -l; z < l + 1; z++)
								{
									if ((dir != 5 && dir != 6) || z==0)
									{
										if (Math.sqrt(x*x+y*y+z*z)<=(l+1.0F)/2.0F && !(x==0 && y==0 && z==0))
										{
											BlockPos pos = event.getPos().add(x, y, z);
											if (canMineEffectively(player, world.getBlockState(pos)))
											{
												((EntityPlayerMP)player).interactionManager.tryHarvestBlock(pos);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	//Makes entities killed with weapons with education ench drop more XP
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingExperienceDropEvent event)
	{
		EntityPlayer player = (EntityPlayer) event.getAttackingPlayer();
		if (player != null)
		{
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.education, player) !=0 && event.getEntityLiving().isNonBoss())
			{
				int lvl = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.education, player);
				event.setDroppedExperience(Math.round(event.getOriginalExperience()*(lvl/2f + 1)));
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		double d = 0.01*EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity);
		AttributeModifier modifier = new AttributeModifier(EnchantmentAgility.speedModifier, "agility", d, 0);
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity) > 0)
		{
			if (!entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(modifier))
			{
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(modifier);
			}
			else if (entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(EnchantmentAgility.speedModifier).getAmount() != d)
			{
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(EnchantmentAgility.speedModifier);
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(modifier);
			}
		}
		else
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(EnchantmentAgility.speedModifier);
		}
		
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.vitality, entity) !=0 && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			if (player.getEntityWorld().getGameRules().getBoolean("naturalRegeneration") && player.getFoodStats().getFoodLevel() >= 18)
	        {
	            if (entity.getHealth() < entity.getMaxHealth() && entity.ticksExisted % (40/EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.vitality, entity)) == 0)
	            {
	            	entity.heal(1.0F);
	            }
	        }
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingEntityUseItemEvent.Tick event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		Item item = event.getItem().getItem();
		if (item instanceof ItemBow)
		{
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, entity) !=0)
			{
				int d =event.getDuration();
				if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, entity)<4)
				{
					if (d%(5-EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, entity)) == 0)
					{
						event.setDuration(d-1);
					}
				}
				else
				{
					event.setDuration(d-(EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.rapidfire, entity))-3);
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(BreakSpeed event)
	{
		EntityPlayer player = event.getEntityPlayer();
		event.getState();
		event.getState().getBlock();
		player.getHeldItemMainhand();
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.versatility, player) !=0)
		{
			if (player.inventory.getDestroySpeed(event.getState()) <= 1.0F)
			{
				Item item = player.getHeldItemMainhand().getItem();
				if(item instanceof ItemTool)
				{
					ItemTool tool =(ItemTool) item;
					ToolMaterial material = ToolMaterial.valueOf(tool.getToolMaterialName());
					event.setNewSpeed(event.getOriginalSpeed()*(material.getEfficiency()/2));
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingAttackEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		if (event.getSource().getImmediateSource()!=null)
		{
			if (event.getSource().getImmediateSource() instanceof EntityArrow)
			{
				EntityArrow arrow = (EntityArrow) event.getSource().getImmediateSource();
				if (arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null));
				{
					IArrowProperties cap = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
					
					if (cap.getCanRecover() && arrow.shootingEntity != null && arrow.pickupStatus == PickupStatus.ALLOWED)
					{
						if (!arrow.shootingEntity.isDead && arrow.shootingEntity instanceof EntityPlayer)
						{
							EntityPlayer shooter = (EntityPlayer) arrow.shootingEntity;
							ItemStack stack = new ItemStack(Items.ARROW);
							boolean flag = shooter.inventory.addItemStackToInventory(stack);
							if (flag)
							{
								shooter.inventoryContainer.detectAndSendChanges();
							}
							else
							{
								EntityItem entityitem = shooter.dropItem(stack, false);
								entityitem.setNoPickupDelay();
			                    entityitem.setOwner(shooter.getName());
							}
						}
					}
					
					if (cap.getExplosionPower() > 0)
					{
						arrow.world.newExplosion(arrow.shootingEntity, arrow.posX, arrow.posY, arrow.posZ, cap.getExplosionPower(), arrow.isBurning(), false);
						arrow.setDead();
						
						if (arrow instanceof EntityTippedArrow)
						{
							NBTTagCompound compound =  arrow.writeToNBT(new NBTTagCompound());
							ArrayList<PotionEffect> effects = new ArrayList<>();
							if (compound.hasKey("Potion", 8))
					        {
					            PotionType potion = PotionUtils.getPotionTypeFromNBT(compound);
					            for(PotionEffect potioneffect : potion.getEffects())
					            {
					            	effects.add(new PotionEffect(potioneffect.getPotion(), potioneffect.getDuration()/8, potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
					            }
					        }
							for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromTag(compound))
					        {
					            effects.add(potioneffect);
					        }
							
							if (!effects.isEmpty())
					        {
					            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(arrow.world, arrow.posX, arrow.posY, arrow.posZ);
					            entityareaeffectcloud.setRadius(2.5f*cap.getExplosionPower());
					            entityareaeffectcloud.setRadiusOnUse(-0.5F);
					            entityareaeffectcloud.setWaitTime(10);
					            entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
					            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

					            for (PotionEffect potioneffect : effects)
					            {
					                entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
					            }

					            arrow.world.spawnEntity(entityareaeffectcloud);
					        }
						}
					}
				}
			}
			Entity source = event.getSource().getImmediateSource();
			
			if (source instanceof EntityLivingBase && !source.world.isRemote)
			{
				if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.penetration, (EntityLivingBase) source) > 0 && !event.getSource().isMagicDamage())
				{
					int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.penetration, (EntityLivingBase) source);
					float min = event.getAmount() * Math.min(((l + 2) / 10.0f), 1);
					event.getEntityLiving().attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, source), min);
				}
			}
			
			if (living.getActiveItemStack().getItem() instanceof ItemCustomShield)
			{
				ItemStack stack = living.getActiveItemStack();
				if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.reflection, stack)!=0)
				{
					int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.reflection, stack);
					if(new Random().nextFloat() < 0.15F * (float)level)
					{
						source.attackEntityFrom(DamageSource.causeThornsDamage(living), level > 10 ? level - 10 : 1 + new Random().nextInt(4));
						stack.damageItem(1, living);
					}
				}
			}
		}
	}
	
	//Causes bow with multishot ench to fire multiple arrows at once
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(ArrowLooseEvent event)
	{
		if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.multishot, event.getBow())>0)
		{
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.multishot, event.getBow());
			for (int x=0; x<l; x++)
			{
				float Yaw = new Random().nextFloat()*30 - 15;
				float Pitch = new Random().nextFloat()*30 - 15;
				EntityPlayer entityplayer =event.getEntityPlayer();
				World worldIn = event.getWorld();
				ItemStack stack = event.getBow();
				boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
				ItemStack ammo = ItemStack.EMPTY;
				if (stack.getItem() instanceof ItemCrossbow)
				{
					ItemCrossbow crossbow = (ItemCrossbow) stack.getItem();
					ammo = crossbow.loadedAmmo(stack);
				}
				ammo = this.findAmmo(entityplayer);
				if (!ammo.isEmpty() || flag)
	            {
	                if (ammo.isEmpty())
	                {
	                	ammo = new ItemStack(Items.ARROW);
	                }

	                float f = ItemBow.getArrowVelocity(event.getCharge());
	                if ((double)f >= 0.1D)
	                {
	                    if (!worldIn.isRemote)
	                    {
	                    	ItemArrow itemarrow = (ItemArrow)((ItemArrow)(ammo.getItem() instanceof ItemArrow ? ammo.getItem() : Items.ARROW));
	                    	EntityArrow entityarrow = itemarrow.createArrow(worldIn, ammo, entityplayer);
	                    	entityarrow.shoot(entityplayer, entityplayer.rotationPitch + Pitch, entityplayer.rotationYaw + Yaw, 0.0F, f * 3.0F, 1.0F);

	                    	if (f == 1.0F)
	                    	{
	                    		entityarrow.setIsCritical(true);
	                    	}

	                    	int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

	                    	if (j > 0)
	                    	{
                        	entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
	                    	}

	                    	int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

	                    	if (k > 0)
	                    	{
	                    		entityarrow.setKnockbackStrength(k);
	                    	}

	                    	if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
	                    	{
	                    		entityarrow.setFire(100);
	                    	}

	                    	stack.damageItem(1, entityplayer);

	                    	entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;

	                    	worldIn.spawnEntity(entityarrow);
	                    }
	                }
                }
			}
		}
	}
	
	private ItemStack findAmmo(EntityPlayer player)
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
	
	boolean canMineEffectively(EntityPlayer player, IBlockState state)
	{
		ItemStack stack = player.getHeldItemMainhand();
		Block block = state.getBlock();
		for (String type:stack.getItem().getToolClasses(player.getHeldItemMainhand()))
		{
			if (block.isToolEffective(type, state) && block.getHarvestLevel(state) <= stack.getItem().getHarvestLevel(player.getHeldItemMainhand(), type, player, state))
			{
				return true;
			}
		}
		return false;
	}
}