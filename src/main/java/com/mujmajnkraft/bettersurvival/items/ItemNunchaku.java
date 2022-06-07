package com.mujmajnkraft.bettersurvival.items;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProwider;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemNunchaku extends ItemCustomWeapon{

	private ToolMaterial mat;
	public ItemNunchaku(ToolMaterial material) {
		super(material, 0.5F, 0.1F);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Nunchaku");
		this.setUnlocalizedName(material.name().toLowerCase()+"nunchaku");
		mat = material;
		
		this.addPropertyOverride(new ResourceLocation("spinning"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if (entityIn != null)
            	{
            		if (entityIn.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null).isSpinning() && entityIn.getHeldItemMainhand() == stack) return 1.0F;
            	}
                return 0.0F;
            }
        });
	}
		
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		int i = EnchantmentHelper.getKnockbackModifier(attacker) + 1;
		target.knockBack(attacker, -(float)i * 0.1F, (double)MathHelper.sin(attacker.rotationYaw * (float)i * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * (float)i * 0.017453292F)));
        
		if (this.mat == ModItems.SILVER && Bettersurvival.isIafLoaded)
		{
            if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
            {
                target.attackEntityFrom(DamageSource.MAGIC, ((3.0F + mat.getAttackDamage() + 6.0F) * 0.5F));
            }
        }
		else if (this.mat == ModItems.DESERT_CHITIN || this.mat == ModItems.JUNGLE_CHITIN)
		{
            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD)
            {
                target.attackEntityFrom(DamageSource.GENERIC, ((3.0F + mat.getAttackDamage() + 6.0F) * 0.5F));
            }
        }
		return super.hitEntity(stack, target, attacker);
    }
	
	@Override
	public CreativeTabs getCreativeTab() {
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
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		return true;
	}
}
