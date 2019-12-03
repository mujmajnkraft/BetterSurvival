package com.mujmajnkraft.bettersurvival.potions;

import com.mujmajnkraft.bettersurvival.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionStun extends Potion {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/misc/potions.png");

	public PotionStun(boolean isBadEffectIn, int liquidColorIn, int iconIndex) {
		super(isBadEffectIn, liquidColorIn);
		this.setIconIndex(iconIndex % 8, iconIndex / 8);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		return super.getStatusIconIndex();
	}

}
