package com.mujmajnkraft.bettersurvival;

import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProwider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageNunchakuSpinServer implements IMessage {
	
	private boolean isSpinning;
	
	private int entityId;
	
	public MessageNunchakuSpinServer()
	{
	}
	
	public MessageNunchakuSpinServer(boolean b, int id)
	{
		this.isSpinning = b;
		this.entityId = id;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.isSpinning = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		buf.writeBoolean(isSpinning);
	}
	
	public static class Handler implements IMessageHandler<MessageNunchakuSpinServer, IMessage> 
	{
		@Override
	public IMessage onMessage(final MessageNunchakuSpinServer message, MessageContext ctx) 
	{
	    Minecraft.getMinecraft().addScheduledTask(
	          new Runnable()
	          {
	              @Override
	              public void run() 
	              {
	                	Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityId);
	                	if (entity instanceof EntityPlayer)
	                	{
	                		INunchakuCombo cap = entity.getCapability(NunchakuComboProwider.NUNCHAKUCOMBO_CAP, null);
		                	cap.setSpinning(message.isSpinning);
	                	}
	              }
	          });
		return null;
		}
	}

}
