package com.mujmajnkraft.bettersurvival.items;

import java.util.List;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemCustomWeapon extends Item {
	
	final float attackDamage;
	final double attackSpeed;
	final ToolMaterial material;

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
	
	public float getAttackDamage()
	{
		return attackDamage;
	}

	public float getReachBonus() { return 0; }
	
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
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
		{
		    stack.damageItem(2, entityLiving);
		}
		
		return true;
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(Bettersurvival.isIafLoaded) {
			if(this.material == ModItems.SILVER) {
				String s = net.minecraft.client.resources.I18n.format("silvertools.hurt");
				tooltip.add(TextFormatting.GREEN + s);
			}
			else if(this.material == ModItems.JUNGLE_CHITIN || this.material == ModItems.DESERT_CHITIN) {
				String s = net.minecraft.client.resources.I18n.format(Reference.MOD_ID + ".chitintools.hurt");
				tooltip.add(TextFormatting.GREEN + s);
			}
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
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
	@Override
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
