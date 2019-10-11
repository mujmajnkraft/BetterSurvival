package com.mujmajnkraft.bettersurvival.items;

import java.util.UUID;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCustomShield extends ItemShield {
	
	public ItemCustomShield(float blockpower, int weight) {
		this.maxStackSize = 1;
		this.blockpower = blockpower;
		this.weight = weight;
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setMaxDamage(250*weight);
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
	}
	
	public float blockpower;
	private int weight;
	
	@Override
	public int getItemEnchantability() {
		
		return 30 - 5 * this.weight;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.NONE;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack.getSubCompound("BlockEntityTag") != null)
        {
            EnumDyeColor enumdyecolor = TileEntityBanner.getColor(stack);
            return I18n.translateToLocal(this.getUnlocalizedName() + enumdyecolor.getUnlocalizedName() + ".name");
        }
        else
        {
            return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Items.IRON_INGOT ? true : false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString("d6107045-134f-4c54-a645-75c3ae5c7a27"));
		playerIn.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(UUID.fromString("d6107045-134f-4c54-a645-75c0ae5c7a27"));
		if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.heavy, playerIn.getHeldItem(handIn)) > 0)
		{
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("d6107045-134f-4c54-a645-75c0ae5c7a27"), "shieldknockbackadjustment", 1, 0);
			playerIn.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(modifier);
		}
		int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.weightless, playerIn.getHeldItem(handIn));
		float w = this.weight - 2*l >= 0 ? this.weight - 2*l : 0;
		double multiplyer = Math.pow(2, 1 - w) - 1;
		AttributeModifier modifier = new AttributeModifier(UUID.fromString("d6107045-134f-4c54-a645-75c3ae5c7a27"), "shieldspeedadjustment", multiplyer, 2);
		playerIn.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(modifier);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
