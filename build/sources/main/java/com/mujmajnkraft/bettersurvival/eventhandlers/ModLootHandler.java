package com.mujmajnkraft.bettersurvival.eventhandlers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModLootHandler {
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LootTableLoadEvent event)
	{
		if (event.getName().toString().equals("minecraft:chests/end_city_treasure"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/end_city_treasure_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/stronghold_corridor"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/stronghold_corridor_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/village_blacksmith"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/village_blacksmith_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/nether_bridge"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/nether_bridge_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
		else if (event.getName().toString().equals("minecraft:chests/spawn_bonus_chest"))
		{
			LootCondition chance = new RandomChance(1.00f);
			LootEntry entry = new LootEntryTable(new ResourceLocation("mujmajnkraftsbettersurvival:chests/spawn_bonus_inject"), 1, 0, null, "Custom Weapons");
			LootPool pool = new LootPool(new LootEntry[] {entry},  new LootCondition[] {chance}, new RandomValueRange(1), new RandomValueRange(0), "Custom Weapons");
			event.getTable().addPool(pool);
		}
	}
}
