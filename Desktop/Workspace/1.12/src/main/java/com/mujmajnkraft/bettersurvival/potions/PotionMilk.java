package com.mujmajnkraft.bettersurvival.potions;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class PotionMilk extends Potion{

	public PotionMilk(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
	}
	
	public boolean isInstant()
    {
        return true;
    }
	
	public boolean isReady(int duration, int amplifier)
    {
        return duration >= 1;
    }
	
	@Override
	public void performEffect(EntityLivingBase entityLiving, int p_76394_2_)
    {
		entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }
	
	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLiving, int amplifier, double health)
    {
		entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }


}
