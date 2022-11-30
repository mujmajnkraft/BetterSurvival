package com.mujmajnkraft.bettersurvival.integration;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;

import java.util.Arrays;

public class PotionTippedSwordRecipe implements ICauldronRecipe {
    @Override
    public boolean matches(ItemStack itemStack, boolean b, int i, CauldronState cauldronState) {
        return cauldronState.getPotion() != null &&
                i > 0 &&
                !Arrays.asList(ForgeConfigHandler.server.paPotionBlacklist).contains(cauldronState.getPotion().getRegistryName().toString()) &&
                ForgeConfigHandler.server.isClassInstanceofWhitelistedWeapon(itemStack.getItem().getClass());
    }

    @Override
    public ItemStack getResult(ItemStack itemStack, boolean b, int i, CauldronState cauldronState) {
        ItemStack returnable = itemStack.copy();

        if(returnable.hasTagCompound()) {
            itemStack.getTagCompound().removeTag("Potion");
            itemStack.getTagCompound().removeTag("CustomPotionEffects");
            itemStack.getTagCompound().removeTag("remainingPotionHits");
        }
        else returnable.setTagCompound(new NBTTagCompound());

        if(!cauldronState.isWater()) {//If its water, just remove the effect and return it, to clean the sword
            PotionUtils.addPotionToItemStack(returnable, cauldronState.getPotion());
            PotionUtils.appendEffects(returnable, cauldronState.getPotion().getEffects());
            returnable.getTagCompound().setInteger("remainingPotionHits", ForgeConfigHandler.server.potionHits);
        }

        return returnable;
    }

    @Override
    public int getLevel(int level) {
        return level-1;
    }

    @Override
    public ItemStack getContainer(ItemStack stack) {
        return ItemStack.EMPTY;
    }
}