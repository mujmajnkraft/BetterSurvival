package com.mujmajnkraft.bettersurvival.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelZeppelin extends ModelBase{
	
	private final ModelRenderer partA;
	private final ModelRenderer partB;
	private final ModelRenderer partC;
	private final ModelRenderer partD;
	private final ModelRenderer partE;
	private final ModelRenderer partF;
	private final ModelRenderer partG;
	
	public ModelZeppelin()
	{
		partA = new ModelRenderer(this);
		partB = new ModelRenderer(this);
		partC = new ModelRenderer(this);
		partD = new ModelRenderer(this);
		partE = new ModelRenderer(this);
		partF = new ModelRenderer(this);
		partG = new ModelRenderer(this);
		
		partA.addBox(-64, 48, -40, 144, 0, 80);
		partA.addBox(-64, 32, 40, 112, 32, 4);
		partA.addBox(-64, 32, -44, 112, 32, 4);
		partA.addBox(-64, 0, -44, 0, 48, 88);
		partA.addBox(-64, 48, -40, 4, 16, 80);
		partA.addBox(140, 48, -8, 4, 16, 16);
		partA.addChild(partB);
		partB.setRotationPoint(24, 32, -44);
		partB.rotateAngleX = 0.017453292F * (-30);
		partB.addBox(-88, -23, 0, 112, 23, 0);
		partA.addChild(partC);
		partC.setRotationPoint(24, 32, 44);
		partC.rotateAngleX = 0.017453292F * 30;
		partC.addBox(-88, -23, 0, 112, 23, 0);
		partA.addChild(partD);
		partD.setRotationPoint(24, 32, -44);
		partD.rotateAngleX = 0.017453292F * (-60);
		partD.addBox(-88, -23, 0, 112, 23, 0);
		partA.addChild(partE);
		partE.setRotationPoint(24, 32, 44);
		partE.rotateAngleX = 0.017453292F * 60;
		partE.addBox(-88, -23, 0, 112, 23, 0);
		partA.addChild(partF);
		partF.setRotationPoint(48, 48, -44);
		partF.rotateAngleY = -0.017453292F * 45;
		partF.addBox(0, 0, 0, 45, 16, 4);
		partA.addChild(partG);
		partG.setRotationPoint(112, 48, 44);
		partG.rotateAngleY = -0.017453292F * 45;
		partG.addBox(0, 0, 0, 45, 16, 4);
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		partA.rotateAngleY = 0.017453292F * 180;
		partE.setRotationPoint(24, 15, 34);
		//System.out.println("Debug");
		this.partA.render(scale);
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

}
