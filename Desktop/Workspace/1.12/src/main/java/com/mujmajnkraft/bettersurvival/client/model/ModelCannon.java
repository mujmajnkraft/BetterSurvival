package com.mujmajnkraft.bettersurvival.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannon extends ModelBase{
	
	private final ModelRenderer partA;
	private final ModelRenderer partB;
	private final ModelRenderer partC;
	private final ModelRenderer partD;
	private final ModelRenderer partE;
	private final ModelRenderer partF;
	private final ModelRenderer partG;
	
	public ModelCannon()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		partA = new ModelRenderer(this);
		partB = new ModelRenderer(this);
		partC = new ModelRenderer(this);
		partD = new ModelRenderer(this);
		partE = new ModelRenderer(this);
		partF = new ModelRenderer(this);
		partG = new ModelRenderer(this);
		
		partA.setRotationPoint(0, 6, 0);
		partA.addBox(-8, 0, -7, 16, 3, 16);
		partA.addBox(-8, 3, -7, 16, 6, 3);
		partA.addBox(-8, 3, 4F, 16, 6, 3);
		partA.addChild(partG);
		
		partG.setRotationPoint(15, 6, 0);
		partG.addBox(0, 0, -4, 10, 2, 8);
		partG.rotateAngleZ = 0.3F;
		partA.rotateAngleZ = 0.2F;
		
		partB.setRotationPoint(0, 8.2F, -9);
		partB.addBox(-1, -1, -1, 2, 2, 3);
		partB.addBox(-8, -0.5F, -0.5F, 16, 1, 1);
		partB.addBox(-0.5F, -8, -0.5F, 1, 16, 1);
		partB.addBox(-3.5F, -8.2F, -1, 7, 1, 2);
		partB.addBox(-3.5F, 7.2F, -1, 7, 1, 2);
		partB.addBox(-8.2F, -3.5F, -1, 1, 7, 2);
		partB.addBox(7.2F, -3.5F, -1, 1, 7, 2);
		
		partC.setRotationPoint(0, 8.2F, -9);
		partC.addBox(-8, -0.5F, -1.5F, 16, 1, 1);
		partC.addBox(-0.5F, -8, -0.5F, 1, 16, 1);
		partC.addBox(-3.5F, -8.2F, -1, 7, 1, 2);
		partC.addBox(-3.5F, 7.2F, -1, 7, 1, 2);
		partC.addBox(-8.2F, -3.5F, -1, 1, 7, 2);
		partC.addBox(7.2F, -3.5F, -1, 1, 7, 2);
		partC.rotateAngleZ = 0.78539814F;
		
		partD.setRotationPoint(0, 8.2F, 9);
		partD.addBox(-1, -1, -1, 2, 2, 2);
		partD.addBox(-8, -0.5F, -0.5F, 16, 1, 1);
		partD.addBox(-0.5F, -8, -0.5F, 1, 16, 1);
		partD.addBox(-3.5F, -8.2F, -1, 7, 1, 2);
		partD.addBox(-3.5F, 7.2F, -1, 7, 1, 2);
		partD.addBox(-8.2F, -3.5F, -1, 1, 7, 2);
		partD.addBox(7.2F, -3.5F, -1, 1, 7, 2);
		
		partE.setRotationPoint(0, 8.2F, 9);
		partE.addBox(-8, -0.5F, -0.5F, 16, 1, 1);
		partE.addBox(-0.5F, -8, -0.5F, 1, 16, 1);
		partE.addBox(-3.5F, -8.2F, -1, 7, 1, 2);
		partE.addBox(-3.5F, 7.2F, -1, 7, 1, 2);
		partE.addBox(-8.2F, -3.5F, -1, 1, 7, 2);
		partE.addBox(7.2F, -3.5F, -1, 1, 7, 2);
		partE.rotateAngleZ = 0.78539814F;
		
		partF.setRotationPoint(0, 17, 0);
		partF.addBox(-14, -2F, -3F, 29, 4, 6);
		partF.addBox(-14, -3, -2, 29, 1, 4);
		partF.addBox(-14, 2, -2, 29, 1, 4);
		partF.addBox(-1, -1, -6, 2, 2, 12);
		
		partF.addBox(-16, -3, -3, 2, 6, 6);
		partF.addBox(-16, -4, -2, 2, 1, 4);
		partF.addBox(-16, 3, -2, 2, 1, 4);
		partF.addBox(-16, -2, -4, 2, 4, 1);
		partF.addBox(-16, -2, 3, 2, 4, 1);
		
		partF.addBox(-6, -3, -3, 2, 6, 6);
		partF.addBox(-6, -3.5F, -2, 2, 1, 4);
		partF.addBox(-6, 2.5F, -2, 2, 1, 4);
		partF.addBox(-6, -2, -3.5F, 2, 4, 1);
		partF.addBox(-6, -2, 2.5F, 2, 4, 1);

		partF.addBox(4, -3, -3, 2, 6, 6);
		partF.addBox(4, -3.5F, -2, 2, 1, 4);
		partF.addBox(4, 2.5F, -2, 2, 1, 4);
		partF.addBox(4, -2, -3.5F, 2, 4, 1);
		partF.addBox(4, -2, 2.5F, 2, 4, 1);
		
		partF.addBox(15, -1, -1, 1, 2, 2);
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		double velocity = Math.sqrt(entityIn.motionX*entityIn.motionX+entityIn.motionZ*entityIn.motionZ);
		if (velocity > 0.01)
		{
			float wheelAngel = (float) (entityIn.ticksExisted/20.0F + velocity);
			partB.rotateAngleZ = wheelAngel;
			partC.rotateAngleZ = 0.78539814F + wheelAngel;
			partD.rotateAngleZ = (float) wheelAngel;
			partE.rotateAngleZ = 0.78539814F + wheelAngel;
		}
		partA.rotateAngleZ = -0.1F;
		partG.rotateAngleZ = -0.3F;
		partG.setRotationPoint(7, 0, 0);
		
		partF.rotateAngleZ = (float) Math.toRadians(entityIn.rotationPitch);
		this.partA.render(scale);
		this.partB.render(scale);
		this.partC.render(scale);
		this.partD.render(scale);
		this.partE.render(scale);
		this.partF.render(scale);
	}

}
