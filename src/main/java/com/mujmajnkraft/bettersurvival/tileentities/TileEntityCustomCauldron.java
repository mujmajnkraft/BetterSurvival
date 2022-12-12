package com.mujmajnkraft.bettersurvival.tileentities;

import java.util.ArrayList;
import java.util.List;

import com.mujmajnkraft.bettersurvival.init.ModBlocks;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCustomCauldron extends TileEntity implements ITickable{
	
	public PotionType type = PotionTypes.EMPTY;
	public int color = 16253176;
	public List<PotionEffect> effects = new ArrayList<PotionEffect>();
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) 
	{
		return (oldState.getBlock() != newSate.getBlock());
	}
	
	@SideOnly(Side.CLIENT)
	public int getColor()
	{
		if (color == 16253176)
		{
			if (!effects.isEmpty())
			{
				return PotionUtils.getPotionColorFromEffectList(effects);
			}
			return PotionUtils.getPotionColor(this.type);
		}
		return color;
	}
	
	public boolean effectmatches(ItemStack stack)
	{
		boolean flag1 = true;
		boolean flag2 = true;
		if (stack.hasTagCompound())
		{
			NBTTagCompound compound = stack.getTagCompound();
			flag1 = (this.type == PotionUtils.getPotionTypeFromNBT(compound));
			if (!this.effects.isEmpty())
			{
				flag2 = (this.effects.equals(PotionUtils.getFullEffectsFromItem(stack)));
			}
		}
		boolean flag = flag1 && flag2;
		return flag;
	}
	
	public void setEffect(ItemStack stack)
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound compound = stack.getTagCompound();
			
			this.type = PotionUtils.getPotionTypeFromNBT(compound);
			this.effects = PotionUtils.getFullEffectsFromTag(compound);
			
			if (compound.hasKey("CustomPotionColor"))
			{
				this.color = compound.getInteger("CustomPotionColor");
			}
			
		}
		this.markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	public ItemStack getEffect(ItemStack stack)
	{
		PotionUtils.addPotionToItemStack(stack, type);
		
		if (!this.effects.isEmpty())
		{
			PotionUtils.appendEffects(stack, effects);
		}
		if (this.color != 16253176)
		{
			NBTTagCompound compound = stack.getTagCompound();
			compound.setInteger("CustomPotionColor", this.color);
		}
		this.markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		return stack;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		if (compound.hasKey("CustomPotionColor"))
		{
			this.color = compound.getInteger("CustomPotionColor");
		}
			
		this.type = PotionUtils.getPotionTypeFromNBT(compound);
		
		if (compound.hasKey("CustomPotionEffects"))
		{
			this.effects = PotionUtils.getFullEffectsFromTag(compound);
		}
    }
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
		super.writeToNBT(compound);
		if (this.color != 16253176)
		{
			compound.setInteger("CustomPotionColor", this.color);
		}
		if (this.type != PotionTypes.EMPTY)
		{
			ResourceLocation resourcelocation = (ResourceLocation)PotionType.REGISTRY.getNameForObject(type);
			compound.setString("Potion", resourcelocation.toString());
		}
		if (!this.effects.isEmpty())
		{
			NBTTagList nbttaglist = new NBTTagList();
			if (compound.hasKey("CustomPotionEffects"))
			{
				nbttaglist = (NBTTagList) compound.getTag("CustomPotionEffects");
			}
			else 
			{
				nbttaglist = new NBTTagList();
			}
			for (PotionEffect potioneffect : this.effects)
            {
                nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            compound.setTag("CustomPotionEffects", nbttaglist);
		}
		return compound;
    }
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		readUpdateTag(compound);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeUpdateTag(compound);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		writeUpdateTag(compound);
		return compound;
	}
	
	public void writeUpdateTag(NBTTagCompound compound) {
		
		if (this.color != 16253176)
		{
			compound.setInteger("CustomPotionColor", this.color);
		}
		if (this.type != PotionTypes.EMPTY)
		{
			ResourceLocation resourcelocation = (ResourceLocation)PotionType.REGISTRY.getNameForObject(type);
			compound.setString("Potion", resourcelocation.toString());
		}
		if (!this.effects.isEmpty())
		{
			NBTTagList nbttaglist = new NBTTagList();
			if (compound.hasKey("CustomPotionEffects"))
			{
				nbttaglist = (NBTTagList) compound.getTag("CustomPotionEffects");
			}
			else 
			{
				nbttaglist = new NBTTagList();
			}
			for (PotionEffect potioneffect : this.effects)
            {
                nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            compound.setTag("CustomPotionEffects", nbttaglist);
		}
	}
	
	public void readUpdateTag(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		if (compound.hasKey("CustomPotionColor"))
		{
			this.color = compound.getInteger("CustomPotionColor");
		}
			
		this.type = PotionUtils.getPotionTypeFromNBT(compound);
		
		if (compound.hasKey("CustomPotionEffects"))
		{
			 this.effects = PotionUtils.getFullEffectsFromTag(compound);
		}
		
	}

	@Override
	public void update()
	{
		if (this.getWorld().getBlockState(this.getPos()).getBlock() == ModBlocks.customcauldron)
		{
			if(this.getWorld().getBlockState(this.getPos()).getValue(BlockCauldron.LEVEL).intValue() == 0)
			{
				this.getWorld().setBlockState(pos, Blocks.CAULDRON.getDefaultState());
				this.markDirty();
			}
		}
	}

}