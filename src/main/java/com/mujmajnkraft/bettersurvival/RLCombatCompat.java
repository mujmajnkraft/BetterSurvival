package com.mujmajnkraft.bettersurvival;

import bettercombat.mod.util.Reference;

public abstract class RLCombatCompat {
    public static boolean isCorrectVersion() {
        char s = Reference.VERSION.charAt(0);
        try {
            int i = Integer.parseInt(String.valueOf(s));
            if(i == 2) return true;
        }
        catch(Exception ignored) { }
        return false;
    }
}