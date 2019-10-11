package com.mujmajnkraft.bettersurvival.client.render;

import javax.annotation.Nonnull;

import com.mujmajnkraft.bettersurvival.entities.EntityFlyingSpear;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderFlyingSpear extends Render<EntityFlyingSpear>
{
	
	public RenderFlyingSpear(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(@Nonnull EntityFlyingSpear entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		//EntityItem spear = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, new ItemStack(Items.STICK));
		
		//spear.hoverStart = 0F;
		
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x-0*Math.sin(entity.rotationYaw), y-0*Math.sin(entity.rotationYaw), z-0*Math.cos(entity.rotationYaw));
			GlStateManager.scale(2, 2, 2);
			GlStateManager.rotate(entity.rotationYaw, 0, 1, 0);
			GlStateManager.rotate(-entity.rotationPitch, 1, 0, 0);
			GlStateManager.rotate(-90, 0, 1, 0);
			GlStateManager.rotate(-45, 0, 0, 1);
			Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(ModItems.debug), TransformType.NONE);
		}
		GlStateManager.popMatrix();
	}
	    
	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingSpear entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}
}