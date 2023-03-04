package com.mujmajnkraft.bettersurvival.integration;

import bettercombat.mod.network.PacketHandler;
import bettercombat.mod.network.PacketMainhandAttack;
import bettercombat.mod.util.InFHandler;
import bettercombat.mod.util.Reference;
import com.mujmajnkraft.bettersurvival.BetterSurvival;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

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

    public static void attackEntityFromClient(RayTraceResult mov, EntityPlayer player) {
        if(BetterSurvival.isIafLoaded && InFHandler.isMultipart(mov.entityHit)) {
            mov.entityHit = InFHandler.getMultipartParent(mov.entityHit);
        }

        player.attackTargetEntityWithCurrentItem(mov.entityHit);
        PacketHandler.instance.sendToServer(new PacketMainhandAttack(mov.entityHit.getEntityId()));
    }
}