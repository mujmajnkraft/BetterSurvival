package com.mujmajnkraft.bettersurvival.items;

import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityBallista;
import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntityCannon;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAmmo extends Item{
	
	private int type;
	
	public ItemAmmo(int type) {
		this.type = type;
		this.maxStackSize = 16;
		//this.setCreativeTab(ModItems.siegeweapons);
	}
	
	public int getType()
	{
		return this.type;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		int t = this.type;
		if (playerIn.getRidingEntity() instanceof EntityCannon && (t>0 && t<4))
		{
			EntityCannon ballista = (EntityCannon) playerIn.getRidingEntity();
			ItemStack gunpowder = findGunPowder(playerIn);
			if (gunpowder != ItemStack.EMPTY)
			{
				boolean loaded = ballista.load(t);
				if (loaded && !playerIn.capabilities.isCreativeMode) 
				{
					gunpowder.shrink(1);
					playerIn.getHeldItem(handIn).shrink(1);

                    if (gunpowder.isEmpty())
                    {
                        playerIn.inventory.deleteStack(gunpowder);
                    }
				}
			}
		}
		if (playerIn.getRidingEntity() instanceof EntityBallista && t==4)
		{
			EntityBallista ballista = (EntityBallista) playerIn.getRidingEntity();
			boolean loaded = ballista.load();
			if (loaded && !playerIn.capabilities.isCreativeMode) playerIn.getHeldItem(handIn).shrink(1);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	private ItemStack findGunPowder(EntityPlayer player)
	{
		for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = player.inventory.getStackInSlot(i);

            if (itemstack.getItem() == Items.GUNPOWDER)
            {
                return itemstack;
            }
        }

        return ItemStack.EMPTY;
	}

}
