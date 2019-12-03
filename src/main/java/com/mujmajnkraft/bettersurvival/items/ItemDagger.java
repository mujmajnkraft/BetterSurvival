package com.mujmajnkraft.bettersurvival.items;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("deprecation")
public class ItemDagger extends ItemSword implements ICustomWeapon{
	
	private ToolMaterial mat;
	public ItemDagger(ToolMaterial material) {
		super(material);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Dagger");
		this.setUnlocalizedName(material.name().toLowerCase()+"dagger");
		mat = material;
	}
	
	public ToolMaterial getMaterial()
	{
		return mat;
	}
	
	@Override
	public float getReach() {
		return 0;
	}
	
	@Override
	public boolean noSweepAttack() {
		return false;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 0.7);
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 0.8);
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
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		if(ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot"+this.getMaterial().name()))
		{
			for (ItemStack stack :OreDictionary.getOres("ingot"+this.getMaterial().name()))
			{
				if (net.minecraftforge.oredict.OreDictionary.itemMatches(stack, repair, false))
				{
					return true;
				}
			}
		}
		return super.getIsRepairable(toRepair, repair);
	}
	
	@Override
	public CreativeTabs getCreativeTab()
	{
		if (this.getMaterial() == ToolMaterial.DIAMOND ||this.getMaterial() == ToolMaterial.GOLD ||this.getMaterial() == ToolMaterial.IRON ||this.getMaterial() == ToolMaterial.STONE||this.getMaterial() == ToolMaterial.WOOD)
		{
			return super.getCreativeTab();
		}
		else if ((this.getMaterial() == ModItems.JUNGLE_CHITIN || this.getMaterial() == ModItems.DESERT_CHITIN || this.getMaterial() == ModItems.DRAGON_BONE) && Bettersurvival.isIafLoaded && ConfigHandler.integration)
		{
			return super.getCreativeTab();
		}
		else if (ConfigHandler.integration && !OreDictionary.getOres("ingot"+this.getMaterial().name()).isEmpty())
		{
			return super.getCreativeTab();
		}
		return null;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (this.mat == ModItems.SILVER && Bettersurvival.isIafLoaded)
		{
			String s = I18n.translateToLocal("silvertools.hurt");
			tooltip.add(TextFormatting.GREEN + s);
		}
		else if (this.mat == ModItems.JUNGLE_CHITIN || this.mat == ModItems.DESERT_CHITIN)
		{
			String s = I18n.translateToLocal(Reference.MOD_ID + ".chitintools.hurt");
			tooltip.add(TextFormatting.GREEN + s);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (this.mat == ModItems.SILVER && Bettersurvival.isIafLoaded)
		{
            if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
            {
                target.hurtResistantTime = 0;
                target.attackEntityFrom(DamageSource.MAGIC, 2);
            }
        }
		else if (this.mat == ModItems.DESERT_CHITIN || this.mat == ModItems.JUNGLE_CHITIN)
		{
            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD)
            {
                target.hurtResistantTime = 0;
                target.attackEntityFrom(DamageSource.MAGIC, 4);
            }
        }
		return super.hitEntity(stack, target, attacker);
	}

}
