package net.minecraft.client.particle.chroma.module.world;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class ForcePush extends Module
{
	public ForcePush()
	{
		super("ForcePush", 89, Category.WORLD);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (!mc.thePlayer.onGround)
		{
			return;
		}
		
        for (final Entity e : mc.theWorld.loadedEntityList)
        {
            if (e instanceof EntityLivingBase)
            {
                if (!isEntityValid(e))
                {
                    continue;
                }
                
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
	}
	
	public boolean isEntityValid(Entity entity)
	{
		if (!mc.thePlayer.onGround)
		{
			return false;
		}
		
		if (entity == null)
		{
			return false;
		}
		
		if (entity == mc.thePlayer)
		{
			return false;
		}

		if (FriendsManager.isFriend(entity.getName()))
		{
			return false;
		}
		
		if (mc.thePlayer.getDistanceToEntity(entity) > 1.0D)
		{
			return false;
		}
		
		if (!mc.thePlayer.canEntityBeSeen(entity))
		{
			return false;
		}
		
		return true;
	}
}