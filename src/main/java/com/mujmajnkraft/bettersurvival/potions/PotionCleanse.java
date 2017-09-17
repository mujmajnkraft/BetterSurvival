package com.mujmajnkraft.bettersurvival.potions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionCleanse extends Potion{

	public PotionCleanse(boolean isBadEffectIn, int liquidColorIn) {
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
		Collection<PotionEffect> activeeffects = entityLiving.getActivePotionEffects();
		ArrayList<PotionEffect> effectstoremove = new ArrayList<PotionEffect>();
		for (PotionEffect effect : activeeffects)
		{
			if (effect.getPotion().isBeneficial() && this.isBadEffect() && effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
			{
				effectstoremove.add(effect);
			}
			else if (!effect.getPotion().isBeneficial() && !this.isBadEffect() && effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
			{
				effectstoremove.add(effect);
			}
		}
		Iterator<PotionEffect> itr= effectstoremove.iterator();
		while (itr.hasNext())
		{
			Potion potion = itr.next().getPotion();
			entityLiving.removePotionEffect(potion);
		}
    }
	
	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLiving, int amplifier, double health)
	{
		Collection<PotionEffect> activeeffects = entityLiving.getActivePotionEffects();
		ArrayList<PotionEffect> effectstoremove = new ArrayList<PotionEffect>();
		for (PotionEffect effect : activeeffects)
		{
			if (effect.getPotion().isBeneficial() && this.isBadEffect() && effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
			{
				effectstoremove.add(effect);
			}
			else if (!effect.getPotion().isBeneficial() && !this.isBadEffect() && effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
			{
				effectstoremove.add(effect);
			}
		}
		Iterator<PotionEffect> itr= effectstoremove.iterator();
		while (itr.hasNext())
		{
			Potion potion = itr.next().getPotion();
			entityLiving.removePotionEffect(potion);
		}
    }

}
