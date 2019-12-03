package com.mujmajnkraft.bettersurvival.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBallistaBolt extends ModelBase {
	
	private final ModelRenderer main;
	
	public ModelBallistaBolt()
	{
		main = new ModelRenderer(this);
		main.addBox(-0.5F, -0.5F, -18, 1, 1, 20);
		main.setRotationPoint(0, 0, 0);
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.main.render(scale);
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

}
