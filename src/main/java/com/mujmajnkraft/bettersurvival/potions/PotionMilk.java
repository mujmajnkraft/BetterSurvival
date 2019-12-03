package com.mujmajnkraft.bettersurvival.potions;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionMilk extends Potion {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/misc/potions.png");

	public PotionMilk(boolean isBadEffectIn, int liquidColorIn, int iconIndex) {
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
		entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }
	
	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLiving, int amplifier, double health)
    {
		entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		return super.getStatusIconIndex();
	}
}
