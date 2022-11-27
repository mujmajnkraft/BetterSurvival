package com.mujmajnkraft.bettersurvival.init;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
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
	public static ToolMaterial DRAGON_BONE = EnumHelper.addToolMaterial("Dragonbone", 4, 1660, 10.0F, 4.0F, 22);
	public static ToolMaterial JUNGLE_CHITIN = EnumHelper.addToolMaterial("JungleChitin", 3, 600, 6.0F, -1.0F, 8);
	public static ToolMaterial DESERT_CHITIN = EnumHelper.addToolMaterial("DesertChitin", 3, 600, 6.0F, -1.0F, 8);
	
	public static final Item woodenhammer = new ItemHammer(ToolMaterial.WOOD);
	public static final Item goldenhammer = new ItemHammer(ToolMaterial.GOLD);
	public static final Item ironhammer = new ItemHammer(ToolMaterial.IRON);
	public static final Item stonehammer = new ItemHammer(ToolMaterial.STONE);
	public static final Item diamondhammer = new ItemHammer(ToolMaterial.DIAMOND);

	public static final Item copperhammer = new ItemHammer(COPPER);
	public static final Item bronzehammer = new ItemHammer(BRONZE);
	public static final Item invarhammer = new ItemHammer(INVAR);
	public static final Item silverhammer = new ItemHammer(SILVER);
	public static final Item electrumhammer = new ItemHammer(ELECTRUM);
	public static final Item aluminiumhammer = new ItemHammer(ALUMINIUM);
	public static final Item steelhammer = new ItemHammer(STEEL);
	public static final Item signalumhammer = new ItemHammer(SIGNALUM);
	public static final Item lumiumhammer = new ItemHammer(LUMIUM);
	public static final Item enderiumhammer = new ItemHammer(ENDERIUM);
	public static final Item dragonbonehammer = new ItemHammer(DRAGON_BONE);
	public static final Item junglechitinhammer = new ItemHammer(JUNGLE_CHITIN);
	public static final Item desertchitinhammer = new ItemHammer(DESERT_CHITIN);
	
	public static final Item woodenspear = new ItemSpear(ToolMaterial.WOOD);
	public static final Item goldenspear = new ItemSpear(ToolMaterial.GOLD);
	public static final Item ironspear = new ItemSpear(ToolMaterial.IRON);
	public static final Item stonespear = new ItemSpear(ToolMaterial.STONE);
	public static final Item diamondspear = new ItemSpear(ToolMaterial.DIAMOND);

	public static final Item copperspear = new ItemSpear(COPPER);
	public static final Item bronzespear = new ItemSpear(BRONZE);
	public static final Item invarspear = new ItemSpear(INVAR);
	public static final Item silverspear = new ItemSpear(SILVER);
	public static final Item electrumspear = new ItemSpear(ELECTRUM);
	public static final Item aluminiumspear = new ItemSpear(ALUMINIUM);
	public static final Item steelspear = new ItemSpear(STEEL);
	public static final Item signalumspear = new ItemSpear(SIGNALUM);
	public static final Item lumiumspear = new ItemSpear(LUMIUM);
	public static final Item enderiumspear = new ItemSpear(ENDERIUM);
	public static final Item dragonbonespear = new ItemSpear(DRAGON_BONE);
	public static final Item junglechitinspear = new ItemSpear(JUNGLE_CHITIN);
	public static final Item desertchitinspear = new ItemSpear(DESERT_CHITIN);
	
	public static final Item woodendagger = new ItemDagger(ToolMaterial.WOOD);
	public static final Item goldendagger = new ItemDagger(ToolMaterial.GOLD);
	public static final Item irondagger = new ItemDagger(ToolMaterial.IRON);
	public static final Item stonedagger = new ItemDagger(ToolMaterial.STONE);
	public static final Item diamonddagger = new ItemDagger(ToolMaterial.DIAMOND);

	public static final Item copperdagger = new ItemDagger(COPPER);
	public static final Item bronzedagger = new ItemDagger(BRONZE);
	public static final Item invardagger = new ItemDagger(INVAR);
	public static final Item silverdagger = new ItemDagger(SILVER);
	public static final Item electrumdagger = new ItemDagger(ELECTRUM);
	public static final Item aluminiumdagger = new ItemDagger(ALUMINIUM);
	public static final Item steeldagger = new ItemDagger(STEEL);
	public static final Item signalumdagger = new ItemDagger(SIGNALUM);
	public static final Item lumiumdagger = new ItemDagger(LUMIUM);
	public static final Item enderiumdagger = new ItemDagger(ENDERIUM);
	public static final Item dragonbonedagger = new ItemDagger(DRAGON_BONE);
	public static final Item junglechitindagger = new ItemDagger(JUNGLE_CHITIN);
	public static final Item desertchitindagger = new ItemDagger(DESERT_CHITIN);
	
	public static final Item woodenbattleaxe = new ItemBattleAxe(ToolMaterial.WOOD);
	public static final Item goldenbattleaxe = new ItemBattleAxe(ToolMaterial.GOLD);
	public static final Item ironbattleaxe = new ItemBattleAxe(ToolMaterial.IRON);
	public static final Item stonebattleaxe = new ItemBattleAxe(ToolMaterial.STONE);
	public static final Item diamondbattleaxe = new ItemBattleAxe(ToolMaterial.DIAMOND);

	public static final Item copperbattleaxe = new ItemBattleAxe(COPPER);
	public static final Item bronzebattleaxe = new ItemBattleAxe(BRONZE);
	public static final Item invarbattleaxe = new ItemBattleAxe(INVAR);
	public static final Item silverbattleaxe = new ItemBattleAxe(SILVER);
	public static final Item electrumbattleaxe = new ItemBattleAxe(ELECTRUM);
	public static final Item aluminiumbattleaxe = new ItemBattleAxe(ALUMINIUM);
	public static final Item steelbattleaxe = new ItemBattleAxe(STEEL);
	public static final Item signalumbattleaxe = new ItemBattleAxe(SIGNALUM);
	public static final Item lumiumbattleaxe = new ItemBattleAxe(LUMIUM);
	public static final Item enderiumbattleaxe = new ItemBattleAxe(ENDERIUM);
	public static final Item dragonbonebattleaxe = new ItemBattleAxe(DRAGON_BONE);
	public static final Item junglechitinbattleaxe = new ItemBattleAxe(JUNGLE_CHITIN);
	public static final Item desertchitinbattleaxe = new ItemBattleAxe(DESERT_CHITIN);
	
	public static final Item woodennunchaku = new ItemNunchaku(ToolMaterial.WOOD);
	public static final Item goldennunchaku = new ItemNunchaku(ToolMaterial.GOLD);
	public static final Item ironnunchaku = new ItemNunchaku(ToolMaterial.IRON);
	public static final Item stonenunchaku = new ItemNunchaku(ToolMaterial.STONE);
	public static final Item diamondnunchaku = new ItemNunchaku(ToolMaterial.DIAMOND);

	public static final Item coppernunchaku = new ItemNunchaku(COPPER);
	public static final Item bronzenunchaku = new ItemNunchaku(BRONZE);
	public static final Item invarnunchaku = new ItemNunchaku(INVAR);
	public static final Item silvernunchaku = new ItemNunchaku(SILVER);
	public static final Item electrumnunchaku = new ItemNunchaku(ELECTRUM);
	public static final Item aluminiumnunchaku = new ItemNunchaku(ALUMINIUM);
	public static final Item steelnunchaku = new ItemNunchaku(STEEL);
	public static final Item signalumnunchaku = new ItemNunchaku(SIGNALUM);
	public static final Item lumiumnunchaku = new ItemNunchaku(LUMIUM);
	public static final Item enderiumnunchaku = new ItemNunchaku(ENDERIUM);
	public static final Item dragonbonenunchaku = new ItemNunchaku(DRAGON_BONE);
	public static final Item junglechitinnunchaku = new ItemNunchaku(JUNGLE_CHITIN);
	public static final Item desertchitinnunchaku = new ItemNunchaku(DESERT_CHITIN);
	
	public static final Item crossbow = new ItemCrossbow();
	public static final Item smallshield = new ItemCustomShield(0.5F, 1);
	public static final Item bigshield = new ItemCustomShield(0.8F, 3);
	
	public static List<ToolMaterial> materials = new ArrayList<ToolMaterial>(EnumSet.of(COPPER, BRONZE, INVAR, SILVER, ELECTRUM, ALUMINIUM, STEEL, SIGNALUM, LUMIUM, ENDERIUM, DRAGON_BONE, JUNGLE_CHITIN, DESERT_CHITIN,
									ToolMaterial.WOOD, ToolMaterial.STONE, ToolMaterial.IRON, ToolMaterial.GOLD, ToolMaterial.DIAMOND));
		
	public static void setRepairMaterials()
	{
		if (Bettersurvival.isIafLoaded)
		{
			if (Item.getByNameOrId("iceandfire:dragonbone") != null)
			{
				ModItems.DRAGON_BONE.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:dragonbone")));
			}
			if (Item.getByNameOrId("iceandfire:myrmex_desert_chitin") != null)
			{
				ModItems.DESERT_CHITIN.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_desert_chitin")));
			}
			if (Item.getByNameOrId("iceandfire:myrmex_jungle_chitin") != null)
			{
				ModItems.JUNGLE_CHITIN.setRepairItem(new ItemStack(Item.getByNameOrId("iceandfire:myrmex_jungle_chitin")));
			}
		}
	}
	
	@SubscribeEvent
	public void regiserItems(RegistryEvent.Register<Item> event)
	{
		smallshield.setRegistryName("ItemSmallShield");
		
		bigshield.setRegistryName("ItemBigShield");
		
		event.getRegistry().registerAll(
				smallshield,
				crossbow,
				bigshield,
				
				woodenhammer,
				stonehammer,
				ironhammer,
				goldenhammer,
				diamondhammer,
				copperhammer,
				bronzehammer,
				invarhammer,
				silverhammer,
				electrumhammer,
				aluminiumhammer,
				steelhammer,
				signalumhammer,
				lumiumhammer,
				enderiumhammer,
				dragonbonehammer,
				junglechitinhammer,
				desertchitinhammer,
				
				woodenspear,
				stonespear,
				ironspear,
				goldenspear,
				diamondspear,
				copperspear,
				bronzespear,
				invarspear,
				silverspear,
				electrumspear,
				aluminiumspear,
				steelspear,
				signalumspear,
				lumiumspear,
				enderiumspear,
				dragonbonespear,
				junglechitinspear,
				desertchitinspear,
				
				woodenbattleaxe,
				stonebattleaxe,
				ironbattleaxe,
				goldenbattleaxe,
				diamondbattleaxe,
				copperbattleaxe,
				bronzebattleaxe,
				invarbattleaxe,
				silverbattleaxe,
				electrumbattleaxe,
				aluminiumbattleaxe,
				steelbattleaxe,
				signalumbattleaxe,
				lumiumbattleaxe,
				enderiumbattleaxe,
				dragonbonebattleaxe,
				junglechitinbattleaxe,
				desertchitinbattleaxe,
				
				woodendagger,
				stonedagger,
				irondagger,
				goldendagger,
				diamonddagger,
				copperdagger,
				bronzedagger,
				invardagger,
				silverdagger,
				electrumdagger,
				aluminiumdagger,
				steeldagger,
				signalumdagger,
				lumiumdagger,
				enderiumdagger,
				dragonbonedagger,
				junglechitindagger,
				desertchitindagger,
				
				woodennunchaku,
				stonenunchaku,
				ironnunchaku,
				goldennunchaku,
				diamondnunchaku,
				coppernunchaku,
				bronzenunchaku,
				invarnunchaku,
				silvernunchaku,
				electrumnunchaku,
				aluminiumnunchaku,
				steelnunchaku,
				signalumnunchaku,
				lumiumnunchaku,
				enderiumnunchaku,
				dragonbonenunchaku,
				junglechitinnunchaku,
				desertchitinnunchaku);
	}
	
	public static void registerRenders() {
		registerRender(bigshield);
		registerRender(smallshield);
		registerRender(crossbow);
		
		registerRender(woodenhammer);
		registerRender(stonehammer);
		registerRender(ironhammer);
		registerRender(goldenhammer);
		registerRender(diamondhammer);
		registerRender(copperhammer);
		registerRender(bronzehammer);
		registerRender(invarhammer);
		registerRender(silverhammer);
		registerRender(electrumhammer);
		registerRender(aluminiumhammer);
		registerRender(steelhammer);
		registerRender(signalumhammer);
		registerRender(lumiumhammer);
		registerRender(enderiumhammer);
		registerRender(dragonbonehammer);
		registerRender(junglechitinhammer);
		registerRender(desertchitinhammer);
		
		registerRender(woodenspear);
		registerRender(stonespear);
		registerRender(ironspear);
		registerRender(goldenspear);
		registerRender(diamondspear);
		registerRender(copperspear);
		registerRender(bronzespear);
		registerRender(invarspear);
		registerRender(silverspear);
		registerRender(electrumspear);
		registerRender(aluminiumspear);
		registerRender(steelspear);
		registerRender(signalumspear);
		registerRender(lumiumspear);
		registerRender(enderiumspear);
		registerRender(dragonbonespear);
		registerRender(junglechitinspear);
		registerRender(desertchitinspear);
		
		registerRender(woodenbattleaxe);
		registerRender(stonebattleaxe);
		registerRender(ironbattleaxe);
		registerRender(goldenbattleaxe);
		registerRender(diamondbattleaxe);
		registerRender(copperbattleaxe);
		registerRender(bronzebattleaxe);
		registerRender(invarbattleaxe);
		registerRender(silverbattleaxe);
		registerRender(electrumbattleaxe);
		registerRender(aluminiumbattleaxe);
		registerRender(steelbattleaxe);
		registerRender(signalumbattleaxe);
		registerRender(lumiumbattleaxe);
		registerRender(enderiumbattleaxe);
		registerRender(dragonbonebattleaxe);
		registerRender(junglechitinbattleaxe);
		registerRender(desertchitinbattleaxe);
		
		registerRender(woodendagger);
		registerRender(stonedagger);
		registerRender(irondagger);
		registerRender(goldendagger);
		registerRender(diamonddagger);
		registerRender(copperdagger);
		registerRender(bronzedagger);
		registerRender(invardagger);
		registerRender(silverdagger);
		registerRender(electrumdagger);
		registerRender(aluminiumdagger);
		registerRender(steeldagger);
		registerRender(signalumdagger);
		registerRender(lumiumdagger);
		registerRender(enderiumdagger);
		registerRender(dragonbonedagger);
		registerRender(junglechitindagger);
		registerRender(desertchitindagger);
		
		registerRender(woodennunchaku);
		registerRender(stonenunchaku);
		registerRender(ironnunchaku);
		registerRender(goldennunchaku);
		registerRender(diamondnunchaku);
		registerRender(coppernunchaku);
		registerRender(bronzenunchaku);
		registerRender(invarnunchaku);
		registerRender(silvernunchaku);
		registerRender(electrumnunchaku);
		registerRender(aluminiumnunchaku);
		registerRender(steelnunchaku);
		registerRender(signalumnunchaku);
		registerRender(lumiumnunchaku);
		registerRender(enderiumnunchaku);
		registerRender(dragonbonenunchaku);
		registerRender(junglechitinnunchaku);
		registerRender(desertchitinnunchaku);
	}
	
	private static void registerRender(Item item) 
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}