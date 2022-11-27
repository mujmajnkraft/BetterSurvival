package com.mujmajnkraft.bettersurvival.items;

import java.util.Random;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;

public class ItemBattleAxe extends ItemCustomWeapon{
	
	public ItemBattleAxe(ToolMaterial material)
	{
		super(material, 1.6f, 1.25f);
		this.setRegistryName("Item"+material.name().toLowerCase()+"BattleAxe");
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer attacker, Entity entity)
	{
		if (attacker.getCooledAttackStrength(0.5F) > 0.9 && entity instanceof EntityLivingBase)
		{
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.disarm, stack);
			if (attacker.getRNG().nextInt(20)<(2+l) && !entity.getIsInvulnerable())
			{
				EntityLivingBase target = (EntityLivingBase) entity;
				if (target instanceof EntityPlayer)
				{
					ItemStack stack1 = target.getHeldItemMainhand();
					target.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
					target.entityDropItem(stack1, 1);
				}
				else
				{
					if(!target.getHeldItemMainhand().isEmpty())
					{
						ItemStack item = target.getHeldItemMainhand();
						NBTTagCompound nbttagcompound = target.writeToNBT(new NBTTagCompound());
						if (nbttagcompound.hasKey("HandDropChances", 9))
				        {
				            NBTTagList nbttaglist = nbttagcompound.getTagList("HandDropChances", 5);
				            float chance = nbttaglist.getFloatAt(0);
				            target.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
							int rnd = new Random().nextInt(100);
							if (chance*100+EnchantmentHelper.getMaxEnchantmentLevel(net.minecraft.init.Enchantments.LOOTING, attacker)>rnd+1)
							{
								target.entityDropItem(item, 1);
							}
						}
					}
				}
			}
		}
		return false;
	}
}
