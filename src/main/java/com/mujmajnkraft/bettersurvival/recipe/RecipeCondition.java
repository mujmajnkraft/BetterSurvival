package com.mujmajnkraft.bettersurvival.recipe;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;

public class RecipeCondition implements IConditionFactory{

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String modID = JsonUtils.getString(json, "mod_id");
		if (modID.matches("iceandfire")) return () -> BetterSurvival.isIafLoaded; //Added an extra check to prevent errors with an unsupported IaF version
		boolean isModLoaded = Loader.isModLoaded(modID) && ForgeConfigHandler.materials.moddedMaterials;
		return () -> isModLoaded;
	}
}