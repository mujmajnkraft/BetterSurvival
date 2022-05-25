package com.mujmajnkraft.bettersurvival;
/*
import com.mujmajnkraft.bettersurvival.client.GUIWorkshop;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityWorkshop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGUIHandler implements IGuiHandler{
	
	public static final int WORKSHOP_GUI = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == WORKSHOP_GUI)
		{
			return new ContainerWorkshop(player.inventory,(TileEntityWorkshop) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == WORKSHOP_GUI)
		{
			return new GUIWorkshop(player.inventory,(TileEntityWorkshop) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
*/