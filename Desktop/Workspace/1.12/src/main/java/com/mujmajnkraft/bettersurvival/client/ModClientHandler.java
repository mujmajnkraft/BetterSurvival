package com.mujmajnkraft.bettersurvival.client;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.MessageExtendedReachAttack;
import com.mujmajnkraft.bettersurvival.MessageUseWeapon;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntitySiegeWeapon;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.text.translation.I18n;
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
			if (player.getHeldItemMainhand().getItem() instanceof ICustomWeapon)
			{
				if (((ICustomWeapon)player.getHeldItemMainhand().getItem()).getReach() > 5.0f)
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
			if (Minecraft.getMinecraft().player.getRidingEntity() instanceof EntitySiegeWeapon)
			{
				boolean l = GS.keyBindLeft.isKeyDown();
				boolean r = GS.keyBindRight.isKeyDown();
				boolean f = GS.keyBindForward.isKeyDown();
				boolean b = GS.keyBindBack.isKeyDown();
				boolean u = GS.keyBindJump.isKeyDown();
				boolean d = GS.keyBindSprint.isKeyDown();
				((EntitySiegeWeapon)Minecraft.getMinecraft().player.getRidingEntity()).updateInputs(l, r, f, b, u, d);
				if (u)
				{
					 Bettersurvival.network.sendToServer(new MessageUseWeapon());
				}
			}
			
			if (player.getHeldItemMainhand().getItem() instanceof ItemNunchaku)
			{
				ItemNunchaku nunchaku = (ItemNunchaku) player.getHeldItemMainhand().getItem();
				if (nunchaku.isSpinning(player.getHeldItemMainhand()) && Minecraft.getMinecraft().objectMouseOver != null)
				{
					if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == Type.ENTITY && player.getCooledAttackStrength(0) == 1.0f)
					{
						Minecraft.getMinecraft().playerController.attackEntity(player, Minecraft.getMinecraft().objectMouseOver.entityHit);
					}
				}
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
            if (itemstack.getItem() instanceof ICustomWeapon)
            {
            	ICustomWeapon ieri = (ICustomWeapon) itemstack.getItem();
                float reach = ieri.getReach();
                getMouseOverExtended(reach);
                if (Minecraft.getMinecraft().objectMouseOver != null)
                {
                    System.out.println("Hitting " + Minecraft.getMinecraft().objectMouseOver + ".");
                    switch (Minecraft.getMinecraft().objectMouseOver.typeOfHit)
                    {
                    	case ENTITY:
	                    {
	                    	Bettersurvival.network.sendToServer(new MessageExtendedReachAttack(Minecraft.getMinecraft().objectMouseOver.entityHit.getEntityId()));
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
					
					event.getToolTip().add(h + " hits remaining");
				}
			}
		}
	}
}
