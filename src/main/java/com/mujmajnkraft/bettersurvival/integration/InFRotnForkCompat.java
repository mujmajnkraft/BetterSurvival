package com.mujmajnkraft.bettersurvival.integration;

import com.github.alexthe666.iceandfire.entity.*;
import com.mujmajnkraft.bettersurvival.items.ItemCustomWeapon;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nullable;

public abstract class InFRotnForkCompat {

    public static final Item.ToolMaterial COPPER = com.github.alexthe666.iceandfire.item.IafItemRegistry.copperTools;
    public static final Item.ToolMaterial DRAGON_BONE_LIGHTNING = com.github.alexthe666.iceandfire.item.IafItemRegistry.lightningBoneTools;

    public static final Item.ToolMaterial SILVER = com.github.alexthe666.iceandfire.item.IafItemRegistry.silverTools;
    public static final Item.ToolMaterial DRAGON_BONE = com.github.alexthe666.iceandfire.item.IafItemRegistry.boneTools;
    public static final Item.ToolMaterial DRAGON_BONE_FLAMED = com.github.alexthe666.iceandfire.item.IafItemRegistry.fireBoneTools;
    public static final Item.ToolMaterial DRAGON_BONE_ICED = com.github.alexthe666.iceandfire.item.IafItemRegistry.iceBoneTools;
    public static final Item.ToolMaterial JUNGLE_CHITIN = EnumHelper.addToolMaterial(
            "JungleChitin",
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getHarvestLevel(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getMaxUses(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEfficiency(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getAttackDamage(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEnchantability());
    public static final Item.ToolMaterial JUNGLE_STINGER = EnumHelper.addToolMaterial(
            "JungleStinger",
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getHarvestLevel(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getMaxUses(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEfficiency(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getAttackDamage(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEnchantability());
    public static final Item.ToolMaterial DESERT_CHITIN = EnumHelper.addToolMaterial(
            "DesertChitin",
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getHarvestLevel(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getMaxUses(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEfficiency(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getAttackDamage(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEnchantability());

    public static final Item.ToolMaterial DESERT_STINGER = EnumHelper.addToolMaterial(
            "DesertStinger",
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getHarvestLevel(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getMaxUses(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEfficiency(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getAttackDamage(),
            com.github.alexthe666.iceandfire.item.IafItemRegistry.myrmexChitin.getEnchantability());

    public static boolean isDeathworm(Entity entity) {
        return entity instanceof EntityDeathWorm;
    }

    public static boolean isFireDragon(Entity entity) {
        return entity instanceof EntityFireDragon;
    }

    public static boolean isIceDragon(Entity entity) {
        return entity instanceof EntityIceDragon;
    }

    public static float getMaterialModifier(ItemStack stack, EntityLivingBase target, @Nullable EntityPlayer player) {
        return getMaterialModifier(stack, target, player, true);
    }

    public static float getMaterialModifier(ItemStack stack, EntityLivingBase target, @Nullable EntityPlayer player, boolean effect) {
        if (!(stack.getItem() instanceof ItemCustomWeapon)) {
            return 0.0F;
        }
        Item.ToolMaterial mat = ((ItemCustomWeapon) stack.getItem()).getMaterial();
        if (mat == SILVER) {
            if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
                return 2.0F;
            }
        } else if (mat == DESERT_CHITIN || mat == JUNGLE_CHITIN) {
            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD) {
                return 4.0F;
            }
            if (isDeathworm(target)) {
                return 4.0F;
            }
        } else if (mat == DESERT_STINGER || mat == JUNGLE_STINGER) {
            target.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 2));
            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD) {
                return 4.0F;
            }
            if (isDeathworm(target)) {
                return 4.0F;
            }
        } else if (mat == DRAGON_BONE_ICED) {
            if (effect) {
                FrozenEntityProperties frozenProps = EntityPropertiesHandler.INSTANCE.getProperties(target, FrozenEntityProperties.class);
                if (frozenProps != null) frozenProps.setFrozenFor(200);
                target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
                target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
                if (player != null) target.knockBack(target, 1F, player.posX - target.posX, player.posZ - target.posZ);
            }
            if (isFireDragon(target)) {
                return 8.0F;
            }
        } else if (mat == DRAGON_BONE_FLAMED) {
            if (effect) {
                target.setFire(5);
                if (player != null) target.knockBack(target, 1F, player.posX - target.posX, player.posZ - target.posZ);
            }
            if (isIceDragon(target)) {
                return 8.0F;
            }
        } else if (mat == InFRotnForkCompat.DRAGON_BONE_LIGHTNING) {
            if (effect) {
                target.world.spawnEntity(new EntityDragonLightningBolt(target.world, target.posX, target.posY, target.posZ, player, target));
                if (player != null) target.knockBack(target, 1F, player.posX - target.posX, player.posZ - target.posZ);
            }
            if (isFireDragon(target) || isIceDragon(target)) {
                return 4.0F;
            }
        }

        return 0.0F;
    }

    public static boolean isStoned(EntityLivingBase entity) {
        StoneEntityProperties capability = EntityPropertiesHandler.INSTANCE.getProperties(entity, StoneEntityProperties.class);
        return (capability != null && capability.isStone);
    }
}
