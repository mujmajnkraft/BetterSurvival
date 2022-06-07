package com.mujmajnkraft.bettersurvival.items;

import java.util.Random;
import java.util.UUID;

import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
		this.setUnlocalizedName(material.name().toLowerCase()+"battleaxe");
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
					//EntityPlayer player = (EntityPlayer) target;
					/*if(player.getHeldItemOffhand().getItem() instanceof ItemShield && player.getActiveHand() == EnumHand.OFF_HAND)
					{
						ItemStack stack1 = player.getHeldItemOffhand();
						player.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
						player.entityDropItem(stack1, 1);
					}
					else*/
					{
						ItemStack stack1 = target.getHeldItemMainhand();
						target.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
						target.entityDropItem(stack1, 1);
					}
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
			
			attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("a6107045-134f-4c04-a645-75c3ae5c7a27"), "compensation", 4, 2);
			
			if (!attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(modifier))
			{
				attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(modifier);
			}
			attacker.resetCooldown();
			
		}
		return false;
	}
}
