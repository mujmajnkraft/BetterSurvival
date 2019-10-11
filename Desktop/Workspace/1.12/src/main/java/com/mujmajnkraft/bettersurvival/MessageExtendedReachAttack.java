package com.mujmajnkraft.bettersurvival;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageExtendedReachAttack implements IMessage{

	private int entityId ;

    public MessageExtendedReachAttack() 
    { 
     // need this constructor
    }

    public MessageExtendedReachAttack(int parEntityId) 
    {
     entityId = parEntityId;
    }

    @Override
    public void fromBytes(ByteBuf buf) 
    {
     entityId = ByteBufUtils.readVarInt(buf, 4);
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
     ByteBufUtils.writeVarInt(buf, entityId, 4);
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
                          
                          if (thePlayer.getHeldItemMainhand().getItem() instanceof ICustomWeapon)
                          {
                              ICustomWeapon theExtendedReachWeapon = (ICustomWeapon)thePlayer.getHeldItemMainhand().getItem();
                              double reachSq = theExtendedReachWeapon.getReach()* theExtendedReachWeapon.getReach();
                              System.out.println(reachSq + " " + thePlayer.getDistanceSqToEntity(theEntity));
                              if (!thePlayer.canEntityBeSeen(theEntity))
                              {
                            	  reachSq /= 4.0d;
                              }
                              if (reachSq >= thePlayer.getDistanceSqToEntity(theEntity))
                              {
                            	  if (theEntity instanceof EntityItem || theEntity instanceof EntityXPOrb || theEntity instanceof EntityArrow || theEntity == thePlayer)
                                  {
                                      thePlayer.connection.disconnect(new TextComponentTranslation("multiplayer.disconnect.invalid_entity_attacked", new Object[0]));
                                      //this.serverController.logWarning("Player " + thePlayer.getName() + " tried to attack an invalid entity");
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
