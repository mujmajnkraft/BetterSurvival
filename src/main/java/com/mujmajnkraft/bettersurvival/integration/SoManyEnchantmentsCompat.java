package com.mujmajnkraft.bettersurvival.integration;

import com.Shultrea.Rin.Enum.EnumList;
import net.minecraft.enchantment.EnumEnchantmentType;

public abstract class SoManyEnchantmentsCompat {

    public static boolean isWeaponSMEEnchant(EnumEnchantmentType type) {
        return type == EnumList.COMBAT_WEAPON || type == EnumList.SWORD || type == EnumList.COMBAT || type == EnumList.ALL_TOOL;
    }

    public static boolean isCombatAxeSMEEnchant(EnumEnchantmentType type) {
        return type == EnumList.COMBAT_AXE;
    }
}