package com.mujmajnkraft.bettersurvival.blocks;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.ModGUIHandler;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityWorkshop;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWorkshop extends Block implements ITileEntityProvider{

	public BlockWorkshop() {
		super(Material.WOOD);
        //this.setCreativeTab(ModItems.siegeweapons);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWorkshop();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote)
		{
			System.out.println("Activating");
			playerIn.openGui(Bettersurvival.instance, ModGUIHandler.WORKSHOP_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}
