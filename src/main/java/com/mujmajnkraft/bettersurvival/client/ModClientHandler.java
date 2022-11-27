package com.mujmajnkraft.bettersurvival.client;

import com.mujmajnkraft.bettersurvival.BetterSurvivalPacketHandler;
import com.mujmajnkraft.bettersurvival.MessageNunchakuSpinClient;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProwider;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ModClientHandler {
	
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

	/*
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
	*/

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
