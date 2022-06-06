package com.mujmajnkraft.bettersurvival;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import de.Whitedraco.switchbow.helper.ArrowItemStackEqual;
import de.Whitedraco.switchbow.helper.QuiverArrowHelper;
import de.Whitedraco.switchbow.helper.SwitchBowHelper;

public class SwitchBowCompat {
	
	public static ItemStack findAmmo(EntityPlayer player, ItemStack bow)
	{
		if (ArrowItemStackEqual.containsArrow(QuiverArrowHelper.getArrowsInInvAndQuiver(player, player.inventory), SwitchBowHelper.getSelectionArrow(bow)))
			return SwitchBowHelper.getSelectionArrow(bow); 
	return ItemStack.EMPTY;
	}

}
