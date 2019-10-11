package com.mujmajnkraft.bettersurvival.client;

import java.io.IOException;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.ContainerWorkshop;
import com.mujmajnkraft.bettersurvival.MessageButtonPressed;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.items.ItemBlueprint;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityWorkshop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GUIWorkshop extends GuiContainer{

	private TileEntityWorkshop workshop;
	private GuiButton ButtonCraft;
    private static final ResourceLocation WORKSHOP_EMPTY = new ResourceLocation(Reference.MOD_ID + ":textures/gui/workshop_build.png");
    private static final ResourceLocation WORKSHOP_BUILD_BALLISTA = new ResourceLocation(Reference.MOD_ID + ":textures/gui/workshop_build_ballista.png");
    private static final ResourceLocation WORKSHOP_BUILD_CANNON = new ResourceLocation(Reference.MOD_ID + ":textures/gui/workshop_build_cannon.png");
    private static final ResourceLocation WORKSHOP_BUILD_THROWER = new ResourceLocation(Reference.MOD_ID + ":textures/gui/workshop_build_thrower.png");
    private static final ResourceLocation WORKSHOP_BUILD_ZEPPELIN = new ResourceLocation(Reference.MOD_ID + ":textures/gui/workshop_build_zeppelin.png");
    
	public GUIWorkshop(InventoryPlayer playerInv, TileEntityWorkshop TE) {
		super(new ContainerWorkshop(playerInv, TE));
		
		this.workshop = TE;
		this.xSize = 176;
        this.ySize = 232;
	}
	
	@Override
	public void initGui()
	{
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.ButtonCraft = new GuiButton(0, i+48, j+123, 80, 20, "Craft");
		this.buttonList.add(ButtonCraft);
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		BlockPos pos = workshop.getPos().subtract(Minecraft.getMinecraft().player.getPosition());
		System.out.println(button.id);
		System.out.println(pos);
		Bettersurvival.network.sendToServer(new MessageButtonPressed(button.id));
		super.actionPerformed(button);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        GlStateManager.pushMatrix();
        if (ItemBlueprint.getType(workshop.getStackInSlot(0)) != null)
		{
        	switch (ItemBlueprint.getType(workshop.getStackInSlot(0)))
        	{
        	case BALLISTA:
        		this.mc.getTextureManager().bindTexture(WORKSHOP_BUILD_BALLISTA);
	            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
				this.fontRenderer.drawString("Building", i+7, j+7, 4210752);
				this.fontRenderer.drawString("Ballista", i+27, j+30, 4210752);
				break;
        	case CANNON:
        		this.mc.getTextureManager().bindTexture(WORKSHOP_BUILD_CANNON);
	            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
				this.fontRenderer.drawString("Building", i+7, j+7, 4210752);
				this.fontRenderer.drawString("Cannon", i+27, j+30, 4210752);
				break;
        	case POTION_THROWER:
        		this.mc.getTextureManager().bindTexture(WORKSHOP_BUILD_THROWER);
	            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
				this.fontRenderer.drawString("Building", i+7, j+7, 4210752);
				this.fontRenderer.drawString("Potion Thrower", i+27, j+30, 4210752);
				break;
        	case ZEPPELIN:
        		this.mc.getTextureManager().bindTexture(WORKSHOP_BUILD_ZEPPELIN);
	            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
				this.fontRenderer.drawString("Building", i+7, j+7, 4210752);
				this.fontRenderer.drawString("Zeppelin", i+27, j+30, 4210752);
				break;
        	}
		}
		else 
		{
	        this.mc.getTextureManager().bindTexture(WORKSHOP_EMPTY);
	        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
			this.fontRenderer.drawString("Building", i+7, j+7, 4210752);
			this.fontRenderer.drawString("Insert blueprint", i+27, j+30, 4210752);
		}
        GlStateManager.popMatrix();
	}

}
