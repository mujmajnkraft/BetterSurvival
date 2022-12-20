package com.mujmajnkraft.bettersurvival;

import java.util.Map;

import org.spongepowered.asm.launch.MixinBootstrap;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.Mixins;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
public class BetterSurvivalPlugin implements IFMLLoadingPlugin
{
    public BetterSurvivalPlugin() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.bettersurvival.core.json");
    }
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}