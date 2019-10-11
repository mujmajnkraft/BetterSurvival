package com.mujmajnkraft.bettersurvival.client.render;

import com.mujmajnkraft.bettersurvival.client.model.ModelPotionThrower;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityPotionThrower;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderPotionThrower extends Render<EntityPotionThrower>{
	
	public RenderPotionThrower(RenderManager renderManager) {
		super(renderManager);
		// TODO Auto-generated constructor stub
	}
	
	protected ModelBase model = new ModelPotionThrower();

	@Override
	public void doRender(EntityPotionThrower entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
        float f = ((EntityPotionThrower)entity).loadingProgress();
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
            bufferbuilder.pos(-9.5 + 20 * f, 2 , -2).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-9.5 + 20 * f, 3 , -2).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 3 , -6 - d01).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
            
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);//left side
            bufferbuilder.pos(-9.5 + 20 * f, 2 , 2).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 2 , 6 + d01).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-20 + d02, 3 , 6 + d01).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-9.5 + 20 * f, 3 , 2).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
            
            ItemStack potion = entity.getPotion();
            if (potion!= ItemStack.EMPTY)
            {
            	GlStateManager.translate(f*20-13.25, 1.25, 0.25);
            	GlStateManager.scale(6, 6, 6);
            	GlStateManager.rotate(90, 0, 1, 0);
            	GlStateManager.rotate(90, -1, 0, 0);
            	Minecraft.getMinecraft().getRenderItem().renderItem(potion, TransformType.NONE);
            }
        }
        
        GlStateManager.translate(0, 2, 0);
        GlStateManager.rotate(-90, 0, 1, 0);
		
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPotionThrower entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}

}
