package com.mujmajnkraft.bettersurvival.integration;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.mujmajnkraft.bettersurvival.BetterSurvival;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    public static float getMaterialModifier(ItemStack stack, EntityLivingBase target, @Nullable EntityPlayer player) {
        return getMaterialModifier(stack, target, player, true);
    }

    public static float getMaterialModifier(ItemStack stack, EntityLivingBase target, @Nullable EntityPlayer player, boolean effect) {
        if(BetterSurvival.isIafLightningForkLoaded) return InFLightningForkCompat.getMaterialModifier(stack, target, player, effect);
        return InFMainCompat.getMaterialModifier(stack, target, player, effect);
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
        if(BetterSurvival.isIafLightningForkLoaded) return InFLightningForkCompat.isStoned(entity);
        return InFMainCompat.isStoned(entity);
    }
}
