package com.mujmajnkraft.bettersurvival.items;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemNunchaku extends ItemCustomWeapon {

	public ItemNunchaku(ToolMaterial material) {
		super(material, 0.5F, 0.1F);
		this.setRegistryName(Reference.MOD_ID,"item"+material.name().toLowerCase()+"nunchaku");
		this.setTranslationKey(material.name().toLowerCase()+"nunchaku");
		
		this.addPropertyOverride(new ResourceLocation("spinning"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            	if(entityIn != null && entityIn.getHeldItemMainhand() == stack) {
					INunchakuCombo cap = entityIn.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
            		if(cap != null && cap.isSpinning()) return 1.0F;
            	}
                return 0.0F;
            }
        });
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		int i = EnchantmentHelper.getKnockbackModifier(attacker) + 1;
		target.knockBack(attacker, -(float)i * 0.1F, (double)MathHelper.sin(attacker.rotationYaw * (float)i * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * (float)i * 0.017453292F)));
		if (attacker instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) attacker;
			INunchakuCombo combo = player.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
			if(combo != null) {
				int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.combo, stack);
				combo.setComboPower(combo.getComboPower() + 0.1F + l/20F);
			}
		}
		return super.hitEntity(stack, target, attacker);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String s = net.minecraft.client.resources.I18n.format(Reference.MOD_ID + ".nunchaku.desc");
		tooltip.add(TextFormatting.AQUA + s);
	}
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		return true;
	}
}