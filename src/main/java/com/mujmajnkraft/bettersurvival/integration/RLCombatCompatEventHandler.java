package com.mujmajnkraft.bettersurvival.integration;

import bettercombat.mod.capability.CapabilityOffhandCooldown;
import bettercombat.mod.event.RLCombatModifyDamageEvent;
import bettercombat.mod.handler.EventHandlers;
import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.integration.InFCompat;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.items.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class RLCombatCompatEventHandler {

    //Handle InF material modifiers
    @SubscribeEvent
    public void onDamageModifyPre(RLCombatModifyDamageEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        if(!player.world.isRemote && event.getTarget() instanceof EntityLivingBase) {
            ItemStack stack = player.getHeldItem(event.getOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if(stack.getItem() instanceof ItemCustomWeapon) {
                if(BetterSurvival.isIafLoaded) {
                    Item.ToolMaterial mat = ((ItemCustomWeapon)stack.getItem()).getMaterial();
                    event.setDamageModifier(event.getDamageModifier() + InFCompat.getMaterialModifier(mat, (EntityLivingBase)event.getTarget(), player));
                }
            }
        }
    }

    //Handle dagger backstab, nunchuku combo, spear breaking, and potion swords with offhand-sensitive methods
    @SubscribeEvent
    public void onDamageModifyPost(RLCombatModifyDamageEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        if(!player.world.isRemote) {
            ItemStack stack = player.getHeldItem(event.getOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            //Dagger
            if(stack.getItem() instanceof ItemDagger) {
                float multiplier = ((ItemDagger)stack.getItem()).getBackstabMultiplier(player, event.getTarget(), event.getOffhand());
                float modifier = (multiplier-1) * event.getBaseDamage();
                event.setDamageModifier(event.getDamageModifier() + modifier);
            }
            //Nunchuku (Mainhand only)
            else if(!event.getOffhand() && stack.getItem() instanceof ItemNunchaku) {
                INunchakuCombo combo = player.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
                if(combo != null) {
                    event.setDamageModifier(event.getDamageModifier() + (event.getBaseDamage() * combo.getComboPower()));
                }
            }
            //Spear
            else if(stack.getItem() instanceof ItemSpear) {
                if(!player.capabilities.isCreativeMode && ((ItemSpear)stack.getItem()).breakChance() >= player.getRNG().nextFloat()) {
                    stack.shrink(1);
                }
            }
            //Hammer
            else if(stack.getItem() instanceof ItemHammer && event.getTarget() instanceof EntityLivingBase) {
                float cooledStrength;
                if(event.getOffhand()) {
                    CapabilityOffhandCooldown capability = player.getCapability(EventHandlers.TUTO_CAP, null);
                    float ohCooldown = 0;
                    if(capability != null) {
                        int ohCooldownBeginning = capability.getOffhandBeginningCooldown();
                        if(ohCooldownBeginning > 0) ohCooldown = capability.getOffhandCooldown()/(float)ohCooldownBeginning;
                    }
                    cooledStrength = Math.abs(1.0F - ohCooldown);
                }
                else {
                    cooledStrength = player.getCooledAttackStrength(0.5F);
                }
                if(cooledStrength > 0.9) {
                    int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.bash, stack);
                    if(player.getRNG().nextInt(20)<(2+l) && !event.getTarget().getIsInvulnerable()) {
                        PotionEffect potioneffectIn = new PotionEffect(ModPotions.stun, ((ItemHammer)stack.getItem()).stunduration);
                        ((EntityLivingBase)event.getTarget()).addPotionEffect(potioneffectIn);
                    }
                }
            }
            //BattleAxe
            else if(stack.getItem() instanceof ItemBattleAxe && event.getTarget() instanceof EntityLivingBase) {
                float cooledStrength;
                if(event.getOffhand()) {
                    CapabilityOffhandCooldown capability = player.getCapability(EventHandlers.TUTO_CAP, null);
                    float ohCooldown = 0;
                    if(capability != null) {
                        int ohCooldownBeginning = capability.getOffhandBeginningCooldown();
                        if(ohCooldownBeginning > 0) ohCooldown = capability.getOffhandCooldown()/(float)ohCooldownBeginning;
                    }
                    cooledStrength = Math.abs(1.0F - ohCooldown);
                }
                else {
                    cooledStrength = player.getCooledAttackStrength(0.5F);
                }
                if(cooledStrength > 0.9) {
                    int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.disarm, stack);
                    if(player.getRNG().nextInt(20)<(2+l) && !event.getTarget().getIsInvulnerable()) {
                        if(event.getTarget() instanceof EntityPlayer) {
                            EntityItem drop = ((EntityPlayer)event.getTarget()).dropItem(((EntityPlayer)event.getTarget()).inventory.decrStackSize(((EntityPlayer)event.getTarget()).inventory.currentItem, 1), false);
                            if(drop != null) drop.setPickupDelay(40);
                        }
                        else {
                            if(!((EntityLivingBase)event.getTarget()).getHeldItemMainhand().isEmpty()) {
                                ItemStack item = ((EntityLivingBase)event.getTarget()).getHeldItemMainhand();
                                NBTTagCompound nbttagcompound = event.getTarget().writeToNBT(new NBTTagCompound());
                                if(nbttagcompound.hasKey("HandDropChances", 9)) {
                                    NBTTagList nbttaglist = nbttagcompound.getTagList("HandDropChances", 5);
                                    float chance = nbttaglist.getFloatAt(0);
                                    ((EntityLivingBase)event.getTarget()).setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                                    int rnd = event.getTarget().world.rand.nextInt(100);
                                    if(chance*100+EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.LOOTING, stack)>rnd+1) {
                                        ((EntityLivingBase)event.getTarget()).entityDropItem(item, 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Potions on swords, only apply when actually doing damage
            if(event.getTarget() instanceof EntityLivingBase && event.getBaseDamage() > 1.0F) {
                if(stack.hasTagCompound()) {
                    NBTTagCompound compound = stack.getTagCompound();
                    int h = compound.getInteger("remainingPotionHits");

                    if(h > 0) {
                        for(PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                            if(effect.getPotion().isInstant()) {
                                effect.getPotion().affectEntity(null, player, (EntityLivingBase)event.getTarget(), effect.getAmplifier(), 1/6D);
                            }
                            else {
                                event.getEntityLiving().addPotionEffect(new PotionEffect(effect.getPotion(), Math.max(effect.getDuration()/ForgeConfigHandler.server.potionDivisor, 1), effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
                            }
                        }
                        if(!player.capabilities.isCreativeMode) {
                            compound.setInteger("remainingPotionHits", h-1);
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