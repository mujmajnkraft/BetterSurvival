package com.mujmajnkraft.bettersurvival;

import com.mujmajnkraft.bettersurvival.items.ItemSpear;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageExtendedReachAttack implements IMessage{

	private int entityId ;

    public MessageExtendedReachAttack() 
    {
    }

    public MessageExtendedReachAttack(int parEntityId) 
    {
    	this.entityId = parEntityId;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
    	entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
    	buf.writeInt(entityId);
    }

    public static class Handler implements IMessageHandler<MessageExtendedReachAttack, 
          IMessage> 
    {
        @Override
        public IMessage onMessage(final MessageExtendedReachAttack message, MessageContext ctx) 
        {
            // Know it will be on the server so make it thread-safe
            final EntityPlayerMP thePlayer = (EntityPlayerMP) ctx.getServerHandler().player;
            thePlayer.getServer().addScheduledTask(
                  new Runnable()
                  {
                      @Override
                      public void run() 
                      {
                          Entity theEntity = thePlayer.getEntityWorld().getEntityByID(message.entityId);
                          
                          if (thePlayer.getHeldItemMainhand().getItem() instanceof ItemSpear)
                          {
                              float reach = ((ItemSpear)thePlayer.getHeldItemMainhand().getItem()).reach;
                              double reachSq = reach * reach;
                              if (!thePlayer.canEntityBeSeen(theEntity))
                              {
                            	  reachSq /= 4.0d;
                              }
                              if (reachSq >= thePlayer.getDistanceSq(theEntity))
                              {
                            	  if (theEntity instanceof EntityItem || theEntity instanceof EntityXPOrb || theEntity instanceof EntityArrow || theEntity == thePlayer)
                                  {
                                      thePlayer.connection.disconnect(new TextComponentTranslation("multiplayer.disconnect.invalid_entity_attacked", new Object[0]));
                                      return;
                                  }

                                  thePlayer.attackTargetEntityWithCurrentItem(theEntity);
                              }
                          }
                          return; // no response in this case
                      }
                }
            );
            return null; // no response message
        }
    }
}
