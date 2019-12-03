package com.mujmajnkraft.bettersurvival.client.render;

import com.mujmajnkraft.bettersurvival.init.ModBlocks;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityCustomCauldron;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class CustomBlockColor {
	
	public static void registerBlockColors()
	{
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex)
			{
				TileEntityCustomCauldron TE = (TileEntityCustomCauldron) worldIn.getTileEntity(pos);
				if (TE != null)
				{
					return TE.getColor();
				}
				return 0;
			}
		}, ModBlocks.customcauldron);
	}

}
