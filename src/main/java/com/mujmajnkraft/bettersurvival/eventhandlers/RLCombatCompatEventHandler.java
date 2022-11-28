package com.mujmajnkraft.bettersurvival.eventhandlers;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RLCombatCompatEventHandler {

    //Handle dagger backstab, nunchuku combo, spear breaking, and potion swords with offhand-sensitive methods
    @SubscribeEvent
    public void onDamageModifyPost(RLCombatModifyDamageEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        if(!player.world.isRemote) {
            ItemStack stack = player.getHeldItem(event.getOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            //Dagger
            if(stack.getItem() instanceof ItemDagger) {
                float multiplier = ((ItemDagger)stack.getItem()).getBackstabMultiplyer(player, event.getTarget());
                float modifier = (multiplier-1) * event.getBaseDamage();
                event.setDamageModifier(event.getDamageModifier() + modifier);
            }
            //Nunchuku (Mainhand only)
            if(!event.getOffhand() && stack.getItem() instanceof ItemNunchaku) {
                INunchakuCombo combo = player.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
                if(combo != null) {
                    event.setDamageModifier(event.getDamageModifier() + (event.getBaseDamage() * combo.getComboPower()));
                }
            }
            //Spear
            if(stack.getItem() instanceof ItemSpear) {
                if(!player.capabilities.isCreativeMode && ((ItemSpear)stack.getItem()).breakChance() >= player.getRNG().nextFloat()) {
                    stack.shrink(1);
                }
            }
            //Potions on swords, only apply when actually doing damage
            if(stack.getItem() instanceof ItemSword && event.getTarget() instanceof EntityLivingBase && event.getBaseDamage() > 1.0F) {
                if(stack.hasTagCompound()) {
                    NBTTagCompound compound = stack.getTagCompound();
                    int h = compound.getInteger("remainingHits");

                    if(h > 0) {
                        for(PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                            if(effect.getPotion().isInstant()) {
                                effect.getPotion().affectEntity(null, player, (EntityLivingBase)event.getTarget(), effect.getAmplifier(), 1/6D);
                            }
                            else {
                                event.getEntityLiving().addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()/8, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
                            }
                        }
                        if(!player.capabilities.isCreativeMode) {
                            compound.setInteger("remainingHits", h-1);
                            if(h-1 <= 0) {
                                compound.removeTag("Potion");
                                compound.removeTag("CustomPotionEffects");
                            }
                        }
                    }
                }
            }
        }
    }
}