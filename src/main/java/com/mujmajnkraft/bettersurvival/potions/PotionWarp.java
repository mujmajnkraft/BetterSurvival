package com.mujmajnkraft.bettersurvival.potions;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PotionWarp extends Potion {

	public PotionWarp(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName("Warp");
	}
	
	public boolean isInstant()
    {
        return true;
    }
	
	public boolean isReady(int duration, int amplifier)
    {
        return duration >= 1;
    }
	
	@Override
	public void performEffect(EntityLivingBase entityLiving, int p_76394_2_)
    {
		System.out.println("Performing effect");
		double TPdistance = 16.0D * (p_76394_2_+1);
		World worldIn = entityLiving.world;
		if (!worldIn.isRemote)
        {
            double d0 = entityLiving.posX;
            double d1 = entityLiving.posY;
            double d2 = entityLiving.posZ;

            for (int i = 0; i < 16; ++i)
            {
                double d3 = entityLiving.posX + (entityLiving.getRNG().nextDouble() - 0.5D) * TPdistance;
                double d4 = MathHelper.clamp(entityLiving.posY + (double)(entityLiving.getRNG().nextInt(16) - 8), 0.0D, (double)(worldIn.getActualHeight() - 1));
                double d5 = entityLiving.posZ + (entityLiving.getRNG().nextDouble() - 0.5D) * TPdistance;

                if (entityLiving.isRiding())
                {
                    entityLiving.dismountRidingEntity();
                }

                if (entityLiving.attemptTeleport(d3, d4, d5))
                {
                    worldIn.playSound((EntityPlayer)null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    entityLiving.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }
	
	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLiving, int amplifier, double health)
    {
		double TPdistance = 16.0D * (amplifier+1) * health;
		World worldIn = entityLiving.world;
		if (!worldIn.isRemote)
        {
            double d0 = entityLiving.posX;
            double d1 = entityLiving.posY;
            double d2 = entityLiving.posZ;

            for (int i = 0; i < 16; ++i)
            {
                double d3 = entityLiving.posX + (entityLiving.getRNG().nextDouble() - 0.5D) * TPdistance;
                double d4 = MathHelper.clamp(entityLiving.posY + (double)(entityLiving.getRNG().nextInt(16) - 8), 0.0D, (double)(worldIn.getActualHeight() - 1));
                double d5 = entityLiving.posZ + (entityLiving.getRNG().nextDouble() - 0.5D) * TPdistance;

                if (entityLiving.isRiding())
                {
                    entityLiving.dismountRidingEntity();
                }

                if (entityLiving.attemptTeleport(d3, d4, d5))
                {
                    worldIn.playSound((EntityPlayer)null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    entityLiving.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

}
