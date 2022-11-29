package com.mujmajnkraft.bettersurvival;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;

public class RecipeCondition implements IConditionFactory{

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String modID = JsonUtils.getString(json, "mod_id");
		boolean isModLoaded = Loader.isModLoaded(modID) && ForgeConfigHandler.server.integration;
		return () -> isModLoaded;
	}
}