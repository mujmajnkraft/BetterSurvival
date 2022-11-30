package com.mujmajnkraft.bettersurvival.integration;

import knightminer.inspirations.common.Config;
import knightminer.inspirations.library.InspirationsRegistry;

public class InspirationsCauldronCompat {

    //Only actually register if you can put potions in the cauldron
    public static void initCauldronRecipes() {
        if(Config.enableCauldronPotions) {
            InspirationsRegistry.addCauldronRecipe(new PotionTippedSwordRecipe());
        }
    }

    //Don't bother registering the custom cauldron if inspirations already overwrites it, even if potions are disabled
    public static boolean inspirationsExtendedCauldron() {
        return Config.enableExtendedCauldron;
    }
}
