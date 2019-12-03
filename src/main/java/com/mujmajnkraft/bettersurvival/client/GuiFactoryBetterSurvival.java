package com.mujmajnkraft.bettersurvival.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;

public class GuiFactoryBetterSurvival implements IModGuiFactory{

	@Override
	public void initialize(Minecraft minecraftInstance) {}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new GuiConfigBetterSurvival(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}
	
	public static class GuiConfigBetterSurvival extends GuiConfig
	{
		public GuiConfigBetterSurvival(GuiScreen parentScreen)
        {
            super(parentScreen, getConfigElements(), Reference.MOD_ID, false, false, I18n.format(Reference.MOD_ID + ".configgui.title"));
        }
        
        private static List<IConfigElement> getConfigElements()
        {
        	 List<IConfigElement> list = new ArrayList<IConfigElement>();
        	 list.add(new DummyCategoryElement(ConfigHandler.MAX_LEVEL, Reference.MOD_ID + ".configgui." + ConfigHandler.MAX_LEVEL, MaxLevel.class));
        	 list.add(new DummyCategoryElement(ConfigHandler.IS_TREASURE, Reference.MOD_ID + ".configgui." + ConfigHandler.IS_TREASURE, IsTreasure.class));
        	 list.add(new DummyCategoryElement(ConfigHandler.MATERIAL_STATS, Reference.MOD_ID + ".configgui." + ConfigHandler.MATERIAL_STATS, MaterialStats.class));
        	 list.add(new DummyCategoryElement(ConfigHandler.OTHER, Reference.MOD_ID + ".configgui." + ConfigHandler.OTHER, Other.class));
        	 return list;
        }
        
        public static class MaxLevel extends CategoryEntry
        {
			public MaxLevel(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}
			
			@Override
            protected GuiScreen buildChildScreen()
            {
            	return new GuiConfig(this.owningScreen,
                        (new ConfigElement(ConfigHandler.getConfig().getCategory(ConfigHandler.MAX_LEVEL))).getChildElements(),
                        this.owningScreen.modID, ConfigHandler.MAX_LEVEL, true, false,
                        GuiConfig.getAbridgedConfigPath(ConfigHandler.getConfig().toString()));
            }
        }
        
        public static class IsTreasure extends CategoryEntry
        {
			public IsTreasure(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}
			
			@Override
            protected GuiScreen buildChildScreen()
            {
            	return new GuiConfig(this.owningScreen,
                        (new ConfigElement(ConfigHandler.getConfig().getCategory(ConfigHandler.IS_TREASURE))).getChildElements(),
                        this.owningScreen.modID, ConfigHandler.IS_TREASURE, false, false,
                        GuiConfig.getAbridgedConfigPath(ConfigHandler.getConfig().toString()));
            }
        }
        
        public static class MaterialStats extends CategoryEntry
        {
			public MaterialStats(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}
			
			@Override
            protected GuiScreen buildChildScreen()
            {
            	return new GuiConfig(this.owningScreen,
                        (new ConfigElement(ConfigHandler.getConfig().getCategory(ConfigHandler.MATERIAL_STATS))).getChildElements(),
                        this.owningScreen.modID, ConfigHandler.MATERIAL_STATS, true, true,
                        GuiConfig.getAbridgedConfigPath(ConfigHandler.getConfig().toString()));
            }
        }
        
        public static class Other extends CategoryEntry
        {
			public Other(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}
			
			@Override
            protected GuiScreen buildChildScreen()
            {
            	return new GuiConfig(this.owningScreen,
                        (new ConfigElement(ConfigHandler.getConfig().getCategory(ConfigHandler.OTHER))).getChildElements(),
                        this.owningScreen.modID, ConfigHandler.OTHER, false, false,
                        GuiConfig.getAbridgedConfigPath(ConfigHandler.getConfig().toString()));
            }
        }
	}

}
