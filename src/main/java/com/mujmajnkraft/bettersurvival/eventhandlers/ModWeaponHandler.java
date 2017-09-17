package com.mujmajnkraft.bettersurvival.eventhandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.MessageExtendedReachAttack;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.entityspeed.EntitySpeedProvider;
import com.mujmajnkraft.bettersurvival.capabilities.entityspeed.IEntitySpeed;
import com.mujmajnkraft.bettersurvival.capabilities.spearsin.ISpearsIn;
import com.mujmajnkraft.bettersurvival.capabilities.spearsin.SpearsInProvider;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.IWeaponEffect;
import com.mujmajnkraft.bettersurvival.capabilities.weaponeffect.WeaponEffectProvider;
import com.mujmajnkraft.bettersurvival.init.ModBlocks;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModWeaponHandler {
	
	public static final ResourceLocation WEAPONEFFECT_CAP = new ResourceLocation(Reference.MOD_ID, "weaponeffect");
	public static final ResourceLocation SPEARSIN_CAP = new ResourceLocation(Reference.MOD_ID, "spearsin");
	public static final ResourceLocation ENTITYSPEED_CAP = new ResourceLocation(Reference.MOD_ID, "speed");
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		if (living.hasCapability(EntitySpeedProvider.ENTITYSPEED_CAP, null))
		{
			IEntitySpeed speed = living.getCapability(EntitySpeedProvider.ENTITYSPEED_CAP, null);
			speed.update();
			speed.setX(living.posX);
			speed.setY(living.posY);
			speed.setZ(living.posZ);
		}
		if (living instanceof EntityPlayer)
		{	
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(UUID.fromString("a6107045-134f-4c04-a645-75c3ae5c7a27"));
			
			if (player.getHeldItemOffhand().getItem() instanceof ItemShield||player.getHeldItemMainhand().getItem() instanceof ItemShield)
			{
				if (player.getTags().contains("NoShield"))
				{
					player.removeTag("NoShield");
					player.getCooldownTracker().setCooldown(Items.SHIELD, 60);
					player.getCooldownTracker().setCooldown(ModItems.bigshield, 60);
					player.getCooldownTracker().setCooldown(ModItems.smallshield, 60);
				}
			}
			else
			{
				player.addTag("NoShield");
			}
			if (player.getHeldItemOffhand().getItem() instanceof ICustomWeapon)
			{
				ICustomWeapon weapon = (ICustomWeapon)player.getHeldItemOffhand().getItem();
				if (weapon.isTwoHand())
				{
					player.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
					if (!event.getEntity().world.isRemote)
					{
						player.dropItem((Item) weapon, 1);
					}
				}
			}
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(UUID.fromString("a6107045-134f-4c54-a645-75c3ae5c7a27"));
			if(player.getHeldItemMainhand().getItem() instanceof ICustomWeapon && player.getHeldItemOffhand()!=ItemStack.EMPTY)
			{
				ICustomWeapon weapon = (ICustomWeapon)player.getHeldItemMainhand().getItem();
				if (weapon.isTwoHand())
				{
					AttributeModifier modifier = new AttributeModifier(UUID.fromString("a6107045-134f-4c54-a645-75c3ae5c7a27"), "tooheavyweapon", -1, 2);
					player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(modifier);
				}
			}
			if (player.getHeldItemMainhand().getItem() instanceof ItemNunchaku)
			{
				ItemNunchaku nunchaku = (ItemNunchaku) player.getHeldItemMainhand().getItem();
				if (nunchaku.isSpinning(player.getHeldItemMainhand()))
				{
					extendAttackReach(player);
				}
			}
		}
		else
		{
			if (event.getEntityLiving().getAttributeMap().getAllAttributes().contains(SharedMonsterAttributes.FOLLOW_RANGE))
			{
				event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27"));
			}
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
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingAttackEvent event)
	{
		if (event.getSource().getSourceOfDamage() !=null)
		{
			if (event.getSource().getSourceOfDamage() instanceof EntityLivingBase)
			{
				EntityLivingBase attacker = (EntityLivingBase) event.getSource().getSourceOfDamage();
				if (attacker.getHeldItemMainhand().getItem() instanceof ItemSword && !attacker.world.isRemote)
				{
					IWeaponEffect poison = attacker.getHeldItemMainhand().getCapability(WeaponEffectProvider.WEAPONEFFECT_CAP, null);
					if (poison.getHitsRemaining() > 0 && event.getEntityLiving().hurtResistantTime<10)
					{
						for (PotionEffect effect : PotionUtils.getEffectsFromStack(attacker.getHeldItemMainhand()))
						{
							if (effect.getPotion().isInstant())
							{
								effect.getPotion().affectEntity(null, event.getSource().getSourceOfDamage(), event.getEntityLiving(), effect.getAmplifier(), 1/6D);
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
								poison.setHitsRemaining(poison.getHitsRemaining()-1);
							}
						}
						if (poison.getHitsRemaining() <= 0)
						{
							NBTTagCompound compound = attacker.getHeldItemMainhand().getTagCompound();
							compound.removeTag("Potion");
							compound.removeTag("CustomPotionEffects");
						}
					}
				}
			}
			if (event.getSource().getSourceOfDamage() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
				if (player.getHeldItemMainhand().getItem() instanceof ICustomWeapon)
				{
					ICustomWeapon weapon = (ICustomWeapon) player.getHeldItemMainhand().getItem();
					if (event.getAmount()==1&&weapon.noSweepAttack())
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
	public void onEvent(LivingJumpEvent event)
	{
		if(event.getEntityLiving().getActivePotionEffect(ModPotions.stun)!=null)
		{
			event.getEntityLiving().motionY=0;
		}
	}
	
	
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(AttachCapabilitiesEvent.Item event)
	{
		if (event.getItemStack().getItem() instanceof ItemSword)
		{
			if (!event.getItemStack().hasCapability(WeaponEffectProvider.WEAPONEFFECT_CAP, null))
			{
				event.addCapability(WEAPONEFFECT_CAP, new WeaponEffectProvider());
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(AttachCapabilitiesEvent.Entity event)
	{
		if ((!event.getEntity().hasCapability(SpearsInProvider.SPEARSIN_CAP, null))&&(event.getEntity() instanceof EntityLivingBase))
		{
			event.addCapability(SPEARSIN_CAP, new SpearsInProvider());
		}
		
		if ((!event.getEntity().hasCapability(EntitySpeedProvider.ENTITYSPEED_CAP, null))&&(event.getEntity() instanceof EntityLivingBase))
		{
			event.addCapability(ENTITYSPEED_CAP, new EntitySpeedProvider());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() instanceof ItemSword)
		{
			IWeaponEffect poison = event.getItemStack().getCapability(WeaponEffectProvider.WEAPONEFFECT_CAP, null);
			
			if (!PotionUtils.getEffectsFromStack(event.getItemStack()).isEmpty() && poison.getHitsRemaining() > 0)
			{
				List<PotionEffect> list = PotionUtils.getEffectsFromStack(event.getItemStack());
				
				for (PotionEffect potioneffect : list)
	            {
	                String s1 = I18n.translateToLocal(potioneffect.getEffectName()).trim();
	                Potion potion = potioneffect.getPotion();

	                if (potioneffect.getAmplifier() > 0)
	                {
	                    s1 = s1 + " " + I18n.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
	                }

	                if (potioneffect.getDuration() > 20)
	                {
	                    s1 = s1 + " (" + Potion.getPotionDurationString(potioneffect, 0.125F) + ")";
	                }

	                if (potion.isBadEffect())
	                {
	                    event.getToolTip().add(TextFormatting.RED + s1);
	                }
	                else
	                {
	                	event.getToolTip().add(TextFormatting.BLUE + s1);
	                }
	            }
				
				event.getToolTip().add(poison.getHitsRemaining() + " hits remaining");
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingHurtEvent event)
	{
		Entity source = event.getSource().getSourceOfDamage();
		if (source instanceof EntityLivingBase && !source.world.isRemote)
		{
			EntityLivingBase living = (EntityLivingBase) source;
			if (living.getHeldItemMainhand()!=null)
			{
				if (living.getHeldItemMainhand().getItem() instanceof ItemSpear && living.hasCapability(EntitySpeedProvider.ENTITYSPEED_CAP, null))
				{
					IEntitySpeed speed = living.getCapability(EntitySpeedProvider.ENTITYSPEED_CAP, null);
					double A = speed.getPrevX() - living.posX;
					double B = speed.getPrevZ() - living.posZ;
					double v = Math.sqrt(A*A + B*B)*20;
					if (v > 4.37)
					{
						float newdamage = v > 13.11 ? (float) (event.getAmount()*3) : (float) (event.getAmount()*(v/4.37));
						event.setAmount(newdamage);
					}
				}
				if (living.getHeldItemMainhand().getItem() instanceof ItemDagger)
				{
					float attackerYaw = Math.min(-living.rotationYaw, 360+living.rotationYaw);
					float targetYaw = Math.min(-event.getEntity().rotationYaw, 360+event.getEntity().rotationYaw);
					if (Math.abs(attackerYaw-targetYaw)<45)
					{
						float newdamage = (float) (event.getAmount()*2);
						event.setAmount(newdamage);
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(LivingDeathEvent event)
	{
		ISpearsIn spearsin = event.getEntity().getCapability(SpearsInProvider.SPEARSIN_CAP, null);
		ArrayList<ItemStack> spears = spearsin.getSpearsIn();
		if (!spears.isEmpty())
		{
		for (ItemStack spear : spears)
			{
				event.getEntity().entityDropItem(spear, 0);
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(EnderTeleportEvent event)
	{
		if (event.getEntityLiving().getActivePotionEffect(ModPotions.antiwarp)!=null)
		{
			event.setCanceled(true);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(RenderPlayerEvent.Post event)
	{
		EntityPlayer player = event.getEntityPlayer();
		if (player.getActiveItemStack().getItem() instanceof ItemSpear)
		{
			float progress = Math.min((player.getActiveItemStack().getMaxItemUseDuration() - player.getItemInUseCount()) / 20.0F, 1.0F);
			if (player.getPrimaryHand() == EnumHandSide.RIGHT)
			{
				ModelRenderer RightArm = event.getRenderer().getMainModel().bipedRightArm;
				RightArm.isHidden=false;
				RightArm.rotationPointZ = -0.9F*MathHelper.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0F;
				if (player.isSneaking())
				{
					RightArm.rotationPointY = 17;
				}
				else
				{
					RightArm.rotationPointY = 20;
				}
				RightArm.rotationPointX = -0.9F*MathHelper.cos((float) Math.toRadians(player.renderYawOffset)) * 5.0F;
				RightArm.rotateAngleX=(float) 0.7F - progress;
				RightArm.rotateAngleY=(float) -Math.toRadians(player.renderYawOffset);
				RightArm.rotateAngleZ=(float) 0.05;
				event.getRenderer().bindTexture(((AbstractClientPlayer) player).getLocationSkin());
				RightArm.renderWithRotation(0.0625F);
				RightArm.rotationPointY = 2;
			}
			else
			{
				ModelRenderer LeftArm = event.getRenderer().getMainModel().bipedRightArm;
				LeftArm.isHidden=false;
				LeftArm.rotationPointZ = 1.25F*MathHelper.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0F;
				if (player.isSneaking())
				{
					LeftArm.rotationPointY = 17;
				}
				else
				{
					LeftArm.rotationPointY = 20;
				}
				LeftArm.rotationPointX = 1.25F*MathHelper.cos((float) Math.toRadians(player.renderYawOffset)) * 5.0F;
				LeftArm.rotateAngleX=(float) 0.7F - progress;
				LeftArm.rotateAngleY=(float) -Math.toRadians(player.renderYawOffset);
				LeftArm.rotateAngleZ=(float) -0.05;
				event.getRenderer().bindTexture(((AbstractClientPlayer) player).getLocationSkin());
				LeftArm.renderWithRotation(0.0625F);
				LeftArm.rotationPointY = 2;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(RenderPlayerEvent.Pre event)
	{
		
		event.getRenderer().getMainModel().bipedLeftArm.isHidden=false;
		EntityPlayer player = event.getEntityPlayer();
		if (player.getActiveItemStack().getItem() instanceof ItemSpear)
		{
			if(player.getPrimaryHand() == EnumHandSide.RIGHT)
			{
				event.getRenderer().getMainModel().bipedRightArm.isHidden=true;
			}
			else
			{
				event.getRenderer().getMainModel().bipedLeftArm.isHidden=true;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(EntityViewRenderEvent.FogDensity event)
	{
		if (event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) event.getEntity();
			if(living.getActivePotionEffect(ModPotions.stun)!=null)
			{
				GlStateManager.setFogStart(0);
				GlStateManager.setFogEnd(1);
				event.setDensity (1);
				event.setCanceled(true);
			} // must be canceled to affect the fog density
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(EntityViewRenderEvent.FogColors event)
	{
		if (event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) event.getEntity();
			if(living.getActivePotionEffect(ModPotions.stun)!=null)
			{
				event.setBlue(0);
				event.setRed(0);
				event.setGreen(0);
			}
		}
	}
	
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
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(MouseEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer thePlayer = mc.player;
		if (event.getButton() == 0 && event.isButtonstate())
	    {
	    	extendAttackReach(thePlayer);
	    }
	}
	
	// This is mostly copied from the EntityRenderer#getMouseOver() method
		public static RayTraceResult getMouseOverExtended(float dist)
		{
		    Minecraft mc = FMLClientHandler.instance().getClient();
		    Entity theRenderViewEntity = mc.getRenderViewEntity();
		    AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(
		            theRenderViewEntity.posX-0.5D,
		            theRenderViewEntity.posY-0.0D,
		            theRenderViewEntity.posZ-0.5D,
		            theRenderViewEntity.posX+0.5D,
		            theRenderViewEntity.posY+1.5D,
		            theRenderViewEntity.posZ+0.5D
		            );
		    RayTraceResult returnMOP = null;
		    if (mc.world != null)
		    {
		        double var2 = dist;
		        returnMOP = theRenderViewEntity.rayTrace(var2, 0);
		        double calcdist = var2;
		        Vec3d pos = theRenderViewEntity.getPositionEyes(0);
		        var2 = calcdist;
		        if (returnMOP != null)
		        {
		            calcdist = returnMOP.hitVec.distanceTo(pos);
		        }
		         
		        Vec3d lookvec = theRenderViewEntity.getLook(0);
		        Vec3d var8 = pos.addVector(lookvec.xCoord * var2, 
		              lookvec.yCoord * var2, 
		              lookvec.zCoord * var2);
		        Entity pointedEntity = null;
		        float var9 = 1.0F;
		        @SuppressWarnings("unchecked")
		        List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(
		              theRenderViewEntity, 
		              theViewBoundingBox.addCoord(
		                    lookvec.xCoord * var2, 
		                    lookvec.yCoord * var2, 
		                    lookvec.zCoord * var2).expand(var9, var9, var9));
		        double d = calcdist;
		            
		        for (Entity entity : list)
		        {
		            if (entity.canBeCollidedWith())
		            {
		                float bordersize = entity.getCollisionBorderSize();
		                AxisAlignedBB aabb = new AxisAlignedBB(
		                      entity.posX-entity.width/2, 
		                      entity.posY, 
		                      entity.posZ-entity.width/2, 
		                      entity.posX+entity.width/2, 
		                      entity.posY+entity.height, 
		                      entity.posZ+entity.width/2);
		                aabb.expand(bordersize, bordersize, bordersize);
		                RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);
		                    
		                if (aabb.isVecInside(pos))
		                {
		                    if (0.0D < d || d == 0.0D)
		                    {
		                        pointedEntity = entity;
		                        d = 0.0D;
		                    }
		                } else if (mop0 != null)
		                {
		                    double d1 = pos.distanceTo(mop0.hitVec);
		                        
		                    if (d1 < d || d == 0.0D)
		                    {
		                        pointedEntity = entity;
		                        d = d1;
		                    }
		                }
		            }
		        }
		           
		        if (pointedEntity != null && (d < calcdist || returnMOP == null))
		        {
		             returnMOP = new RayTraceResult(pointedEntity);
		        }
		    }
		    return returnMOP;
		}

		@SideOnly(Side.CLIENT)
		private void extendAttackReach(EntityPlayer thePlayer)
		{
	        if (thePlayer != null)
	        {
	            ItemStack itemstack = thePlayer.getHeldItemMainhand();
	            ICustomWeapon ieri;
	            if (itemstack != null)
	            {
	                if (itemstack.getItem() instanceof ICustomWeapon)
	                {
	                    ieri = (ICustomWeapon) itemstack.getItem();
	                } else
	                {
	                    ieri = null;
	                }
	   
	                if (ieri != null)
	                {
	                    float reach = ieri.getReach();
	                    RayTraceResult mov = getMouseOverExtended(reach);
	                    if (mov != null)
	                    {
	                        if (mov.entityHit != null)
	                        {
	                            if (mov.entityHit != thePlayer && mov.entityHit.hurtResistantTime < 10)
	                            {
	                            	if (mov.entityHit instanceof EntityLivingBase)
	                            	{
	                            		EntityLivingBase living = (EntityLivingBase) mov.entityHit;
	                            		if (living.deathTime > 0) return;
	                            	}
	                                Bettersurvival.network.sendToServer(new MessageExtendedReachAttack(mov.entityHit.getEntityId()));
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    
		}

}
