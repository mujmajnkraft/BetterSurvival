package com.mujmajnkraft.bettersurvival.items;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraftforge.oredict.OreDictionary;

public class ItemBattleAxe extends ItemSword implements ICustomWeapon{
	
	private ToolMaterial mat;

	public ItemBattleAxe(ToolMaterial material) {
		super(material);
		this.setRegistryName("Item"+material.name().toLowerCase()+"BattleAxe");
		this.setUnlocalizedName(material.name().toLowerCase()+"battleaxe");
		mat = material;
	}
	
	public ToolMaterial getMaterial()
	{
		return mat;
	}	
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 1.6);
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 1.25);
		}

		return modifiers;
	}

	/**
	 * Replace a modifier in the {@link Multimap} with a copy that's had {@code multiplier} applied to its value.
	 *
	 * @param modifierMultimap The MultiMap
	 * @param attribute        The attribute being modified
	 * @param id               The ID of the modifier
	 * @param multiplier       The multiplier to apply
	 */
	private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier) {
		// Get the modifiers for the specified attribute
		final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

		// Find the modifier with the specified ID, if any
		final java.util.Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		if (modifierOptional.isPresent()) { // If it exists,
			final AttributeModifier modifier = modifierOptional.get();
			modifiers.remove(modifier); // Remove it
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation())); // Add the new modifier
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer attacker, Entity entity)
	{
		if (attacker.getCooledAttackStrength(0.5F) > 0.9 && entity instanceof EntityLivingBase)
		{
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.disarm, stack);
			if (new Random().nextInt(20)<(2+l))
			{
				EntityLivingBase target = (EntityLivingBase) entity;
				if (target instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) target;
					if(player.getActiveItemStack().getItem() == Items.SHIELD && player.getHeldItemOffhand().getItem() == Items.SHIELD)
					{
						ItemStack item = player.getHeldItemOffhand();
						player.setHeldItem(EnumHand.OFF_HAND, null);
						player.entityDropItem(item, 1);
					}
					else
					{
						ItemStack item = target.getHeldItemMainhand();
						target.setHeldItem(EnumHand.MAIN_HAND, null);
						target.entityDropItem(item, 1);
					}
				}
				else
				{
					if(target.getHeldItemMainhand() !=null)
					{
						ItemStack item = target.getHeldItemMainhand();
						NBTTagCompound nbttagcompound = target.writeToNBT(new NBTTagCompound());
						System.out.println(nbttagcompound.toString());
						if (nbttagcompound.hasKey("HandDropChances", 9))
				        {
				            NBTTagList nbttaglist = nbttagcompound.getTagList("HandDropChances", 5);
				            float chance = nbttaglist.getFloatAt(0);
				            System.out.println(chance);
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
	
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
		if (enchantment instanceof EnchantmentSweepingEdge)
		{
			return false;
		}
		else
		{
			return enchantment.type.canEnchantItem(stack.getItem());
		}
    }

	@Override
	public float getReach() {
		return 0;
	}

	@Override
	public boolean isTwoHand() {
		return true;
	}

	@Override
	public boolean noSweepAttack() {
		return false;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		if (this.getMaterial() == ToolMaterial.DIAMOND ||this.getMaterial() == ToolMaterial.GOLD ||this.getMaterial() == ToolMaterial.IRON ||this.getMaterial() == ToolMaterial.STONE||this.getMaterial() == ToolMaterial.WOOD)
		{
			return super.getIsRepairable(toRepair, repair);
		}
		else if(ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+this.getMaterial().name()))
		{
			for (ItemStack stack :OreDictionary.getOres("ingot"+this.getMaterial().name()))
			{
				if (net.minecraftforge.oredict.OreDictionary.itemMatches(stack, repair, false))
				{
					return true;
				}
			}
		}
		return false;
	}
}
