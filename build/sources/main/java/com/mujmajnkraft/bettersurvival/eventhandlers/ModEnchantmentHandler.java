package com.mujmajnkraft.bettersurvival.eventhandlers;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesProvider;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.IArrowProperties;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.items.ItemCrossbow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingJumpEvent event)
	{
	    if (event.getEntity() instanceof EntityPlayer)
	    {
	        EntityPlayer entity = (EntityPlayer) event.getEntity();
		    if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.highjump, entity) !=0)
		    {
		    	entity.motionY = entity.motionY + (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.highjump, entity)/10D);
		    }
	    }
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(HarvestDropsEvent event)
	{
		if (event.getHarvester() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getHarvester();
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
				if (block instanceof BlockOre && !itemdrops.contains(drop))
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
	
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityArrow)
		{
			EntityArrow arrow = (EntityArrow) event.getEntity();
			EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
			if (arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null))
			{
				IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
				try 
				{
					if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.arrowrecovery, shooter) !=0)
					{
						int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.arrowrecovery, shooter);
						if (new Random().nextInt(4)<l)
						{
							properties.setCanRecover(true);
						}
					}
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
					if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.range, shooter) !=0)
					{
					properties.setNoDrag(true);
					}/*
				if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.multishot, shooter) !=0)
				{
					NBTTagCompound nbt = new NBTTagCompound();
					NBTTagCompound compound = arrow.writeToNBT(nbt);
					int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.multishot, shooter);
					for (int x=0; x<l; x++)
					{
						ItemArrow itemarrow = (ItemArrow) Items.ARROW;
						Entity entityarrow = itemarrow.createArrow(event.getWorld(), new ItemStack(itemarrow), shooter);
						arrow.world.spawnEntity(newarrow);
					}
				}*/
				}
				catch (Exception e)
				{
				}
			
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(BreakEvent event)
	{
		EntityPlayer player = event.getPlayer();
		World world = event.getWorld();
		Block block = event.getState().getBlock();
		Item tool = player.getHeldItemMainhand().getItem();
		IBlockState state = event.getState();
		boolean canBeMined = false;
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.tunneling, player) !=0)
		{
			for (String type:tool.getToolClasses(player.getHeldItemMainhand()))
			{
				if (block.isToolEffective(type, state) && block.getHarvestLevel(state) <= tool.getHarvestLevel(player.getHeldItemMainhand(), type, player, state))
				{
					canBeMined = true;
				}
			}
			if (canBeMined)
			{
				int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.tunneling, player);
				java.util.List<BlockPos> blocks = new ArrayList<>();
				for(int x = (int) -l; x < l + 1; x++)
				{
					if((!player.getTags().contains("west") && !player.getTags().contains("east")) || x==0)
					{
						for(int y = (int) -l; y < l + 1; y++)
						{
							if((!player.getTags().contains("up") && !player.getTags().contains("down")) || y==0)
							{
								for(int z = (int) -l; z < l + 1; z++)
								{
									if ((!player.getTags().contains("north") && !player.getTags().contains("south")) || z==0)
									{
										if (Math.sqrt(x*x+y*y+z*z)<=(l+1.0F)/2.0F && !(x==0 && y==0 && z==0))
										{
											BlockPos pos = event.getPos().add(x, y, z);
											blocks.add(pos);
										}
									}
								}
							}
						}
					}
				}
				for (BlockPos blockpos : blocks)
				{
					IBlockState block1 = world.getBlockState(blockpos);
					boolean canBeMined1 = false;
					for (String type:tool.getToolClasses(player.getHeldItemMainhand()))
					{
						if (block1.getBlock().isToolEffective(type, block1) && block1.getBlock().getHarvestLevel(block1) <= tool.getHarvestLevel(player.getHeldItemMainhand(), type, player, block1))
						{
							canBeMined1 = true;
						}
					}
					if (canBeMined1)
					{
						block1.getBlock().harvestBlock(event.getWorld(), player, blockpos, block1, null, player.getHeldItemMainhand());
						world.destroyBlock(blockpos, false);
						player.getHeldItemMainhand().damageItem(1, player);
					}
				}
			}
		}
	}
	
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
		entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString("e6107045-134f-4c54-a645-75c3ae5c7a27"));
		entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(UUID.fromString("e6107045-134f-4c54-a645-75c3ae5c7a28"));
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity) !=0)
		{
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("e6107045-134f-4c54-a645-75c3ae5c7a27"), "agility", 0.01*EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity), 0);
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(modifier);
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
		IBlockState state = event.getState();
		Block block  = event.getState().getBlock();
		ItemStack stack = player.getHeldItemMainhand();
		boolean canBeMined = false;
		if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.versatility, player) !=0)
		{
			for (String type:stack.getItem().getToolClasses(player.getHeldItemMainhand()))
			{
				if (block.isToolEffective(type, state) && block.getHarvestLevel(state) <= stack.getItem().getHarvestLevel(stack, type, player, state))
				{
					canBeMined = true;
				}
			}
			if (!canBeMined)
			{
				Item item = player.getHeldItemMainhand().getItem();
				if(item instanceof ItemTool)
				{
					ItemTool tool =(ItemTool) item;
					event.setNewSpeed(event.getOriginalSpeed()*(tool.getToolMaterial().getEfficiencyOnProperMaterial()/2));
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingAttackEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		if (event.getSource().getSourceOfDamage()!=null)
		{
			if (event.getSource().isProjectile())
			{
				if (event.getSource().getSourceOfDamage() instanceof EntityArrow)
				{
					EntityArrow arrow = (EntityArrow) event.getSource().getSourceOfDamage();
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
							arrow.world.newExplosion(arrow.shootingEntity, arrow.posX, arrow.posY, arrow.posZ, cap.getExplosionPower(), arrow.isBurning(), cap.getCanDestroyBlocks());
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
			}
			Entity source = event.getSource().getSourceOfDamage();
			if (living.isActiveItemStackBlocking())
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
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingHurtEvent event)
	{
		Entity entitysource = event.getSource().getSourceOfDamage();
		if (entitysource instanceof EntityLivingBase)
		{
			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.penetration, (EntityLivingBase) entitysource) !=0)
			{
				int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.penetration, (EntityLivingBase) entitysource);
				AttributeModifier armordebuff = new AttributeModifier(UUID.fromString("e6107045-134f-4c54-a645-75c3ae5c7a28"), "armordebuff", -2*l, 0);
				event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(armordebuff);
			}
		}
	}
	
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
	                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (ammo.getItem() instanceof ItemArrow && ((ItemArrow) ammo.getItem()).isInfinite(ammo, stack, entityplayer));

	                    if (!worldIn.isRemote)
	                    {
	                    	ItemArrow itemarrow = (ItemArrow)((ItemArrow)(ammo.getItem() instanceof ItemArrow ? ammo.getItem() : Items.ARROW));
	                    	EntityArrow entityarrow = itemarrow.createArrow(worldIn, ammo, entityplayer);
	                    	entityarrow.setAim(entityplayer, entityplayer.rotationPitch + Pitch, entityplayer.rotationYaw + Yaw, 0.0F, f * 3.0F, 1.0F);

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

	                    	if (flag1 || entityplayer.capabilities.isCreativeMode && (ammo.getItem() == Items.SPECTRAL_ARROW || ammo.getItem() == Items.TIPPED_ARROW))
	                    	{
	                    		entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	                    	}

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
    
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(FOVUpdateEvent event)
	{
		if (ConfigHandler.FOV)
		{
			event.setNewfov(1);
		}
	}
}