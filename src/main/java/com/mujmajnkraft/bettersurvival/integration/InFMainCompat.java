package com.mujmajnkraft.bettersurvival.integration;

import com.github.alexthe666.iceandfire.entity.FrozenEntityProperties;
import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import com.mujmajnkraft.bettersurvival.items.ItemCustomWeapon;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;

public abstract class InFMainCompat {

    public static float getMaterialModifier(ItemStack stack, EntityLivingBase target, @Nullable EntityPlayer player, boolean effect) {
        if(!(stack.getItem() instanceof ItemCustomWeapon)) {
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
        else if (mat == InFCompat.DESERT_STINGER || mat == InFCompat.JUNGLE_STINGER) {
            target.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 2));
            if(target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD) {
                return 4.0F;
            }
            if(InFCompat.isDeathworm(target)) {
                return 4.0F;
            }
        }
        else if(mat == InFCompat.DRAGON_BONE_ICED) {
            if(effect) {
                FrozenEntityProperties frozenProps = EntityPropertiesHandler.INSTANCE.getProperties(target, FrozenEntityProperties.class);
                frozenProps.setFrozenFor(200);
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
        return 0.0F;
    }

    public static boolean isStoned(EntityLivingBase entity) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(entity, StoneEntityProperties.class);
        return (properties != null && properties.isStone);
    }
}
