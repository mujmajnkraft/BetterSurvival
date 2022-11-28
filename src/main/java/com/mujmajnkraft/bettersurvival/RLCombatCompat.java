package com.mujmajnkraft.bettersurvival;

import bettercombat.mod.util.Reference;

public abstract class RLCombatCompat {
    public static boolean isCorrectVersion() {
        return Reference.VERSION.startsWith("2");
    }
}
