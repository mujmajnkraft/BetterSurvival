package com.mujmajnkraft.bettersurvival.client;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mujmajnkraft.bettersurvival.BetterSurvivalPacketHandler;
import com.mujmajnkraft.bettersurvival.MessageExtendedReachAttack;
import com.mujmajnkraft.bettersurvival.MessageNunchakuSpinClient;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProwider;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ModClientHandler {
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(InputEvent event)
	{
		
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player != null)
		{
			if (player.getHeldItemMainhand().getItem() instanceof ItemSpear)
			{
				if (((ItemSpear)player.getHeldItemMainhand().getItem()).reach > 5.0f)
				{
					GameSettings GS = Minecraft.getMinecraft().gameSettings;
					if (GS.keyBindAttack.isPressed())
					{
						extendAttackReach(Minecraft.getMinecraft().player);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		GameSettings GS = Minecraft.getMinecraft().gameSettings;
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player != null)
		{
			if (player.getHeldItemMainhand().getItem() instanceof ItemNunchaku && !player.isRowingBoat() && player.getActiveItemStack() == ItemStack.EMPTY && GS.keyBindAttack.isKeyDown())
			{
				BetterSurvivalPacketHandler.NETWORK.sendToServer(new MessageNunchakuSpinClient(true));
				player.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null).setSpinning(true);
				if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == Type.ENTITY && player.getCooledAttackStrength(0) == 1.0f)
				{
					Minecraft.getMinecraft().playerController.attackEntity(player, Minecraft.getMinecraft().objectMouseOver.entityHit);
				}
			}
			else if (player.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null).isSpinning())
			{
				BetterSurvivalPacketHandler.NETWORK.sendToServer(new MessageNunchakuSpinClient(false));
				player.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null).setSpinning(false);
			}
		}
	}

	public static void getMouseOverExtended(float dist)
	{
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		
        if (entity != null)
        {
            if (Minecraft.getMinecraft().world != null)
            {
                Minecraft.getMinecraft().mcProfiler.startSection("pick");
                double d0 = dist;
                Minecraft.getMinecraft().objectMouseOver = entity.rayTrace(d0, 1.0F);
                Vec3d vec3d = entity.getPositionEyes(1.0F);
                boolean flag = false;
                double d1 = d0;
                
                {
                    if (d0 > 3.0D)
                    {
                        flag = true;
                    }
                }

                if (Minecraft.getMinecraft().objectMouseOver != null)
                {
                    d1 = Minecraft.getMinecraft().objectMouseOver.hitVec.distanceTo(vec3d);
                }

                Vec3d vec3d1 = entity.getLook(1.0F);
                Vec3d vec3d2 = vec3d.addVector(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
                Entity pointedEntity = null;
                Vec3d vec3d3 = null;
                List<Entity> list = Minecraft.getMinecraft().world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
                {
                    public boolean apply(@Nullable Entity p_apply_1_)
                    {
                        return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
                    }
                }));
                double d2 = d1;

                for (int j = 0; j < list.size(); ++j)
                {
                    Entity entity1 = list.get(j);
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double)entity1.getCollisionBorderSize());
                    RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

                    if (axisalignedbb.contains(vec3d))
                    {
                        if (d2 >= 0.0D)
                        {
                            pointedEntity = entity1;
                            vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
                            d2 = 0.0D;
                        }
                    }
                    else if (raytraceresult != null)
                    {
                        double d3 = vec3d.distanceTo(raytraceresult.hitVec);

                        if (d3 < d2 || d2 == 0.0D)
                        {
                            if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract())
                            {
                                if (d2 == 0.0D)
                                {
                                    pointedEntity = entity1;
                                    vec3d3 = raytraceresult.hitVec;
                                }
                            }
                            else
                            {
                                pointedEntity = entity1;
                                vec3d3 = raytraceresult.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }

                if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > dist)
                {
                    pointedEntity = null;
                    Minecraft.getMinecraft().objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing)null, new BlockPos(vec3d3));
                }
                
                if (pointedEntity != null && (d2 < d1 || Minecraft.getMinecraft().objectMouseOver == null))
                {
                	Minecraft.getMinecraft().objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);

                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                    {
                        Minecraft.getMinecraft().pointedEntity = pointedEntity;
                    }
                }

                Minecraft.getMinecraft().mcProfiler.endSection();
            }
        }
	}

	private void extendAttackReach(EntityPlayerSP thePlayer)
	{
        if (!thePlayer.isRowingBoat())
        {
        	ItemStack itemstack = thePlayer.getHeldItemMainhand();
            if (itemstack.getItem() instanceof ItemSpear)
            {
                float reach = ((ItemSpear)itemstack.getItem()).reach;
                getMouseOverExtended(reach);
                if (Minecraft.getMinecraft().objectMouseOver != null)
                {
                    switch (Minecraft.getMinecraft().objectMouseOver.typeOfHit)
                    {
                    	case ENTITY:
	                    {
	                    	BetterSurvivalPacketHandler.NETWORK.sendToServer(new MessageExtendedReachAttack(Minecraft.getMinecraft().objectMouseOver.entityHit.getEntityId()));
	                    	if (!thePlayer.isSpectator())
	                    	{
	                    		thePlayer.attackTargetEntityWithCurrentItem(Minecraft.getMinecraft().objectMouseOver.entityHit);
	                    		thePlayer.resetCooldown();
	                    	}
	                    	break;
	                    }
                    	case BLOCK:
                            BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
                            //Only extends attack reach, not breaking reach
                            if (!Minecraft.getMinecraft().world.isAirBlock(blockpos) && thePlayer.getDistanceSq(blockpos) <= 25)
                            {
                            	Minecraft.getMinecraft().playerController.clickBlock(blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit);
                                break;
                            }

                        case MISS:

                            if (Minecraft.getMinecraft().playerController.isNotCreative())
                            {
                                //this.leftClickCounter = 10;
                            }

                            thePlayer.resetCooldown();
                            net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(thePlayer);
                    }

                    thePlayer.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }
	 
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onTooltipRender(ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() instanceof ItemSword)
		{
			if (event.getItemStack().hasTagCompound())
			{
				int h =event.getItemStack().getTagCompound().getInteger("remainingHits");
				
				if (!PotionUtils.getEffectsFromStack(event.getItemStack()).isEmpty() && h > 0)
				{
					List<PotionEffect> list = PotionUtils.getEffectsFromStack(event.getItemStack());
					
					for (PotionEffect potioneffect : list)
		            {
		                String s1 = I18n.format(potioneffect.getEffectName()).trim();
		                Potion potion = potioneffect.getPotion();

		                if (potioneffect.getAmplifier() > 0)
		                {
		                    s1 = s1 + " " + I18n.format("potion.potency." + potioneffect.getAmplifier()).trim();
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
					
					event.getToolTip().add(h + " hits remaining");
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(FOVUpdateEvent event)
	{
		if (ConfigHandler.FoVany) event.setNewfov(1.0F);
		else if (ConfigHandler.FoVshields)
		{
			//Replicates vanilla behaviour (net.minecraft.client.entity.AbstractClientPlayer.getFovModifier())
			float f = 1.0F;
			
			AbstractClientPlayer player = Minecraft.getMinecraft().player;
	
			if (player.capabilities.isFlying)
			{
				f *= 1.1F;
			}
	
			double speed = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
			//FoV change caused by shield + weightlessness is ignored, it's ugly
			if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(ItemCustomShield.weightModifierUUID) != null)
			{
				double multiplyer = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(ItemCustomShield.weightModifierUUID).getAmount() + 1;
				speed = (double) speed/multiplyer;
			}
			
			f = (float)((double)f * ((speed / (double)player.capabilities.getWalkSpeed() + 1.0D) / 2.0D));
	
			if (player.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
			{
				f = 1.0F;
			}
	
			if (player.isHandActive() && player.getActiveItemStack().getItem() instanceof net.minecraft.item.ItemBow)
			{
				int i = player.getItemInUseMaxCount();
				float f1 = (float)i / 20.0F;
	
				if (f1 > 1.0F)
	            {
	                f1 = 1.0F;
	            }
	            else
	            {
	                f1 = f1 * f1;
	            }
	
	            f *= 1.0F - f1 * 0.15F;
	        }
	        
			event.setNewfov(f);
		}
	}
}
