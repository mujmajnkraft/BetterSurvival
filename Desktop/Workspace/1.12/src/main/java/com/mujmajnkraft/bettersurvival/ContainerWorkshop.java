package com.mujmajnkraft.bettersurvival;

import java.util.ArrayList;
import java.util.Arrays;

import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EnumWeaponType;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.items.ItemBlueprint;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityWorkshop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerWorkshop extends Container{
	
	private final IInventory workshopInventory;
	public TileEntityWorkshop TE;
	
	public ContainerWorkshop(IInventory playerInventory, TileEntityWorkshop InventoryIn)
	{
		this.workshopInventory = InventoryIn;
		this.TE = InventoryIn;
        
		this.addSlotToContainer(new Slot(workshopInventory, 0, 8, 26)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == ModItems.blueprint;
            }
		});
		
		ArrayList<Integer> Xcoord = new ArrayList<>(Arrays.asList(new Integer [] {25,15,13,56,70,136,147,139,135}));
		ArrayList<Integer> Ycoord = new ArrayList<>(Arrays.asList(new Integer [] {109,79,65,45,49,51,63,79,113}));
		
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(workshopInventory, i+1, Xcoord.get(i), Ycoord.get(i))
			{
				public boolean isItemValid(ItemStack stack)
	            {
					EnumWeaponType type = ItemBlueprint.getType(InventoryIn.getStackInSlot(0));
					if (type != null)
					{
						Item item = WorkshopRecipe.instance().getIngredients(type).get(this.slotNumber-1).getItem();
						return stack.getItem() == item;
					}
					return false;
	            }
				
				@SideOnly(Side.CLIENT)
				public boolean isEnabled()
				{
					EnumWeaponType type = ItemBlueprint.getType(InventoryIn.getStackInSlot(0));
					if (type != null)
					{
						return !WorkshopRecipe.instance().getIngredients(type).get(this.slotNumber-1).isEmpty();
					}
					return false;
				}
			});
		}
		/*
		this.addSlotToContainer(new Slot(workshopInventory, 1, 137, 77)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.STRING && InventoryIn.getStackInSlot(0).getItem() == ModItems.blueprint;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Potion_Thrower") || type.equals("Ballista");
				}
				return false;
			}
		});
		
		this.addSlotToContainer(new Slot(workshopInventory, 2, 25, 109)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Item.getItemFromBlock(Blocks.PLANKS);
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return InventoryIn.getStackInSlot(0).getItem() == ModItems.blueprint;
			}
		});
		this.addSlotToContainer(new Slot(workshopInventory, 3, 13, 65)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.IRON_INGOT;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Ballista") ||  type.equals("Potion_Thrower") || type.equals("Zeppelin");
				}
				return false;
			}
		});
		this.addSlotToContainer(new Slot(workshopInventory, 4, 56, 45)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.LEATHER;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Potion_Thrower");
				}
				return false;
			}
		});
		
		this.addSlotToContainer(new Slot(workshopInventory, 5, 147, 63)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.STRING && InventoryIn.getStackInSlot(0).getItem() == ModItems.blueprint;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Ballista") || type.equals("Zeppelin");
				}
				return false;
			}
		});
		
		this.addSlotToContainer(new Slot(workshopInventory, 6, 123, 113)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.REDSTONE && InventoryIn.getStackInSlot(0).getItem() == ModItems.blueprint;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Zeppelin");
				}
				return false;
			}
		});
		
		this.addSlotToContainer(new Slot(workshopInventory, 7, 70, 49)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Item.getItemFromBlock(Blocks.WOOL) && InventoryIn.getStackInSlot(0).getItem() == ModItems.blueprint;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Zeppelin");
				}
				return false;
			}
		});
		
		this.addSlotToContainer(new Slot(workshopInventory, 8, 14, 78)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.FLINT;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Cannon");
				}
				return false;
			}
		});
		
		this.addSlotToContainer(new Slot(workshopInventory, 9, 135, 50)
		{
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.IRON_INGOT;
            }
			
			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				if (workshopInventory.getStackInSlot(0).getItem() == ModItems.blueprint)
				{
					String type = ((ItemBlueprint)workshopInventory.getStackInSlot(0).getItem()).getType(workshopInventory.getStackInSlot(0));
					return type.equals("Cannon");
				}
				return false;
			}
		});*/
		
		for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 150 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 208));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return workshopInventory.isUsableByPlayer(playerIn);
	}

}
