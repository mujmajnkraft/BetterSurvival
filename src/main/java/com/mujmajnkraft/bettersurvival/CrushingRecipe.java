package com.mujmajnkraft.bettersurvival;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class CrushingRecipe {
	
	private static final CrushingRecipe CRUSHING_BASE = new CrushingRecipe();
	private final Map<IBlockState, IBlockState> crushingList = Maps.<IBlockState, IBlockState>newHashMap();
	
	public static CrushingRecipe instance()
	{
		return CRUSHING_BASE;
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
	
	private CrushingRecipe()
	{
		addCrushingRecipe(Blocks.STONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState());
		addCrushingRecipe(Blocks.SANDSTONE.getDefaultState(), Blocks.SAND.getDefaultState());
		addCrushingRecipe(Blocks.STONEBRICK.getDefaultState(), Blocks.STONEBRICK.getStateFromMeta(2));
		addCrushingRecipe(Blocks.STONEBRICK.getStateFromMeta(2), Blocks.COBBLESTONE.getDefaultState());
		addCrushingRecipe(Blocks.COBBLESTONE.getDefaultState(), Blocks.GRAVEL.getDefaultState());
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
	}

}
