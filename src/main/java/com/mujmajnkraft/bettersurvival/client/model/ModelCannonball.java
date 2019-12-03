package com.mujmajnkraft.bettersurvival.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonball extends ModelBase{
	
	private final ModelRenderer ball;
	
	public ModelCannonball() {
		ball = new ModelRenderer(this);
		ball.addBox(-4, -4, -4, 8, 8, 8);
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		ball.render(scale);
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

}
