package com.mujmajnkraft.bettersurvival;
/*
import com.mujmajnkraft.bettersurvival.tileentities.TileEntityWorkshop;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageButtonPressed implements IMessage{

	public int ID;
	
	public MessageButtonPressed()
	{}
	
	public MessageButtonPressed(int ID)
	{
		this.ID = ID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.ID = ByteBufUtils.readVarInt(buf, 4);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeVarInt(buf, ID, 4);
	}
	
	public static class Handler implements IMessageHandler<MessageButtonPressed, IMessage>
	{

		@Override
		public IMessage onMessage(MessageButtonPressed message, MessageContext ctx)
		{
			final EntityPlayer player = ctx.getServerHandler().player;
			if (player.openContainer instanceof ContainerWorkshop)
			{
				TileEntityWorkshop workshop = ((ContainerWorkshop)player.openContainer).TE;
				if (workshop.isUsableByPlayer(player))
				{
					if (message.ID == 0)
					{
						System.out.println("Sending MessageButtonPressed");
						workshop.startCrafting();
					}
				}
			}
			return null;
		}
	}

}*/
