package com.mujmajnkraft.bettersurvival.potions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCleanse extends Potion {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/misc/potions.png");

	public PotionCleanse(boolean isBadEffectIn, int liquidColorIn, int iconIndex) {
		super(isBadEffectIn, liquidColorIn);
		this.setIconIndex(iconIndex % 8, iconIndex / 8);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		return super.getStatusIconIndex();
	}

}
