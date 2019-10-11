package com.mujmajnkraft.bettersurvival.config;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class GuiConfigBettersurvival extends net.minecraftforge.fml.client.config.GuiConfig
{
	//This is still WIP. It does not work in this version.
	
	GuiSlider agility;
	
	public GuiConfigBettersurvival(GuiScreen parentScreen) {
		super(parentScreen, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, false, false, "This is the best mod ever");
		titleLine2 = Bettersurvival.config.getAbsolutePath();
	}
	
	@Override
    public void initGui()
    {
        super.initGui();
    }

    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        // You can do things like create animations, draw additional elements, etc. here
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        // You can process any additional buttons you may have added here
        super.actionPerformed(button);
    }

}
