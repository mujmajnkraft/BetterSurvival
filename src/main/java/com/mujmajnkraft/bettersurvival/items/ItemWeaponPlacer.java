package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.entities.EntityAirBallon;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityZeppelin;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EnumWeaponType;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWeaponPlacer extends Item {
	
	private EnumWeaponType type;
	
	public ItemWeaponPlacer(EnumWeaponType typeIn) {
		this.maxStackSize = 1;
		this.type = typeIn;
		this.setRegistryName(typeIn.getName()+"_Placer");
		this.setUnlocalizedName(typeIn.getName().toLowerCase()+"placer");
		//this.setCreativeTab(ModItems.siegeweapons);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).isSideSolid(worldIn, pos, facing) && facing == EnumFacing.UP)
		{
			ItemStack itemstack  = player.getHeldItem(hand);
			if (!worldIn.isRemote)
			{
				
				Entity weapon = type.getEntityFromType(worldIn);
				weapon.setPosition(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
				
				if (type == EnumWeaponType.ZEPPELIN)
				{
					EntityAirBallon airballon = new EntityAirBallon(worldIn);
					airballon.setPosition(pos.getX()+0.5, pos.getY()+11, pos.getZ()+0.5);
					((EntityZeppelin)weapon).bind(airballon);
					worldIn.spawnEntity(airballon);
				}
				
				if (itemstack.hasDisplayName())
                {
					weapon.setCustomNameTag(itemstack.getDisplayName());
                }
				worldIn.spawnEntity(weapon);
			}

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

}
