package com.mujmajnkraft.bettersurvival.client;

import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.integration.RLCombatCompat;
import com.mujmajnkraft.bettersurvival.packet.BetterSurvivalPacketHandler;
import com.mujmajnkraft.bettersurvival.packet.MessageNunchakuSpinClient;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;

import meldexun.reachfix.hook.client.EntityRendererHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import java.util.ArrayList;
import java.util.List;

public class ModClientHandler {
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		Entity rvEntity = mc.getRenderViewEntity();
		EntityPlayerSP player = mc.player;
		GameSettings GS = mc.gameSettings;

		if(player != null && rvEntity != null) {
			INunchakuCombo cap = player.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
			if(player.getHeldItemMainhand().getItem() instanceof ItemNunchaku && !player.isRowingBoat() && player.getActiveItemStack() == ItemStack.EMPTY && GS.keyBindAttack.isKeyDown()) {
				if(cap != null && !cap.isSpinning()) {//Don't spam packets if we're already spinning
					BetterSurvivalPacketHandler.NETWORK.sendToServer(new MessageNunchakuSpinClient(true));
					cap.setSpinning(true);
				}
				if(player.getCooledAttackStrength(0) == 1.0f) {
					RayTraceResult mov = EntityRendererHook.pointedObject(rvEntity, player, EnumHand.MAIN_HAND, mc.world, mc.getRenderPartialTicks());
					if(mov != null && mov.entityHit != null && mov.entityHit != player ) {
						if(mov.entityHit.hurtResistantTime<10) {
							if(BetterSurvival.isRLCombatLoaded) RLCombatCompat.attackEntityFromClient(mov, player);
							else mc.playerController.attackEntity(player, mov.entityHit);
						}
					}
				}
			}
			else if(cap != null && cap.isSpinning()) {
				BetterSurvivalPacketHandler.NETWORK.sendToServer(new MessageNunchakuSpinClient(false));
				cap.setSpinning(false);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onTooltipRender(ItemTooltipEvent event) {
		if(event.getItemStack().hasTagCompound()) {
			int h = event.getItemStack().getTagCompound().getInteger("remainingPotionHits");

			if(h > 0 && !PotionUtils.getEffectsFromStack(event.getItemStack()).isEmpty()) {
				List<PotionEffect> list = PotionUtils.getEffectsFromStack(event.getItemStack());
				List<String> processed = new ArrayList<>();
				for(PotionEffect potioneffect : list) {
					if(processed.contains(potioneffect.getEffectName())) continue;//Don't double display both potion and effect
					processed.add(potioneffect.getEffectName());

					String s1 = I18n.format(potioneffect.getEffectName()).trim();

					Potion potion = potioneffect.getPotion();

					if(potioneffect.getAmplifier() > 0) {
						s1 = s1 + " " + I18n.format("potion.potency." + potioneffect.getAmplifier()).trim();
					}

					if(potioneffect.getDuration() > 20) {
						s1 = s1 + " (" + Potion.getPotionDurationString(potioneffect, 1.0F/(float)ForgeConfigHandler.server.potionDivisor) + ")";
					}

					if(potion.isBadEffect()) {
						event.getToolTip().add(TextFormatting.RED + s1);
					}
					else {
						event.getToolTip().add(TextFormatting.BLUE + s1);
					}
				}

				event.getToolTip().add(h + "/" + ForgeConfigHandler.server.maximumPotionHits + " hits remaining");
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onEvent(FOVUpdateEvent event)
	{
		if(ForgeConfigHandler.client.staticFOV) event.setNewfov(1.0F);
		else if(ForgeConfigHandler.client.fovShields)
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
