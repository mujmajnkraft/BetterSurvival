package com.mujmajnkraft.bettersurvival.client.render;

import com.mujmajnkraft.bettersurvival.client.model.ModelCannonball;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityCannonball;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class RenderCannonball extends Render<EntityCannonball>{
	
	private ModelBase model = new ModelCannonball();

	public RenderCannonball(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityCannonball entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		this.bindEntityTexture(entity);
		GlStateManager.rotate(entityYaw, 0, 1, 0);
		this.model.render(entity, 0.0F, 0.0F, -0.1F, entity.rotationYaw, entity.rotationPitch, 0.0625F);

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCannonball entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}

}
