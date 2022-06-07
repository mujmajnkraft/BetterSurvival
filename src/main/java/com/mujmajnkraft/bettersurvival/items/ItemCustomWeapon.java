package com.mujmajnkraft.bettersurvival.items;

import java.util.List;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemCustomWeapon extends Item{
	
	float attackDamage;
	double attackSpeed;
	ToolMaterial material;
	
	public ItemCustomWeapon(ToolMaterial material, float damageModifier, float delayModifier)
	{
		this.material = material;
		this.maxStackSize = 1;
		this.attackDamage = (3.0F + material.getAttackDamage()) * damageModifier;
		this.attackSpeed = -2.4000000953674316 * delayModifier;
		this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(CreativeTabs.COMBAT);
	}
	
	public ToolMaterial getMaterial()
	{
		return material;
	}
	
	public float getAttackDamage() {
		return attackDamage;
	}
	
	//Copied from ItemSword
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public int getItemEnchantability()
    {
		return this.material.getEnchantability();
	}
    
	public String getToolMaterialName()
	{
		return this.material.toString();
	}
	
	@Override
	public CreativeTabs getCreativeTab()
	{
		if (material == ToolMaterial.DIAMOND ||material == ToolMaterial.GOLD ||material == ToolMaterial.IRON ||material == ToolMaterial.STONE||material == ToolMaterial.WOOD)
		{
			return super.getCreativeTab();
		}
		else if ((material == ModItems.JUNGLE_CHITIN || material == ModItems.DESERT_CHITIN || material == ModItems.DRAGON_BONE) && Bettersurvival.isIafLoaded && ConfigHandler.integration)
		{
			return super.getCreativeTab();
		}
		else if (ConfigHandler.integration && !OreDictionary.getOres("ingot"+material.name()).isEmpty())
		{
			return super.getCreativeTab();
		}
		return null;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		if(ConfigHandler.integration && OreDictionary.doesOreNameExist("ingot" + material.name()))
		{
			for (ItemStack stack :OreDictionary.getOres("ingot" + material.name()))
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
	@SideOnly((Side.CLIENT))
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (this.material == ModItems.SILVER && Bettersurvival.isIafLoaded)
		{
			String s = I18n.translateToLocal("silvertools.hurt");
			tooltip.add(TextFormatting.GREEN + s);
		}
		else if (this.material == ModItems.JUNGLE_CHITIN || this.material == ModItems.DESERT_CHITIN)
		{
			String s = I18n.translateToLocal(Reference.MOD_ID + ".chitintools.hurt");
			tooltip.add(TextFormatting.GREEN + s);
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (this.material == ModItems.SILVER && Bettersurvival.isIafLoaded)
		{
            if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
            {
                target.attackEntityFrom(DamageSource.MAGIC, this.getAttackDamage() + 3);
            }
        }
		else if (this.material == ModItems.DESERT_CHITIN || this.material == ModItems.JUNGLE_CHITIN)
		{
            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD)
            {
                target.attackEntityFrom(DamageSource.MAGIC, this.getAttackDamage() + 5);
            }
        }
		stack.damageItem(1, attacker);
		return super.hitEntity(stack, target, attacker);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, 0));
		}
		return multimap;
	}
	
	//Allows sword enchantments 
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
		if (enchantment.type == EnumEnchantmentType.WEAPON && !(enchantment instanceof EnchantmentSweepingEdge))
		{
			return true;
		}
		else
		{
			return enchantment.type.canEnchantItem(stack.getItem());
		}
    }

}
