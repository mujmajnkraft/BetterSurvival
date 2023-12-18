package com.mujmajnkraft.bettersurvival.integration;

import com.github.alexthe666.iceandfire.api.ChainLightningUtils;
import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import com.mujmajnkraft.bettersurvival.items.ItemCustomWeapon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;

public abstract class InFLightningForkCompat {

    public static final Item.ToolMaterial COPPER = com.github.alexthe666.iceandfire.core.ModItems.copperTools;
    public static final Item.ToolMaterial DRAGON_BONE_LIGHTNING = com.github.alexthe666.iceandfire.core.ModItems.lightningBoneTools;

    public static float getMaterialModifier(ItemStack stack, EntityLivingBase target, @Nullable EntityPlayer player, boolean effect) {
        if (!(stack.getItem() instanceof ItemCustomWeapon)) {
            return 0.0F;
        }
        Item.ToolMaterial mat = ((ItemCustomWeapon) stack.getItem()).getMaterial();
        if(mat == InFCompat.SILVER) {
            if(target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
                return 2.0F;
            }
        }
        else if(mat == InFCompat.DESERT_CHITIN || mat == InFCompat.JUNGLE_CHITIN) {
            if(target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD) {
                return 4.0F;
            }
            if(InFCompat.isDeathworm(target)) {
                return 4.0F;
            }
        }
        else if(mat == InFCompat.DRAGON_BONE_ICED) {
            if(effect) {
                IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(target);
                capability.setFrozen(200);
                target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
                target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
                if(player != null) target.knockBack(target, 1F, player.posX - target.posX, player.posZ - target.posZ);
            }
            if(InFCompat.isFireDragon(target)) {
                return 8.0F;
            }
        }
        else if(mat == InFCompat.DRAGON_BONE_FLAMED) {
            if(effect) {
                target.setFire(5);
                if(player != null) target.knockBack(target, 1F, player.posX - target.posX, player.posZ - target.posZ);
            }
            if(InFCompat.isIceDragon(target)) {
                return 8.0F;
            }
        }
        else if(mat == InFLightningForkCompat.DRAGON_BONE_LIGHTNING) {
            if(effect) {
                ChainLightningUtils.createChainLightningFromTarget(target.world, target, player);
                if(player != null) target.knockBack(target, 1F, player.posX - target.posX, player.posZ - target.posZ);
            }
            if(InFCompat.isFireDragon(target) || InFCompat.isIceDragon(target)) {
                return 4.0F;
            }
        }

        return 0.0F;
    }

    public static boolean isStoned(EntityLivingBase entity) {
        IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(entity);
        return (capability != null && capability.isStoned());
    }
}
