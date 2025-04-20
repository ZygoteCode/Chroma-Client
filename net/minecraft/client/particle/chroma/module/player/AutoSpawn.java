package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AutoSpawn extends Module
{
	private Timer timer;
	
	public AutoSpawn()
	{
		super("AutoSpawn", 67, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		timer = new Timer();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE) && timer.hasReach(1500D))
		{
			timer.reset();
			
			Entity target = null;
			
	        for (final Entity e : mc.theWorld.loadedEntityList)
	        {
	            if (e instanceof EntityLivingBase)
	            {
	                if (!isEntityValid(e))
	                {
	                    continue;
	                }
	                
	                target = (EntityLivingBase) e;
	            }
	        }
	        
	        if (isEntityValid(target))
	        {
	        	mc.thePlayer.sendChatMessage("/spawn");
	        }
		}
	}
	
	public boolean isEntityValid(Entity entity)
	{
		if (entity == null)
		{
			return false;
		}
		
		if (entity == mc.thePlayer)
		{
			return false;
		}
		
		if (!(entity instanceof EntityPlayer))
		{
			return false;
		}
		
		if (!entity.isEntityAlive())
		{
			return false;
		}
		
		if (!mc.thePlayer.canEntityBeSeen(entity))
		{
			return false;
		}
		
		if (entity.isInvisible())
		{
			return false;
		}
		
		if (FriendsManager.isFriend(entity.getName()))
		{
			return false;
		}
		
		if (mc.thePlayer.getDistanceToEntity(entity) > 10.0D)
		{
			return false;
		}
			
		return true;
	}
}