package com.mujmajnkraft.bettersurvival.mixin;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Inject(
            method = "attemptTeleport",
            at = @At("HEAD"),
            cancellable = true
    )
    public void betterSurvival_EntityLivingBase_attemptTeleport(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if(ForgeConfigHandler.server.antiWarpMixin && ((EntityLivingBase)(Object)this).getActivePotionEffect(ModPotions.antiwarp) != null) cir.setReturnValue(false);
    }
}
