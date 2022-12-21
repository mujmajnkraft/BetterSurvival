package com.mujmajnkraft.bettersurvival.enchantments;

import java.util.List;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public class EnchantmentDiamonds extends Enchantment {
	
	public EnchantmentDiamonds() {
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("diamonds");
		this.setName(Reference.MOD_ID + ".diamonds");
	}
	

	private static ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "gameplay/diamonds_everywhere");
	
	//Called during HarvestDropsEvent if an ore is mined by an enchanted tool
	public static void conjureDiamonds(List<ItemStack> drops, Block blockMined, EntityPlayer player)
	{
		Item blockitem = Item.getItemFromBlock(blockMined);
		if((blockMined instanceof BlockOre || blockMined instanceof BlockRedstoneOre)) {
			for(ItemStack drop : drops) {
				if(drop.getItem() == blockitem) {
					return;// Block which drops itself can't also drop diamonds to prevent diamond farming
				}
			}
			// Checking if the effect happens and generating extra loot is handled by a loot table
			LootTable loottable = player.world.getLootTableManager().getLootTableFromLocation(location);
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.diamonds, player.getHeldItemMainhand());
			LootContext.Builder context = (new LootContext.Builder((WorldServer)player.world)).withLuck(l);
			drops.addAll(loottable.generateLootForPools(player.world.rand, context.build()));
		}
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 15 + (enchantmentLevel - 1) * 9;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ForgeConfigHandler.enchantments.diamondsEverywhereLevel;
    }

    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.SILK_TOUCH && ench != Enchantments.FORTUNE;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
    	return stack.getItem() instanceof ItemPickaxe;
	}
    
    public boolean isTreasureEnchantment()
    {
    	return ForgeConfigHandler.enchantments.diamondsEverywhereTreasure;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ForgeConfigHandler.enchantments.diamondsEverywhereLevel != 0;
    }
}
