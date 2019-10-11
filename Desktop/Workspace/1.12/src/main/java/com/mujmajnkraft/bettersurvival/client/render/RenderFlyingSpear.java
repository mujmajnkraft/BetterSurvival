package com.mujmajnkraft.bettersurvival.client.render;

import javax.annotation.Nonnull;

import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderFlyingSpear extends Render<EntityFlyingSpear>
{
	private ItemStack spear;
	
	public RenderFlyingSpear(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(@Nonnull EntityFlyingSpear entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		spear = entity.getSpear();
		
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x-0*Math.sin(entity.rotationYaw), y-0*Math.sin(entity.rotationYaw), z-0*Math.cos(entity.rotationYaw));
			GlStateManager.scale(1, 1, 1);
			GlStateManager.rotate(entity.rotationYaw, 0, 1, 0);
			GlStateManager.rotate(-entity.rotationPitch, 1, 0, 0);
			GlStateManager.rotate(-90, 0, 1, 0);
			GlStateManager.rotate(-45, 0, 0, 1);
			Minecraft.getMinecraft().getRenderItem().renderItem(spear, TransformType.NONE);
		}
		GlStateManager.popMatrix();
	}
	    
	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingSpear entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}
}