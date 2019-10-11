package com.mujmajnkraft.bettersurvival.client.render;

import com.mujmajnkraft.bettersurvival.client.model.ModelBallista;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityBallista;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderBallista extends Render<EntityBallista>{

	//private static final ResourceLocation BALLISTA_TEXTURES = new ResourceLocation(Reference.MOD_ID+":textures/entity/minecart.png");
	protected ModelBase model = new ModelBallista();
	
	public RenderBallista(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityBallista entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		this.bindEntityTexture(entity);
		float angle = (entityYaw + entity.prevRotationYaw)/2;
		GlStateManager.rotate(90 - angle, 0, 1, 0);
		this.model.render(entity, 0.0F, 0.0F, -0.1F, entity.rotationYaw, entity.rotationPitch, 0.0625F);

        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        float f = ((EntityBallista)entity).loadingProgress();
        double d01 = -13.00961183D * Math.cos(3.70363391D + f * 0.26179938D);
        double d02 = -13.00961183D * Math.sin(3.70363391D + f * 0.26179938D);
        
        GlStateManager.translate(0, 16, 0);
        GlStateManager.rotate(entity.rotationPitch, 0, 0, 1);
        
        if (f <= 0)
        {
        	bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(-20 + d02, 2 , -6 - d01).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 2 , 6 + d01).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 3 , 6 + d01).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 3 , -6 - d01).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
        }
        
        else
        {
        	bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);//Right side
            bufferbuilder.pos(-20 + d02, 2 , -6 - d01).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-9.5 + 20 * f, 2 , -1).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-9.5 + 20 * f, 3 , -1).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 3 , -6 - d01).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
            
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);//left side
            bufferbuilder.pos(-9.5 + 20 * f, 2 , 1).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 2 , 6 + d01).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 3 , 6 + d01).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-9.5 + 20 * f, 3 , 1).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
        }
        
        GlStateManager.translate(0, 2, 0);
        GlStateManager.rotate(-90, 0, 1, 0);
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
	protected ResourceLocation getEntityTexture(EntityBallista entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}

}
