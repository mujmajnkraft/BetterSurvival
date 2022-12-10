package com.mujmajnkraft.bettersurvival.integration;

import com.github.alexthe666.iceandfire.entity.*;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nullable;

public abstract class InFCompat {

    public static final Item.ToolMaterial SILVER = com.github.alexthe666.iceandfire.core.ModItems.silverTools;
    public static final Item.ToolMaterial DRAGON_BONE = com.github.alexthe666.iceandfire.core.ModItems.boneTools;
    public static final Item.ToolMaterial DRAGON_BONE_FLAMED = com.github.alexthe666.iceandfire.core.ModItems.fireBoneTools;
    public static final Item.ToolMaterial DRAGON_BONE_ICED = com.github.alexthe666.iceandfire.core.ModItems.iceBoneTools;
    public static final Item.ToolMaterial JUNGLE_CHITIN = EnumHelper.addToolMaterial(
            "JungleChitin",
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getHarvestLevel(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getMaxUses(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getEfficiency(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getAttackDamage(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getEnchantability());
    public static final Item.ToolMaterial DESERT_CHITIN = EnumHelper.addToolMaterial(
            "DesertChitin",
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getHarvestLevel(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getMaxUses(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getEfficiency(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getAttackDamage(),
            com.github.alexthe666.iceandfire.core.ModItems.myrmexChitin.getEnchantability());

    public static float getMaterialModifier(Item.ToolMaterial mat, EntityLivingBase target, @Nullable EntityPlayer player) {
        return getMaterialModifier(mat, target, player, true);
    }

    public static float getMaterialModifier(Item.ToolMaterial mat, EntityLivingBase target, @Nullable EntityPlayer player, boolean effect) {
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

    public static boolean isDeathworm(Entity entity) {
        return entity instanceof EntityDeathWorm;
    }

    public static boolean isFireDragon(Entity entity) {
        return entity instanceof EntityFireDragon;
    }

    public static boolean isIceDragon(Entity entity) {
        return entity instanceof EntityIceDragon;
    }

    public static boolean isStoned(EntityLivingBase entity) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(entity, StoneEntityProperties.class);
        return (properties != null && properties.isStone);
    }
}
