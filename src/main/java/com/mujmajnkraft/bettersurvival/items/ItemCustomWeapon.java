package com.mujmajnkraft.bettersurvival.items;

import java.util.List;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.integration.InFCompat;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.integration.InFLightningForkCompat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
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
	
	private final float attackDamage;
	private final double attackSpeed;
	private final ToolMaterial material;

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
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		ItemStack mat = this.material.getRepairItemStack();
		if(!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
		if(ForgeConfigHandler.materials.moddedMaterials && OreDictionary.doesOreNameExist("ingot" + this.material.name())) {
			for(ItemStack stack :OreDictionary.getOres("ingot" + this.material.name())) {
				if(net.minecraftforge.oredict.OreDictionary.itemMatches(stack, repair, false)) {
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
		if(BetterSurvival.isIafLoaded) {
			if(this.material == InFCompat.SILVER) {
				String s = net.minecraft.client.resources.I18n.format("silvertools.hurt");
				tooltip.add(TextFormatting.GREEN + s);
			}
			else if(this.material == InFCompat.JUNGLE_CHITIN || this.material == InFCompat.DESERT_CHITIN) {
				String s = net.minecraft.client.resources.I18n.format("myrmextools.hurt");
				tooltip.add(TextFormatting.GREEN + s);
			}
			else if(this.material == InFCompat.JUNGLE_STINGER || this.material == InFCompat.DESERT_STINGER) {
				String s = net.minecraft.client.resources.I18n.format("myrmextools.hurt");
				tooltip.add(TextFormatting.GREEN + s);
				s = net.minecraft.client.resources.I18n.format("myrmextools.poison");
				tooltip.add(TextFormatting.DARK_GREEN + s);
			}
			else if(this.material == InFCompat.DRAGON_BONE_ICED) {
				String s = net.minecraft.client.resources.I18n.format("dragon_sword_ice.hurt1");
				tooltip.add(TextFormatting.GREEN + s);
				s = net.minecraft.client.resources.I18n.format("dragon_sword_ice.hurt2");
				tooltip.add(TextFormatting.RED + s);
			}
			else if(this.material == InFCompat.DRAGON_BONE_FLAMED) {
				String s = net.minecraft.client.resources.I18n.format("dragon_sword_fire.hurt1");
				tooltip.add(TextFormatting.GREEN + s);
				s = net.minecraft.client.resources.I18n.format("dragon_sword_fire.hurt2");
				tooltip.add(TextFormatting.RED + s);
			}
			else if(BetterSurvival.isIafLightningForkLoaded && this.material == InFLightningForkCompat.DRAGON_BONE_LIGHTNING) {
				String s = net.minecraft.client.resources.I18n.format("dragon_sword_lightning.hurt1");
				tooltip.add(TextFormatting.GREEN + s);
				s = net.minecraft.client.resources.I18n.format("dragon_sword_lightning.hurt2");
				tooltip.add(TextFormatting.RED + s);
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
		if(!(enchantment instanceof EnchantmentSweepingEdge) && (enchantment.type == EnumEnchantmentType.WEAPON))
		{
			return true;
		}
		else
		{
			return super.canApplyAtEnchantingTable(stack, enchantment);
		}
    }
	
	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return false;
    }

}
