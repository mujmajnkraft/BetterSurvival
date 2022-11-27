package com.mujmajnkraft.bettersurvival.items;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProwider;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNunchaku extends ItemCustomWeapon{

	public ItemNunchaku(ToolMaterial material) {
		super(material, 0.5F, 0.1F);
		this.setRegistryName("Item"+material.name().toLowerCase()+"Nunchaku");
		
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
		if (attacker instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) attacker;
			INunchakuCombo combo = player.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null);
			int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.combo, stack);
			combo.setComboPower(combo.getComboPower() + 0.1F + l/20F);
		}
		return super.hitEntity(stack, target, attacker);
    }
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		return true;
	}
}
