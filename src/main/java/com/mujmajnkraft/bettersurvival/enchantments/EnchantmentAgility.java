package com.mujmajnkraft.bettersurvival.enchantments;

import java.util.UUID;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentAgility extends Enchantment {

	public EnchantmentAgility() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
		this.setRegistryName("agility");
		this.setName(Reference.MOD_ID + ".agility");
	}
	
	public static final UUID speedModifier = UUID.fromString("e6107045-134f-4c54-a645-75c3ae5c7a27");
	
	//Called during LivingUpdateEvent if enchantment is active and modifier not present
	public static void applySpeedModifier(EntityLivingBase entity)
	{
		double d = 0.01 * EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity);
		AttributeModifier modifier = new AttributeModifier(EnchantmentAgility.speedModifier, "agility", d, 0);
		IAttributeInstance speed = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		if (!speed.hasModifier(modifier))
		{
			speed.applyModifier(modifier);
		}
		else if (speed.getModifier(EnchantmentAgility.speedModifier).getAmount() != d)
		{
			speed.removeModifier(EnchantmentAgility.speedModifier);
			speed.applyModifier(modifier);
		}
	}
	
	@Override
	public int getMaxLevel()
    {
        return ConfigHandler.agilitylevel;
    }
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }
	
	public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
	
	public boolean isTreasureEnchantment()
    {
    	return ConfigHandler.agility;
    }
	
	public boolean isAllowedOnBooks()
    {
		return ConfigHandler.agilitylevel != 0;
    }
}
