package com.mujmajnkraft.bettersurvival.client.render;

import com.mujmajnkraft.bettersurvival.client.model.ModelZeppelin;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityZeppelin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class RenderZeppelin extends Render<EntityZeppelin>{
	
	protected ModelBase model = new ModelZeppelin();

	public RenderZeppelin(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityZeppelin entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		this.bindEntityTexture(entity);
		GlStateManager.rotate(90-entityYaw, 0, 1, 0);
		this.model.render(entity, 0.0F, 0.0F, -0.1F, entity.rotationYaw, entity.rotationPitch, 0.0625F);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityZeppelin entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}

}
