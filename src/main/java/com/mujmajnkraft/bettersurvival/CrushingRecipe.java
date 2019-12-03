package com.mujmajnkraft.bettersurvival;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CrushingRecipe {
	
	private static final CrushingRecipe CRUSHING_BASE = new CrushingRecipe();
	private final Map<IBlockState, IBlockState> crushingList = Maps.<IBlockState, IBlockState>newHashMap();
	
	public static CrushingRecipe instance()
	{
		return CRUSHING_BASE;
	}
	
	public static void Crush (EntityPlayer playerIn, BlockPos pos, boolean particles)
	{
		World worldIn = playerIn.world;
		if (worldIn instanceof WorldServer && particles) 
		{
			WorldServer worldserver = (WorldServer)worldIn;
            worldserver.spawnParticle(EnumParticleTypes.CRIT, pos.getX() + 0.5f, pos.getY()+1.0D, pos.getZ() + 0.5f, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
		}
		IBlockState result = CrushingRecipe.instance().getCrushingResult(worldIn.getBlockState(pos));
    	if (result != null)
    	{
    		if (worldIn.getBlockState(pos).getBlock() == Blocks.MONSTER_EGG && !worldIn.isRemote)
    		{
    			EntitySilverfish entitysilverfish = new EntitySilverfish(worldIn);
                entitysilverfish.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + 1, (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
                worldIn.spawnEntity(entitysilverfish);
                entitysilverfish.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), (float) playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
    		}
    		worldIn.setBlockState(pos, result);
    	}
	}
	
	public void addCrushingRecipe(IBlockState target, IBlockState result)
	{
		if (target != null && result != null)
		{
			if (getCrushingResult(target) == null)
			{
				crushingList.put(target, result);
			}
		}
	}
	
	public IBlockState getCrushingResult(IBlockState target)
	{
		for (Entry<IBlockState, IBlockState> entry : this.crushingList.entrySet())
        {
            if (target == entry.getKey())
            {
                return (IBlockState)entry.getValue();
            }
        }
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private CrushingRecipe()
	{
		addCrushingRecipe(Blocks.STONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState());
		addCrushingRecipe(Blocks.SANDSTONE.getDefaultState(), Blocks.SAND.getDefaultState());
		addCrushingRecipe(Blocks.STONEBRICK.getDefaultState(), Blocks.STONEBRICK.getStateFromMeta(2));
		addCrushingRecipe(Blocks.STONEBRICK.getStateFromMeta(1), Blocks.STONEBRICK.getStateFromMeta(2));
		addCrushingRecipe(Blocks.STONEBRICK.getStateFromMeta(3), Blocks.STONEBRICK.getStateFromMeta(2));
		addCrushingRecipe(Blocks.STONEBRICK.getStateFromMeta(2), Blocks.COBBLESTONE.getDefaultState());
		addCrushingRecipe(Blocks.MOSSY_COBBLESTONE.getDefaultState(), Blocks.GRAVEL.getDefaultState());
		addCrushingRecipe(Blocks.COBBLESTONE.getDefaultState(), Blocks.GRAVEL.getDefaultState());
		addCrushingRecipe(Blocks.MONSTER_EGG.getStateFromMeta(1), Blocks.COBBLESTONE.getDefaultState());
		addCrushingRecipe(Blocks.MONSTER_EGG.getStateFromMeta(2), Blocks.STONEBRICK.getStateFromMeta(2));
		addCrushingRecipe(Blocks.MONSTER_EGG.getStateFromMeta(3), Blocks.STONEBRICK.getStateFromMeta(2));
		addCrushingRecipe(Blocks.MONSTER_EGG.getStateFromMeta(4), Blocks.COBBLESTONE.getDefaultState());
		addCrushingRecipe(Blocks.MONSTER_EGG.getStateFromMeta(5), Blocks.STONEBRICK.getStateFromMeta(2));
		addCrushingRecipe(Blocks.GRASS.getDefaultState(), Blocks.DIRT.getDefaultState());
		addCrushingRecipe(Blocks.STONE.getStateFromMeta(2), Blocks.STONE.getStateFromMeta(1));
		addCrushingRecipe(Blocks.STONE.getStateFromMeta(4), Blocks.STONE.getStateFromMeta(3));
		addCrushingRecipe(Blocks.STONE.getStateFromMeta(6), Blocks.STONE.getStateFromMeta(5));
		addCrushingRecipe(Blocks.RED_SANDSTONE.getDefaultState(), Blocks.SAND.getStateFromMeta(1));
		addCrushingRecipe(Blocks.RED_SANDSTONE.getStateFromMeta(1), Blocks.RED_SANDSTONE.getDefaultState());
		addCrushingRecipe(Blocks.RED_SANDSTONE.getStateFromMeta(2), Blocks.RED_SANDSTONE.getDefaultState());
		addCrushingRecipe(Blocks.SANDSTONE.getStateFromMeta(1), Blocks.SANDSTONE.getDefaultState());
		addCrushingRecipe(Blocks.SANDSTONE.getStateFromMeta(2), Blocks.SANDSTONE.getDefaultState());
		addCrushingRecipe(Blocks.PRISMARINE.getStateFromMeta(1), Blocks.PRISMARINE.getDefaultState());
		//addCrushingRecipe(Blocks.GLASS.getDefaultState(), Blocks.AIR.getDefaultState());
		//addCrushingRecipe(Blocks.GLASS_PANE.getDefaultState(), Blocks.AIR.getDefaultState());
		for (int i = 0; i < 16; i++)
		{
			//addCrushingRecipe(Blocks.STAINED_GLASS.getStateFromMeta(i), Blocks.AIR.getDefaultState());
			//addCrushingRecipe(Blocks.STAINED_GLASS_PANE.getStateFromMeta(i), Blocks.AIR.getDefaultState());
			addCrushingRecipe(Blocks.WOOL.getStateFromMeta(i), Blocks.CARPET.getStateFromMeta(i));
		}
	}

}
