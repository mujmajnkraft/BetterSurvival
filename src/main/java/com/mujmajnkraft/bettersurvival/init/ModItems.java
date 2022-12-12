package com.mujmajnkraft.bettersurvival.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.integration.InFCompat;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.items.ItemBattleAxe;
import com.mujmajnkraft.bettersurvival.items.ItemCrossbow;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemDagger;
import com.mujmajnkraft.bettersurvival.items.ItemHammer;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import com.mujmajnkraft.bettersurvival.items.ItemSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModItems {
	
	public static ToolMaterial COPPER = EnumHelper.addToolMaterial("Copper", ConfigHandler.copperStats.get(0).intValue(), ConfigHandler.copperStats.get(1).intValue(), ConfigHandler.copperStats.get(2).floatValue(), ConfigHandler.copperStats.get(3).floatValue(), ConfigHandler.copperStats.get(4).intValue());
	public static ToolMaterial BRONZE = EnumHelper.addToolMaterial("Bronze", ConfigHandler.bronzeStats.get(0).intValue(), ConfigHandler.bronzeStats.get(1).intValue(), ConfigHandler.bronzeStats.get(2).floatValue(), ConfigHandler.bronzeStats.get(3).floatValue(), ConfigHandler.bronzeStats.get(4).intValue());
	public static ToolMaterial INVAR = EnumHelper.addToolMaterial("Invar", ConfigHandler.invarStats.get(0).intValue(), ConfigHandler.invarStats.get(1).intValue(), ConfigHandler.invarStats.get(2).floatValue(), ConfigHandler.invarStats.get(3).floatValue(), ConfigHandler.invarStats.get(4).intValue());
	public static ToolMaterial SILVER = EnumHelper.addToolMaterial("Silver", ConfigHandler.silverStats.get(0).intValue(), ConfigHandler.silverStats.get(1).intValue(), ConfigHandler.silverStats.get(2).floatValue(), ConfigHandler.silverStats.get(3).floatValue(), ConfigHandler.silverStats.get(4).intValue());
	public static ToolMaterial ELECTRUM = EnumHelper.addToolMaterial("Electrum", ConfigHandler.electrumStats.get(0).intValue(), ConfigHandler.electrumStats.get(1).intValue(), ConfigHandler.electrumStats.get(2).floatValue(), ConfigHandler.electrumStats.get(3).floatValue(), ConfigHandler.electrumStats.get(4).intValue());
	public static ToolMaterial ALUMINIUM = EnumHelper.addToolMaterial("Aluminium", ConfigHandler.aluminiumStats.get(0).intValue(), ConfigHandler.aluminiumStats.get(1).intValue(), ConfigHandler.aluminiumStats.get(2).floatValue(), ConfigHandler.aluminiumStats.get(3).floatValue(), ConfigHandler.aluminiumStats.get(4).intValue());
	public static ToolMaterial STEEL = EnumHelper.addToolMaterial("Steel", ConfigHandler.steelStats.get(0).intValue(), ConfigHandler.steelStats.get(1).intValue(), ConfigHandler.steelStats.get(2).floatValue(), ConfigHandler.steelStats.get(3).floatValue(), ConfigHandler.steelStats.get(4).intValue());
	public static ToolMaterial SIGNALUM = EnumHelper.addToolMaterial("Signalum", ConfigHandler.signalumStats.get(0).intValue(), ConfigHandler.signalumStats.get(1).intValue(), ConfigHandler.signalumStats.get(2).floatValue(), ConfigHandler.signalumStats.get(3).floatValue(), ConfigHandler.signalumStats.get(4).intValue());
	public static ToolMaterial LUMIUM = EnumHelper.addToolMaterial("Lumium", ConfigHandler.lumiumStats.get(0).intValue(), ConfigHandler.lumiumStats.get(1).intValue(), ConfigHandler.lumiumStats.get(2).floatValue(), ConfigHandler.lumiumStats.get(3).floatValue(), ConfigHandler.lumiumStats.get(4).intValue());
	public static ToolMaterial ENDERIUM = EnumHelper.addToolMaterial("Enderium", ConfigHandler.enderiumStats.get(0).intValue(), ConfigHandler.enderiumStats.get(1).intValue(), ConfigHandler.enderiumStats.get(2).floatValue(), ConfigHandler.enderiumStats.get(3).floatValue(), ConfigHandler.enderiumStats.get(4).intValue());
	
	public static final Item crossbow = new ItemCrossbow();
	public static final Item smallshield = new ItemCustomShield(0.5F, 1);
	public static final Item bigshield = new ItemCustomShield(0.8F, 3);
	
	private static List<ToolMaterial> materials = new ArrayList<ToolMaterial>(EnumSet.of(COPPER, BRONZE, INVAR, SILVER, ELECTRUM, ALUMINIUM, STEEL, SIGNALUM, LUMIUM, ENDERIUM,
									ToolMaterial.WOOD, ToolMaterial.STONE, ToolMaterial.IRON, ToolMaterial.GOLD, ToolMaterial.DIAMOND));

	private static List<Item> items = new ArrayList<Item>();
		
	public static void setRepairMaterials() {
		if(BetterSurvival.isIafLoaded) {
			if(Item.getByNameOrId("iceandfire:myrmex_desert_chitin") != null) {
				InFCompat.DESERT_CHITIN.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_desert_chitin")));
			}
			if(Item.getByNameOrId("iceandfire:myrmex_jungle_chitin") != null) {
				InFCompat.JUNGLE_CHITIN.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_jungle_chitin")));
			}
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		if(BetterSurvival.isIafLoaded) {
			materials.remove(ModItems.SILVER);
			materials.addAll(Arrays.asList(InFCompat.SILVER, InFCompat.DRAGON_BONE, InFCompat.DRAGON_BONE_FLAMED, InFCompat.DRAGON_BONE_ICED, InFCompat.JUNGLE_CHITIN, InFCompat.DESERT_CHITIN));
		}

		crossbow.setRegistryName(Reference.MOD_ID, "itemcrossbow");
		crossbow.setTranslationKey("crossbow");
		items.add(crossbow);
		smallshield.setRegistryName(Reference.MOD_ID, "itemsmallshield");
		smallshield.setTranslationKey("smallshield");
		items.add(smallshield);
		bigshield.setRegistryName(Reference.MOD_ID, "itembigshield");
		bigshield.setTranslationKey("bigshield");
		items.add(bigshield);

		for(ToolMaterial mat : materials) {
			items.add(new ItemHammer(mat));
			items.add(new ItemSpear(mat));
			items.add(new ItemDagger(mat));
			items.add(new ItemBattleAxe(mat));
			items.add(new ItemNunchaku(mat));
		}

		event.getRegistry().registerAll(items.toArray(new Item[0]));
	}
	
	public static void registerRenders() {
		for(Item item : items) registerRender(item);
	}
	
	private static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}