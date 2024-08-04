package com.mujmajnkraft.bettersurvival.integration;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.items.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RLCombatCompatEventHandler {

    //Handle InF material modifiers
    @SubscribeEvent
    public void onDamageModifyPre(RLCombatModifyDamageEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        if(!player.world.isRemote && event.getTarget() instanceof EntityLivingBase) {
            ItemStack stack = player.getHeldItem(event.getOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if(stack.getItem() instanceof ItemCustomWeapon) {
                if(BetterSurvival.isIafLoaded) {
                    event.setDamageModifier(event.getDamageModifier() + InFCompat.getMaterialModifier(stack, (EntityLivingBase)event.getTarget(), player));
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

            if(stack.getItem() instanceof ItemDagger) {
                //Dagger
                float multiplier = ((ItemDagger)stack.getItem()).getBackstabMultiplier(player, event.getTarget(), event.getOffhand());
                float modifier = (multiplier-1) * event.getBaseDamage();
                event.setDamageModifier(event.getDamageModifier() + modifier);
            }
            else if(!event.getOffhand() && stack.getItem() instanceof ItemNunchaku) {
                //Nunchuku (Mainhand only)
                INunchakuCombo combo = player.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
                if(combo != null) {
                    event.setDamageModifier(event.getDamageModifier() + (event.getBaseDamage() * combo.getComboPower()));
                }
            }
            else if(stack.getItem() instanceof ItemSpear) {
                //Spear
                if(!player.capabilities.isCreativeMode && ((ItemSpear)stack.getItem()).breakChance() >= player.world.rand.nextFloat()) {
                    stack.shrink(1);
                }
            }
            else if(stack.getItem() instanceof ItemHammer && event.getTarget() instanceof EntityLivingBase) {
                //Hammer
                if(event.getCooledStrength() > 0.9) {
                    int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.bash, stack);
                    if(player.world.rand.nextFloat()<(ForgeConfigHandler.weapons.stunBaseChance + l*ForgeConfigHandler.weapons.bashModifier) && !event.getTarget().getIsInvulnerable()) {
                        PotionEffect potioneffectIn = new PotionEffect(ModPotions.stun, ((ItemHammer)stack.getItem()).stunduration);
                        ((EntityLivingBase)event.getTarget()).addPotionEffect(potioneffectIn);
                    }
                }
            }
            else if(stack.getItem() instanceof ItemBattleAxe && event.getTarget() instanceof EntityLivingBase) {
                //BattleAxe
                if(event.getCooledStrength() > 0.9) {
                    int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.disarm, stack);
                    if(player.world.rand.nextFloat()<(ForgeConfigHandler.weapons.disarmBaseChance + l*ForgeConfigHandler.weapons.disarmModifier) && !event.getTarget().getIsInvulnerable()) {
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

                    if(h > 0 && event.getTarget().hurtResistantTime<10) {
                        for(PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                            if(effect.getPotion().isInstant()) {
                                event.getTarget().hurtResistantTime = 0;
                                effect.getPotion().affectEntity(null, player, (EntityLivingBase)event.getTarget(), effect.getAmplifier(), 1/6D);
                            }
                            else {
                                ((EntityLivingBase)event.getTarget()).addPotionEffect(new PotionEffect(effect.getPotion(), Math.max(effect.getDuration()/ForgeConfigHandler.potions.potionDivisor, 1), effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
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