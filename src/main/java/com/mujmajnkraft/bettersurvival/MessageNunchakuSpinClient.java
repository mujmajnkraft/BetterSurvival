package com.mujmajnkraft.bettersurvival;

import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageNunchakuSpinClient implements IMessage {
	
	private boolean isSpinning;
	
	public MessageNunchakuSpinClient()
	{
	}
	
	public MessageNunchakuSpinClient(boolean b)
	{
		this.isSpinning = b;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.isSpinning = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(isSpinning);
	}
	
	public static class Handler implements IMessageHandler<MessageNunchakuSpinClient, IMessage> 
	{
		@Override
	public IMessage onMessage(final MessageNunchakuSpinClient message, MessageContext ctx) 
	{
	    // Know it will be on the server so make it thread-safe
	    final EntityPlayerMP thePlayer = (EntityPlayerMP) ctx.getServerHandler().player;
	    thePlayer.getServer().addScheduledTask(
				() -> {
					  INunchakuCombo cap = thePlayer.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
					  cap.setSpinning(message.isSpinning);
					  if (!message.isSpinning) cap.setComboTime(0); //Stops combo if nunchaku stops spinning
					  BetterSurvivalPacketHandler.NETWORK.sendToAllTracking(new MessageNunchakuSpinServer(message.isSpinning, thePlayer.getEntityId()), thePlayer);
				});
		return null;
		}
	}

}
