package com.mujmajnkraft.bettersurvival.config;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class GuiFactoryBettersurvival implements IModGuiFactory 
{
	//This is still WIP. It does not work in this version.
	
    @Override
    public void initialize(Minecraft minecraftInstance) 
    {
 
    }
 
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() 
    {
        return null;
    }

	@Override
	public boolean hasConfigGui() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		// TODO Auto-generated method stub
		return null;
	}
}