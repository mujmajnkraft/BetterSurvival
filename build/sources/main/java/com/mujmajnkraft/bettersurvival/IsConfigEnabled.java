package com.mujmajnkraft.bettersurvival;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class IsConfigEnabled implements LootCondition{
	
	//This is still WIP. It does not work in this version
	private final String name;
	
	public IsConfigEnabled(String name)
	{
		this.name = name;
	}
	
	public static class Serializer extends LootCondition.Serializer<IsConfigEnabled>
    {
        public Serializer()
        {
            super(new ResourceLocation("is_config_enabled"), IsConfigEnabled.class);
        }

        public void serialize(JsonObject json, IsConfigEnabled value, JsonSerializationContext context)
        {
            json.addProperty("name", String.valueOf(value.name));
        }

        public IsConfigEnabled deserialize(JsonObject json, JsonDeserializationContext context)
        {
            return new IsConfigEnabled(JsonUtils.getString(json, "name"));
        }
    }

	@Override
	public boolean testCondition(Random rand, LootContext context) {
		
		System.out.println(this.name);
		return ConfigHandler.blast;
	}

}
