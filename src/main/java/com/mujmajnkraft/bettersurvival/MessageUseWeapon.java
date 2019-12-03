package com.mujmajnkraft.bettersurvival;

import com.mujmajnkraft.bettersurvival.entities.siegeweapons.EntitySiegeWeapon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUseWeapon implements IMessage{
	
	public MessageUseWeapon() 
    {
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}
	
	public static class Handler implements IMessageHandler<MessageUseWeapon, IMessage>
	{

		public IMessage onMessage(final MessageUseWeapon message, MessageContext ctx)
		{
			final EntityPlayerMP thePlayer = (EntityPlayerMP) ctx.getServerHandler().player;
            thePlayer.getServer().addScheduledTask(
                  new Runnable()
                  {
                      @Override
                      public void run() 
                      {
                          Entity theEntity = thePlayer.getRidingEntity();
                          
                          if (theEntity instanceof EntitySiegeWeapon && theEntity.getControllingPassenger().equals(thePlayer))
                          {
                        	  ((EntitySiegeWeapon)theEntity).performAction();
                          }
                      }
                  });
			return null;
		}
		
	}

}
