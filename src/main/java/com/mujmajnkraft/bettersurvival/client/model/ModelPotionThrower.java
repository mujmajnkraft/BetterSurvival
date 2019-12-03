package com.mujmajnkraft.bettersurvival.client.model;

import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityPotionThrower;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPotionThrower extends ModelBase{
	
	private final ModelRenderer partA;
	private final ModelRenderer partB;
	private final ModelRenderer partC;
	private final ModelRenderer partD;
	private final ModelRenderer partE;
	private final ModelRenderer partF;
	private final ModelRenderer partG;
	private final ModelRenderer partH;
	private final ModelRenderer partI;
	private final ModelRenderer partJ;
	
	public ModelPotionThrower() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		partA = new ModelRenderer(this);
		partB = new ModelRenderer(this, 16, 0);
		partC = new ModelRenderer(this, 16, 8);
		partD = new ModelRenderer(this);
		partE = new ModelRenderer(this);
		partF = new ModelRenderer(this);
		partG = new ModelRenderer(this);
		partH = new ModelRenderer(this);
		partI = new ModelRenderer(this);
		partJ = new ModelRenderer(this);
		
		partA.addBox(-1, 0, -1, 2, 13, 2);//support
		partA.addBox(-1, 0, -8, 2, 1, 16);
		partA.addBox(-8, 0, -1, 16, 1, 2);
		partA.addChild(partI);
		partA.addChild(partJ);
		
		partB.addBox(-22, 0, -3, 44, 2, 1);//main part
		partB.addBox(-22, 0, -2, 36, 1, 4);
		partB.addBox(-22, 0, 2, 44, 2, 1);
		partB.addBox(-22, 0, 3, 4, 5, 1);
		partB.addBox(-22, 0, -4, 4, 5, 1);
		partB.addBox(-22, -1, -10, 4, 1, 20);
		partB.addBox(-22, 5, -10, 4, 1, 20);
		partB.addChild(partC);
		partB.addChild(partD);
		partB.addChild(partE);
		partB.addChild(partF);
		partB.addChild(partG);
		this.partB.setRotationPoint(0, 16, 0);
		
		partC.addBox(9.5F, 0, -2, 1, 2, 4);
		
		partD.addBox(-2.5F, -1, 0, 5, 2, 1);
		partD.addBox(-1, -2.5F, 0, 2, 5, 1);
		partD.addBox(-2.5F, -1, 4, 5, 2, 4);
		partD.addBox(-1, -2.5F, 4, 2, 5, 4);
		partD.setRotationPoint(17.5F, 1.5f, -4);

		partE.addBox(-2.5F, -1, 0, 5, 2, 1);
		partE.addBox(-1, -2.5F, 0, 2, 5, 1);
		partE.addBox(-2.5F, -1, 4, 5, 2, 4);
		partE.addBox(-1, -2.5F, 4, 2, 5, 4);
		partE.addBox(-4.5F, -0.5F, 0, 9, 1, 1);
		partE.addBox(-0.5F, -4.5F, 0, 1, 9, 1);
		partE.setRotationPoint(17.5F, 1.5f, -4);
		
		partF.addBox(1, -0.5F, -0.5F, 12, 2, 1);
		partF.addBox(-1, -2, -1, 2, 6, 2);
		partF.setRotationPoint(-20, 2, -6);
		partF.rotateAngleY = -0.017453292F *30;
		
		partG.addBox(1, -0.5F, -0.5F, 12, 2, 1);
		partG.addBox(-1, -2, -1, 2, 6, 2);
		partG.setRotationPoint(-20, 2, 6);
		partG.rotateAngleY = 0.017453292F *30;
		
		partH.addBox(-3, 13, -3, 6, 1, 6);
		partH.addBox(-3, 14, -3, 6, 4, 1);
		partH.addBox(-3, 14, 2, 6, 4, 1);
		
		partI.addBox(-0.5F, 0, -0.5F, 1, 5, 1);
		partI.addBox(-0.5F, 5, 0.5F, 1, 1, 5);
		partI.rotateAngleX = 0.017453292F * 45;
		
		partJ.addBox(-0.5F, 0, -0.5F, 1, 5, 1);
		partJ.addBox(0.5F, 5, -0.5F, 5, 1, 1);
		partJ.rotateAngleZ = -0.017453292F * 45;
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) 
	{
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		EntityPotionThrower thrower = (EntityPotionThrower) entityIn;
		float f = thrower.loadingProgress();
		
		partI.setRotationPoint(0, 0.5F, -4);
		partJ.setRotationPoint(-4, 0.5F, 0);
		this.partA.render(scale);

		this.partC.setRotationPoint( -20, 1, 0);
		this.partC.offsetX = 20 * f * scale;
		this.partC.isHidden = f <= 0.0F;
		this.partD.rotateAngleZ = -0.017453292F * ((f * 720)+45);
		this.partE.rotateAngleZ = -0.017453292F * f * 720;
		this.partF.rotateAngleY = 0.017453292F * (60 - (15 * f));
		this.partG.rotateAngleY = -0.017453292F * (60 - (15 * f));
		
		this.partB.render(scale);
		this.partH.render(scale);
		
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.partA.rotateAngleY = 0.017453292F * (netHeadYaw - 90);
		this.partB.rotateAngleZ = 0.017453292F * (headPitch);
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}

}
