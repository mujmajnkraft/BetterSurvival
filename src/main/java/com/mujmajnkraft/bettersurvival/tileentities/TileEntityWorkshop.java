package com.mujmajnkraft.bettersurvival.tileentities;
/*
import java.util.List;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.WorkshopRecipe;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntitySiegeWeapon;
import com.mujmajnkraft.bettersurvival.init.ModItems;
import com.mujmajnkraft.bettersurvival.items.ItemBlueprint;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityWorkshop extends TileEntityLockable implements ITickable{
	
	private String customName;
	private int progress;
    private NonNullList<ItemStack> workshopItemStacks = NonNullList.<ItemStack>withSize(18, ItemStack.EMPTY);

	@Override
	public int getSizeInventory() {
		return this.workshopItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.workshopItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.workshopItemStacks.size() ? (ItemStack)this.workshopItemStacks.get(index) : ItemStack.EMPTY;
	}

	public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.workshopItemStacks, index, count);
    }*/

    /**
     * Removes a stack from the given slot and returns it.
     *//*
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.workshopItemStacks, index);
    }*/

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *//*
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        if (index >= 0 && index < this.workshopItemStacks.size())
        {
            this.workshopItemStacks.set(index, stack);
        }
    }*/

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     *//*
    public int getInventoryStackLimit()
    {
        return 64;
    }*/

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *//*
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }*/

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     *//*
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.workshopItemStacks.clear();
		
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "modcontainer.workshop";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGuiID() {
		return Reference.MOD_ID + "workshop";
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, workshopItemStacks);
		
		if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		ItemStackHelper.loadAllItems(compound, this.workshopItemStacks);

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
	}

	@Override
	public void update() 
	{
		if (this.progress > 0)
		{
			if (this.doIngredientCheck())
			{
				this.progress++;
				System.out.println(progress);
			}
		}
	}

	public void startCrafting()
	{
		System.out.println("Attemping to start crafting");
		if (this.workshopItemStacks.get(0).getItem() == ModItems.blueprint && this.progress <= 0)
		{
			if (ItemBlueprint.getType(this.workshopItemStacks.get(0)) != null)
			{
				if (!WorkshopRecipe.instance().getIngredients(ItemBlueprint.getType(this.workshopItemStacks.get(0))).isEmpty())
				{
					if (this.doIngredientCheck())
					{
						this.progress = 1;
					}
				}
			};
		}
	}
	
	private boolean doIngredientCheck()
	{
		List<ItemStack> ingredients = WorkshopRecipe.instance().getIngredients(ItemBlueprint.getType(this.workshopItemStacks.get(0)));
		int TimeNeeded = 0;
		for (int i = 0; i < 9; i++)
		{
			ItemStack avalible = this.workshopItemStacks.get(i+1);
			ItemStack required = ingredients.get(i);
			TimeNeeded += required.getCount() * 20;
			if (!required.isEmpty())
			{
				if (required.getItem() != avalible.getItem() || required.getCount() > avalible.getCount())
				{
					return false;
				}
			}
		}
		if (this.progress >= TimeNeeded)
		{
			this.finish();
			return false;
		}
		return true;
	}
	
	private void finish()
	{
		List<ItemStack> ingredients = WorkshopRecipe.instance().getIngredients(ItemBlueprint.getType(this.workshopItemStacks.get(0)));
		for (int i = 0; i < 9; i++)
		{
			this.workshopItemStacks.get(i+1).shrink(ingredients.get(i).getCount());
		}
		EntitySiegeWeapon weapon = ItemBlueprint.getType(this.workshopItemStacks.get(0)).getEntityFromType(this.world);
		System.out.println(weapon);
	}
	
	

}*/
