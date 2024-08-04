package com.mujmajnkraft.bettersurvival.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.integration.InFCompat;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.integration.InFLightningForkCompat;
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
	
	public static ToolMaterial COPPER = EnumHelper.addToolMaterial("Copper", ForgeConfigHandler.materials.copperStats[0].intValue(), ForgeConfigHandler.materials.copperStats[1].intValue(), ForgeConfigHandler.materials.copperStats[2].floatValue(), ForgeConfigHandler.materials.copperStats[3].floatValue(), ForgeConfigHandler.materials.copperStats[4].intValue());
	public static ToolMaterial BRONZE = EnumHelper.addToolMaterial("Bronze", ForgeConfigHandler.materials.bronzeStats[0].intValue(), ForgeConfigHandler.materials.bronzeStats[1].intValue(), ForgeConfigHandler.materials.bronzeStats[2].floatValue(), ForgeConfigHandler.materials.bronzeStats[3].floatValue(), ForgeConfigHandler.materials.bronzeStats[4].intValue());
	public static ToolMaterial INVAR = EnumHelper.addToolMaterial("Invar", ForgeConfigHandler.materials.invarStats[0].intValue(), ForgeConfigHandler.materials.invarStats[1].intValue(), ForgeConfigHandler.materials.invarStats[2].floatValue(), ForgeConfigHandler.materials.invarStats[3].floatValue(), ForgeConfigHandler.materials.invarStats[4].intValue());
	public static ToolMaterial SILVER = EnumHelper.addToolMaterial("Silver", ForgeConfigHandler.materials.silverStats[0].intValue(), ForgeConfigHandler.materials.silverStats[1].intValue(), ForgeConfigHandler.materials.silverStats[2].floatValue(), ForgeConfigHandler.materials.silverStats[3].floatValue(), ForgeConfigHandler.materials.silverStats[4].intValue());
	public static ToolMaterial ELECTRUM = EnumHelper.addToolMaterial("Electrum", ForgeConfigHandler.materials.electrumStats[0].intValue(), ForgeConfigHandler.materials.electrumStats[1].intValue(), ForgeConfigHandler.materials.electrumStats[2].floatValue(), ForgeConfigHandler.materials.electrumStats[3].floatValue(), ForgeConfigHandler.materials.electrumStats[4].intValue());
	public static ToolMaterial ALUMINIUM = EnumHelper.addToolMaterial("Aluminium", ForgeConfigHandler.materials.aluminiumStats[0].intValue(), ForgeConfigHandler.materials.aluminiumStats[1].intValue(), ForgeConfigHandler.materials.aluminiumStats[2].floatValue(), ForgeConfigHandler.materials.aluminiumStats[3].floatValue(), ForgeConfigHandler.materials.aluminiumStats[4].intValue());
	public static ToolMaterial STEEL = EnumHelper.addToolMaterial("Steel", ForgeConfigHandler.materials.steelStats[0].intValue(), ForgeConfigHandler.materials.steelStats[1].intValue(), ForgeConfigHandler.materials.steelStats[2].floatValue(), ForgeConfigHandler.materials.steelStats[3].floatValue(), ForgeConfigHandler.materials.steelStats[4].intValue());
	public static ToolMaterial SIGNALUM = EnumHelper.addToolMaterial("Signalum", ForgeConfigHandler.materials.signalumStats[0].intValue(), ForgeConfigHandler.materials.signalumStats[1].intValue(), ForgeConfigHandler.materials.signalumStats[2].floatValue(), ForgeConfigHandler.materials.signalumStats[3].floatValue(), ForgeConfigHandler.materials.signalumStats[4].intValue());
	public static ToolMaterial LUMIUM = EnumHelper.addToolMaterial("Lumium", ForgeConfigHandler.materials.lumiumStats[0].intValue(), ForgeConfigHandler.materials.lumiumStats[1].intValue(), ForgeConfigHandler.materials.lumiumStats[2].floatValue(), ForgeConfigHandler.materials.lumiumStats[3].floatValue(), ForgeConfigHandler.materials.lumiumStats[4].intValue());
	public static ToolMaterial ENDERIUM = EnumHelper.addToolMaterial("Enderium", ForgeConfigHandler.materials.enderiumStats[0].intValue(), ForgeConfigHandler.materials.enderiumStats[1].intValue(), ForgeConfigHandler.materials.enderiumStats[2].floatValue(), ForgeConfigHandler.materials.enderiumStats[3].floatValue(), ForgeConfigHandler.materials.enderiumStats[4].intValue());
	
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
				InFCompat.DESERT_STINGER.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_desert_chitin")));
			}
			if(Item.getByNameOrId("iceandfire:myrmex_jungle_chitin") != null) {
				InFCompat.JUNGLE_CHITIN.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_jungle_chitin")));
				InFCompat.JUNGLE_STINGER.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_jungle_chitin")));
			}
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		if(BetterSurvival.isIafLoaded) {
			materials.remove(ModItems.SILVER);
			materials.addAll(Arrays.asList(InFCompat.SILVER, InFCompat.DRAGON_BONE, InFCompat.DRAGON_BONE_FLAMED, InFCompat.DRAGON_BONE_ICED, InFCompat.JUNGLE_CHITIN, InFCompat.JUNGLE_STINGER, InFCompat.DESERT_CHITIN, InFCompat.DESERT_STINGER));
			if(BetterSurvival.isIafLightningForkLoaded) {
				materials.remove(ModItems.COPPER);
				materials.add(InFLightningForkCompat.COPPER);
				materials.add(InFLightningForkCompat.DRAGON_BONE_LIGHTNING);
			}
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