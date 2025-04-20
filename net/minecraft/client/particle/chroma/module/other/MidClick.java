package net.minecraft.client.particle.chroma.module.other;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventClickMouse;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.ClickType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MidClick extends Module
{
	public MidClick()
	{
		super("MidClick", 72, Category.OTHER);
	}
	
	@EventTarget
	public void onMouseClick(EventClickMouse event)
	{
		if (event.getClickType().equals(ClickType.MIDDLE))
		{
			if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null)
			{
				if (mc.objectMouseOver.entityHit instanceof EntityPlayer)
				{
					if (isEntityValid(mc.objectMouseOver.entityHit))
					{
						EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
						
						if (FriendsManager.isFriend(entityPlayer.getName()))
						{
							FriendsManager.removeFriend(entityPlayer.getName());
							Chroma.message("Succesfully removed §a" + entityPlayer.getName() + " §7from friends.");
						}
						else
						{
							FriendsManager.addFriend(entityPlayer.getName(), "");
							Chroma.message("Succesfully added §a" + entityPlayer.getName() + " §7to your friends.");
						}
					}
				}
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
		
		if (mc.thePlayer.getDistanceToEntity(entity) > 6.0D)
		{
			return false;
		}
			
		return true;
	}
}