package com.mujmajnkraft.bettersurvival.integration;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;

import java.util.Arrays;

public class PotionTippedSwordRecipe implements ICauldronRecipe {
    @Override
    public boolean matches(ItemStack itemStack, boolean b, int i, CauldronState cauldronState) {
        return cauldronState.getPotion() != null &&
                i > 0 &&
                !Arrays.asList(ForgeConfigHandler.potions.paPotionBlacklist).contains(cauldronState.getPotion().getRegistryName().toString()) &&
                ForgeConfigHandler.potions.isClassInstanceofWhitelistedWeapon(itemStack.getItem().getClass());
    }

    @Override
    public ItemStack getResult(ItemStack itemStack, boolean b, int i, CauldronState cauldronState) {
        ItemStack returnable = itemStack.copy();

        if(returnable.hasTagCompound()) {
            int h = returnable.getTagCompound().getInteger("remainingPotionHits");
            if(h > 0 && PotionUtils.getPotionFromItem(returnable) == cauldronState.getPotion()) {
                returnable.getTagCompound().setInteger("remainingPotionHits", Math.min(ForgeConfigHandler.potions.potionHits + h, ForgeConfigHandler.potions.maximumPotionHits));
                return returnable;//Add doses if its the same potion instead of resetting
            }
            returnable.getTagCompound().removeTag("Potion");
            returnable.getTagCompound().removeTag("CustomPotionEffects");
            returnable.getTagCompound().removeTag("remainingPotionHits");
        }
        else returnable.setTagCompound(new NBTTagCompound());

        if(!(cauldronState.isWater() ||
                cauldronState.getPotion() == PotionTypes.EMPTY ||
                cauldronState.getPotion() == PotionTypes.WATER ||
                cauldronState.getPotion() == PotionTypes.MUNDANE ||
                cauldronState.getPotion() == PotionTypes.THICK ||
                cauldronState.getPotion() == PotionTypes.AWKWARD)) {//If its water, just remove the effect and return it, to clean the sword
            PotionUtils.addPotionToItemStack(returnable, cauldronState.getPotion());
            PotionUtils.appendEffects(returnable, cauldronState.getPotion().getEffects());
            returnable.getTagCompound().setInteger("remainingPotionHits", ForgeConfigHandler.potions.potionHits);
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