package com.mujmajnkraft.bettersurvival.items;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemNunchaku extends ItemSword implements ICustomWeapon{

	private ToolMaterial mat;
	public ItemNunchaku(ToolMaterial material) {
		super(material);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Nunchaku");
		this.setUnlocalizedName(material.name().toLowerCase()+"nunchaku");
		mat = material;
		
		this.addPropertyOverride(new ResourceLocation("spinning"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn == null ? 0.0F : (isSpinning(stack) ? 1.0F : 0.0F);
            }
        });
	}
	
	public ToolMaterial getMaterial()
	{
		return mat;
	}
	
	public boolean isSpinning(ItemStack stack)
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("Spinning"))
			{
				return compound.getBoolean("Spinning");
			}
		}
		return false;
	}

	@Override
	public float getReach() {
		return 5F;
	}

	@Override
	public boolean isTwoHand() {
		return false;
	}

	@Override
	public boolean noSweepAttack() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		GameSettings GS = Minecraft.getMinecraft().gameSettings;
		if (GS.keyBindAttack.isKeyDown() && isSelected && Minecraft.getMinecraft().currentScreen == null)
		{
			this.setSpinning(true, stack);
		}
		else
		{
			this.setSpinning(false, stack);
		}
    }
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 0.5);
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 0.1);
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
	
	public void setSpinning(boolean Spinning, ItemStack stack)
	{
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("Spinning", Spinning);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		int i = EnchantmentHelper.getKnockbackModifier(attacker) + 1;
		target.knockBack(attacker, -(float)i * 0.1F, (double)MathHelper.sin(attacker.rotationYaw * (float)i * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * (float)i * 0.017453292F)));
        stack.damageItem(1, attacker);
        return true;
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
