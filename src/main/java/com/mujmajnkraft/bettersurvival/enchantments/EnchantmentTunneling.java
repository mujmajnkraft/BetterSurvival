package com.mujmajnkraft.bettersurvival.enchantments;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
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
		World world = miner.getEntityWorld();
		ItemStack stack = miner.getHeldItemMainhand();
		int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.tunneling, miner.getHeldItemMainhand());

		if(l > 0 && canMineEffectively(miner, state, pos)) {
			EnumFacing facing = EnumFacing.getDirectionFromEntityLiving(pos, miner).getOpposite();
			int dir;
			switch(facing.getName()) {
				case "west"	: dir = 1; break;
				case "east"	: dir = 2; break;
				case "down"	: dir = 3; break;
				case "up"	: dir = 4; break;
				case "south": dir = 5; break;
				case "north": dir = 6; break;
				default     : dir = 0; break;
			}
			if(dir != 0) {
				//if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				//stack.getTagCompound().setBoolean("tunnelCooldown", true);

				for(int x = (int) -l; x < l + 1; x++) {
					if((dir !=1 && dir !=2) || x==0) {
						for(int y = (int) -l; y < l + 1; y++) {
							if((dir != 3 && dir != 4) || y==0) {
								for(int z = (int) -l; z < l + 1; z++) {
									if((dir != 5 && dir != 6) || z==0) {
										if(Math.sqrt(x*x+y*y+z*z)<=(l+1.0F)/2.0F && !(x==0 && y==0 && z==0)) {
											BlockPos pos1 = pos.add(x, y, z);
											if(canMineEffectively(miner, world.getBlockState(pos1), pos1)) {
												((EntityPlayerMP)miner).interactionManager.tryHarvestBlock(pos1);
											}
										}
									}
								}
							}
						}
					}
				}
				//stack.getTagCompound().setBoolean("tunnelCooldown", false);
			}
		}
	}
	
	static boolean canMineEffectively(EntityPlayer player, IBlockState state, BlockPos pos)
	{
		ItemStack stack = player.getHeldItemMainhand();
		Block block = state.getBlock();

		if(block == Blocks.AIR) return false;
		if(ForgeConfigHandler.enchantments.preventTunnelingTileEntities && player.world.getTileEntity(pos) != null) return false;

		for(String type : stack.getItem().getToolClasses(player.getHeldItemMainhand())) {
			if(block.isToolEffective(type, state) && block.getHarvestLevel(state) <= stack.getItem().getHarvestLevel(player.getHeldItemMainhand(), type, player, state)) {
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
        return ForgeConfigHandler.enchantments.tunnelingLevel;
    }
    
    public boolean isTreasureEnchantment()
    {
    	return ForgeConfigHandler.enchantments.tunnelingTreasure;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ForgeConfigHandler.enchantments.tunnelingLevel != 0;
    }
}
