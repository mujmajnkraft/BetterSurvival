package com.mujmajnkraft.bettersurvival.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.init.ModPotionTypes;
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityCustomCauldron;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockCustomCauldron extends BlockCauldron implements ITileEntityProvider{
	
	public BlockCustomCauldron() {
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCustomCauldron();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        
        TileEntity TE = worldIn.getTileEntity(pos);
        if(TE instanceof TileEntityCustomCauldron) {
        	TileEntityCustomCauldron cauldron = (TileEntityCustomCauldron) TE;
        	
        	if(itemstack.isEmpty()) return true;
            else {
            	int i = state.getValue(LEVEL);
                Item item = itemstack.getItem();

                if(item == Items.POTIONITEM && (!PotionUtils.getEffectsFromStack(itemstack).isEmpty() || PotionUtils.getPotionFromItem(itemstack) != PotionTypes.WATER)) {
                	if(i < 3 && !worldIn.isRemote) {
                        if(!playerIn.capabilities.isCreativeMode) {
                            ItemStack itemstack2 = new ItemStack(Items.GLASS_BOTTLE);
                            playerIn.addStat(StatList.CAULDRON_USED);
                            playerIn.setHeldItem(hand, itemstack2);

                            if(playerIn instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                            }
                        }

                        worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if(i == 0) {
                        	cauldron.setEffect(itemstack);
                        	this.setWaterLevel(worldIn, pos, state, i + 1);
                        }
                        else if(cauldron.effectmatches(itemstack)) {
                        	cauldron.setEffect(itemstack);
                        	this.setWaterLevel(worldIn, pos, state, i + 1);
                        }
                        else {
                        	this.setWaterLevel(worldIn, pos, state, 0);
                        	worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        	if(worldIn instanceof WorldServer) {
                                WorldServer worldserver = (WorldServer)worldIn;
                                worldserver.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.getX(), pos.getY()+1.0D, pos.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            }
                        }
                    }

                    worldIn.markBlockRangeForRenderUpdate(pos, pos);
                    return true;
                }
                else if(item == Items.GLASS_BOTTLE) {
                	if(i > 0 && !worldIn.isRemote) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            ItemStack itemstack3 = cauldron.getEffect(new ItemStack(Items.POTIONITEM));
                            playerIn.addStat(StatList.CAULDRON_USED);
                            itemstack.shrink(1);

                            if(itemstack.isEmpty()) {
                                playerIn.setHeldItem(hand, itemstack3);
                            }
                            else if(!playerIn.inventory.addItemStackToInventory(itemstack3)) {
                                playerIn.dropItem(itemstack3, false);
                            }
                            else if(playerIn instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                            }
                        }

                        worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        this.setWaterLevel(worldIn, pos, state, i - 1);
                    }
                	
                	worldIn.markBlockRangeForRenderUpdate(pos, pos);
                	return true;
                }
                else if(item == Items.ARROW) {
                	if(i > 0 && !worldIn.isRemote) {
                		ItemStack itemstack3 = cauldron.getEffect(new ItemStack(Items.TIPPED_ARROW));
                        playerIn.addStat(StatList.CAULDRON_USED);
                        int c = Math.min(itemstack.getCount(), 8);
                        itemstack3.setCount(c);
                        itemstack.shrink(8);

                        if(itemstack.isEmpty()) {
                            playerIn.setHeldItem(hand, itemstack3);
                        }
                        else if(!playerIn.inventory.addItemStackToInventory(itemstack3)) {
                            playerIn.dropItem(itemstack3, false);
                        }
                        else if(playerIn instanceof EntityPlayerMP) {
                            ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                        }
                        
                        if(!playerIn.capabilities.isCreativeMode) {
                        	this.setWaterLevel(worldIn, pos, state, i - 1);
                        }
                        
                        worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                	
                	worldIn.markBlockRangeForRenderUpdate(pos, pos);
                	return true;
                }
                else if(!Arrays.asList(ForgeConfigHandler.server.paPotionBlacklist).contains(cauldron.type.getRegistryName().toString()) && ForgeConfigHandler.server.isClassInstanceofWhitelistedWeapon(item.getClass())) {
                	if(i > 0 && !worldIn.isRemote) {
						if(itemstack.hasTagCompound()) {
							int h = itemstack.getTagCompound().getInteger("remainingPotionHits");
							if(h > 0 && PotionUtils.getPotionFromItem(itemstack) == cauldron.type) {//Add doses if its the same potion instead of resetting
								itemstack.getTagCompound().setInteger("remainingPotionHits", Math.min(ForgeConfigHandler.server.potionHits + h, ForgeConfigHandler.server.maximumPotionHits));

								if(!playerIn.capabilities.isCreativeMode) this.setWaterLevel(worldIn, pos, state, i-1);
								worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

								return true;
							}
							itemstack.getTagCompound().removeTag("Potion");
							itemstack.getTagCompound().removeTag("CustomPotionEffects");
							itemstack.getTagCompound().removeTag("remainingPotionHits");
						}
                        else {
                            itemstack.setTagCompound(new NBTTagCompound());
                        }

						if(!(cauldron.type == PotionTypes.EMPTY ||
								cauldron.type == PotionTypes.WATER ||
								cauldron.type == PotionTypes.MUNDANE ||
								cauldron.type == PotionTypes.THICK ||
								cauldron.type == PotionTypes.AWKWARD)) {//If its water, just remove the effect and return it, to clean the sword
							PotionUtils.addPotionToItemStack(itemstack, cauldron.type);
							PotionUtils.appendEffects(itemstack, cauldron.effects);
							itemstack.getTagCompound().setInteger("remainingPotionHits", ForgeConfigHandler.server.potionHits);
						}

                		if(!playerIn.capabilities.isCreativeMode) {
                			this.setWaterLevel(worldIn, pos, state, i-1);
                		}

                		worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    }
                	return true;
                }
                else if (item == Items.MILK_BUCKET) {
                	if(i == 0 && !worldIn.isRemote) {
                		cauldron.type = ModPotionTypes.milk;
                		if(!playerIn.capabilities.isCreativeMode) {
                			playerIn.addStat(StatList.CAULDRON_USED);
                			playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                			if(playerIn instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                            }
                        }
            			
            			worldIn.playSound((EntityPlayer)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            			this.setWaterLevel(worldIn, pos, state, 3);
                	}
                	
                	worldIn.markBlockRangeForRenderUpdate(pos, pos);
                	return true;
                }
                else if(item == Items.BUCKET) {
                	if(i == 3 && !worldIn.isRemote && cauldron.type == ModPotionTypes.milk) {
                		if(!playerIn.capabilities.isCreativeMode) {
                			playerIn.addStat(StatList.CAULDRON_USED);
                			playerIn.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
                			if(playerIn instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                            }
                        }
            			
            			worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            			this.setWaterLevel(worldIn, pos, state, 0);
                	}
                	
                	worldIn.markBlockRangeForRenderUpdate(pos, pos);
                	return true;
                }
                else {
                	worldIn.markBlockRangeForRenderUpdate(pos, pos);
                	return false;
                }
            }
        }
        else {
        	worldIn.markBlockRangeForRenderUpdate(pos, pos);
        	return false;
        }
    }
	
	@Override
	public void fillWithRain(World worldIn, BlockPos pos)
	{
		//do nothing
	}
	
	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		int i = ((Integer)state.getValue(LEVEL)).intValue();
        float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;
        TileEntity TE = worldIn.getTileEntity(pos);
        boolean consumepotion = false;
        
        if (TE instanceof TileEntityCustomCauldron)
        {
        	if (!worldIn.isRemote && i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f)
        	{
        		if (entityIn instanceof EntityLivingBase)
        		{
        			TileEntityCustomCauldron cauldron = (TileEntityCustomCauldron) TE;
        			List<PotionEffect> effects = new ArrayList<PotionEffect>();
        			if (!cauldron.effects.isEmpty())
        			{
        				effects = cauldron.effects;
        			}
        			else if (cauldron.type != null)
        			{
        				effects = cauldron.type.getEffects();
        			}
        			EntityLivingBase living = (EntityLivingBase) entityIn;
        		
        			for (PotionEffect effect : effects)
        			{
        				if (!living.getActivePotionEffects().isEmpty())
        				{
        					boolean apply = true;
        					for (PotionEffect effect1 : living.getActivePotionEffects())
        					{
        						if (effect.getEffectName() == effect1.getEffectName() && effect.getAmplifier() >= effect1.getAmplifier())
        						{
        							apply = false;
        						}
        					}
        					if (apply)
        					{
        						living.addPotionEffect(new PotionEffect(effect));
        	                	consumepotion = true;
        					}
        				}
        				else 
        				{
        					living.addPotionEffect(new PotionEffect(effect));
                        	consumepotion = true;
        				}
        			}
        		}
        		
        		if (entityIn.isBurning())
            	{
                	entityIn.extinguish();
                	consumepotion = true;
            	}
        		
        		if (consumepotion)
        		{
        			this.setWaterLevel(worldIn, pos, state, i - 1);
        		}
        	}
        }
        worldIn.markBlockRangeForRenderUpdate(pos, pos);
	}

}