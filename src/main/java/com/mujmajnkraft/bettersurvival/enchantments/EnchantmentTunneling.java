package com.mujmajnkraft.bettersurvival.enchantments;

import java.util.Set;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchantmentTunneling extends Enchantment {

	public EnchantmentTunneling() {
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setRegistryName("tunneling");
		this.setName(Reference.MOD_ID + ".tunneling");
	}
	
	//Called during BreakEvent
	public static void mineManyBlocks(EntityPlayer miner, IBlockState state, BlockPos pos)
	{
		System.out.println("Calling the tunneling enchantment");
		World world = miner.getEntityWorld();
		Set<String> t = miner.getTags();
		int dir = 0;
		if (t.contains("west")) {dir = 1; t.remove("west");}
		else if (t.contains("east")) {dir = 2; t.remove("east");}
		else if (t.contains("down")) {dir = 3; t.remove("down");}
		else if (t.contains("up")) {dir = 4; t.remove("up");}
		else if (t.contains("south")) {dir = 5; t.remove("south");}
		else if (t.contains("north")) {dir = 6; t.remove("north");}
		if (dir != 0 && canMineEffectively(miner, state))
		{
			int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.tunneling, miner);
			for(int x = (int) -l; x < l + 1; x++)
			{
				if((dir !=1 && dir !=2) || x==0)
				{
					for(int y = (int) -l; y < l + 1; y++)
					{
						if((dir != 3 && dir != 4) || y==0)
						{
							for(int z = (int) -l; z < l + 1; z++)
							{
								if ((dir != 5 && dir != 6) || z==0)
								{
									if (Math.sqrt(x*x+y*y+z*z)<=(l+1.0F)/2.0F && !(x==0 && y==0 && z==0))
									{
										BlockPos pos1 = pos.add(x, y, z);
										if (canMineEffectively(miner, world.getBlockState(pos1)))
										{
											((EntityPlayerMP)miner).interactionManager.tryHarvestBlock(pos1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	static boolean canMineEffectively(EntityPlayer player, IBlockState state)
	{
		ItemStack stack = player.getHeldItemMainhand();
		Block block = state.getBlock();
		for (String type:stack.getItem().getToolClasses(player.getHeldItemMainhand()))
		{
			if (block.isToolEffective(type, state) && block.getHarvestLevel(state) <= stack.getItem().getHarvestLevel(player.getHeldItemMainhand(), type, player, state))
			{
				return true;
			}
		}
		return false;
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 25 + (enchantmentLevel - 1) * 15;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return ConfigHandler.tunnelinglevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.tunneling;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.tunnelinglevel != 0;
    }
}
