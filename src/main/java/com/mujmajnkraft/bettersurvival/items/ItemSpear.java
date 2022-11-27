package com.mujmajnkraft.bettersurvival.items;

import java.util.List;

import com.google.common.collect.Multimap;
import com.mujmajnkraft.bettersurvival.Bettersurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import com.mujmajnkraft.bettersurvival.config.ConfigHandler;
import com.mujmajnkraft.bettersurvival.entities.projectiles.EntityFlyingSpear;
import com.mujmajnkraft.bettersurvival.init.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("deprecation")
public class ItemSpear extends Item{

    private final float attackDamage;
    public final float reach;
	private ToolMaterial mat;
	
	public ItemSpear(ToolMaterial material) {
		this.setRegistryName("Item"+material.name().toLowerCase()+"Spear");
		this.maxStackSize = 16;
		this.attackDamage = (float) (0.75 * (3.0F + material.getAttackDamage()));
		this.setCreativeTab(CreativeTabs.COMBAT);
		this.reach = 7.0F;
		mat = material;
	}
	
	public ToolMaterial getMaterial()
	{
		return mat;
	}
	
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        boolean flag = playerIn.capabilities.isCreativeMode;
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!itemstack.isEmpty() || flag)
        {
            float f = 1.0f;
            
            boolean flag1 = playerIn.capabilities.isCreativeMode;

            if (!worldIn.isRemote)
            {
                EntityFlyingSpear entityspear = new EntityFlyingSpear(worldIn, playerIn);
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(1);
                entityspear.setSpear(itemstack1);
                entityspear.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, f * 2.0F, 1.0F);
                
                ItemSpear spear =(ItemSpear) itemstack.getItem();
                entityspear.setDamage((spear.getMaterial().getAttackDamage()+3)*0.75);

                if (flag1)
                {
                	entityspear.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                }
                else{
                entityspear.pickupStatus = EntityArrow.PickupStatus.ALLOWED;}

                worldIn.spawnEntity(entityspear);
            }

            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (!flag1)
            {
                playerIn.getHeldItem(handIn).shrink(1);
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
	
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
	
	public float breakChance()
	{
		return 32.0f / this.mat.getMaxUses();
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
                target.attackEntityFrom(DamageSource.MAGIC, ((3.0F + mat.getAttackDamage() + 6.0F) * 0.75F));
            }
        }
		else if (this.mat == ModItems.DESERT_CHITIN || this.mat == ModItems.JUNGLE_CHITIN)
		{
            if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD)
            {
                target.attackEntityFrom(DamageSource.GENERIC, ((3.0F + mat.getAttackDamage() + 6.0F) * 0.75F));
            }
        }
		return super.hitEntity(stack, target, attacker);
	}
}