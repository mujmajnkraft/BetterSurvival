package com.mujmajnkraft.bettersurvival.items;

import java.util.UUID;

import javax.annotation.Nullable;

import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentReflection;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;

import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.block.BlockDispenser;
import net.minecraft.command.AdvancementCommand;
import net.minecraft.command.CommandException;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCustomShield extends ItemShield {
	
	public ItemCustomShield(float blockpower, int weight) {
		this.maxStackSize = 1;
		this.blockpower = blockpower;
		this.weight = weight;
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setMaxDamage(250 * weight);
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

	public static UUID weightModifierUUID = UUID.fromString("d6107045-134f-4c54-a645-75c3ae5c7a27");
	
	public static UUID knockbackModifierUUID = UUID.fromString("d6107045-134f-4c54-a645-75c0ae5c7a27");
	
	public void applyModifiers(EntityLivingBase entity)
	{
		AttributeModifier KRmodifier = new AttributeModifier(knockbackModifierUUID, "shield_knockback_adjustment", 1, 0);
		if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.heavy, entity.getActiveItemStack()) > 0)
		{
			if (!entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).hasModifier(KRmodifier))
			{
				entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(KRmodifier);
			}
		}
		else
		{
			entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(KRmodifier);
		}
		int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.weightless, entity.getActiveItemStack());
		int w = ((ItemCustomShield)entity.getActiveItemStack().getItem()).getWeight();
		float i = w - 2*l >= 0 ? w - 2*l : 0;
		double multiplyer = Math.pow(2, 1 - i) - 1;
		AttributeModifier speedmodifier = new AttributeModifier(weightModifierUUID, "shield_speed_adjustment", multiplyer, 2);
		if (!entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(speedmodifier))
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(speedmodifier);
		}
		else if (entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(weightModifierUUID).getAmount() != multiplyer)
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(weightModifierUUID);
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(speedmodifier);
		}
	}
	
	//Blocking arrows works just like with vanilla shield
	public static boolean blockArrow(DamageSource source, EntityLivingBase defender)
	{
		if (canBlockDamageSource(source, defender) && defender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) defender;
			player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
			ItemStack copyBeforeUse = player.getActiveItemStack().copy();
			player.getActiveItemStack().damageItem(1, player);
			
			if (player instanceof EntityPlayerMP)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP) player;
				try {
					grantAdvancement(playerMP);
				} catch (CommandException e)
				{
					e.printStackTrace();
				}
			}

	        if (player.getActiveItemStack().isEmpty())
	        {
	            EnumHand enumhand = player.getActiveHand();
	            net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, enumhand);

	            if (enumhand == EnumHand.MAIN_HAND)
	            {
	                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
	            }
	            else
	            {
	                player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
	            }

	            player.resetActiveHand();
	            player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
	        }
			return true;
		}
		return false;
	}
	
	//Unlike with vanilla shield, damage is only reduced, not prevented
	public float getDamageBlocked(EntityLivingBase wielder, DamageSource source, float amount)
	{
		if (amount > 0.0F && canBlockDamageSource(source, wielder))
		{
			if (!source.isUnblockable())
			{
				int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.blockpower, wielder.getActiveItemStack());
				if (wielder instanceof EntityPlayer)
				{
					damageShield(amount, (EntityPlayer)wielder);
				}
				amount *= (1.0f - this.blockpower) / (1 + l);
				if (!source.isProjectile())
		        {
		            Entity entity = source.getImmediateSource();
		            if (entity instanceof EntityLivingBase)
		            {
		            	//Knocks attackers back like vanilla shield, but also damages them with reflection ench
		                ((EntityLivingBase) entity).knockBack(wielder, 0.5F, wielder.posX - entity.posX, wielder.posZ - entity.posZ);
		                if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.reflection, wielder.getActiveItemStack()) > 0)
							EnchantmentReflection.reflectDamage(entity, wielder);
		            }
		        }
			}
			//Works partially against magic too with spellshield ench
			else if (source.isMagicDamage())
			{
				int l = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.spellshield, wielder.getActiveItemStack());
				if (l > 0)
				{
					if (wielder instanceof EntityPlayer)
					{
						damageShield(amount, (EntityPlayer)wielder);
					}
					amount *= (1.0f - (this.blockpower * (l / 3.0F)));
				}
			}
	    }
		return amount;
	}
	
	//Copy of EntityPlayer.canBlockDamageSource with a few changes to work with custom shields
	public static boolean canBlockDamageSource(DamageSource damageSourceIn, EntityLivingBase entity)
	{
		if (entity.getActiveItemStack().getItem() instanceof ItemCustomShield)
	    {
	        Vec3d vec3d = damageSourceIn.getDamageLocation();

	        if (vec3d != null)
	        {
	            Vec3d vec3d1 = entity.getLook(1.0F);
	            Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(entity.posX, entity.posY, entity.posZ)).normalize();
	            vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

				return vec3d2.dotProduct(vec3d1) < 0.0D;
	        }
	    }
			
		return false;
	}
	
	void damageShield(float damage, EntityPlayer player)
	{
	    if (damage >= 3.0F && player.getActiveItemStack().getItem() instanceof ItemCustomShield)
	    {
	        ItemStack copyBeforeUse = player.getActiveItemStack().copy();
	        int i = 1 + MathHelper.floor(damage);
	        player.getActiveItemStack().damageItem(i, player);

	        if (player.getActiveItemStack().isEmpty())
	        {
	            EnumHand enumhand = player.getActiveHand();
	            net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, enumhand);

	            if (enumhand == EnumHand.MAIN_HAND)
	            {
	            	player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
	            }
	            else
	            {
	                player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
	            }

	            player.resetActiveHand();
	            player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
	        }
	    }
	}
	
	//Gives the player "Not today, thank you" advancement
	static void grantAdvancement(EntityPlayerMP playerMP) throws CommandException
	{
		AdvancementProgress progress = playerMP.getAdvancements().getProgress(AdvancementCommand.findAdvancement(playerMP.getServer(), "minecraft:story/deflect_arrow"));
		for (String s : progress.getRemaningCriteria())
        {
			playerMP.getAdvancements().grantCriterion(AdvancementCommand.findAdvancement(playerMP.getServer(), "minecraft:story/deflect_arrow"), s);
        }
	}
	
	@Override
	public int getItemEnchantability() {
		
		return 30 - 5 * this.weight;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.NONE;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return repair.getItem() == Items.IRON_INGOT;
	}
	
	public int getWeight()
	{
		return this.weight;
	}

}
