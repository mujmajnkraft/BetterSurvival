package com.mujmajnkraft.bettersurvival.client.render;

import com.mujmajnkraft.bettersurvival.client.model.ModelBallistaBolt;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityBallistaBolt;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderBallistaBolt extends Render<EntityBallistaBolt>{

	private ModelBase model = new ModelBallistaBolt();
	public RenderBallistaBolt(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityBallistaBolt entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(entity.rotationYaw, 0, 1, 0);
		GlStateManager.rotate(-entity.rotationPitch, 1, 0, 0);
		this.bindEntityTexture(entity);
		this.model.render(entity, 0.0F, 0.0F, -0.1F, entity.rotationYaw, entity.rotationPitch, 0.0625F);
		GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(0.0D, -1D, -18.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, -1D, -16.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, 1D, -16.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, 1D, -18.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(0.0D, -1D, 1.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, -0.0D, 3.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 3.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, 1D, 1.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-1.0D, 0.0D, -18.0D).tex(0.0625D, 0.1875D).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(-1.0D, 0.0D, -16.0D).tex(0.125D, 0.1875D).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(1.0D, 0.0D, -16.0D).tex(0.125D, 0.125D).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(1.0D, 0.0D, -18.0D).tex(0.0625D, 0.125D).normal(1.0F, 0.0F, 0.0F).endVertex();
        tessellator.draw();
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-1.0D, 0.0D, 1.0D).tex(0.0625D, 0.1875D).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 3.0D).tex(0.125D, 0.1875D).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 3.0D).tex(0.125D, 0.125D).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(1.0D, 0.0D, 1.0D).tex(0.0625D, 0.125D).normal(1.0F, 0.0F, 0.0F).endVertex();
        tessellator.draw();
        
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBallistaBolt entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}

}
