package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.entity.player.EntityPlayer;

public class EventPushWater extends EventCancellable
{
	private EntityPlayer player;
	
	public EventPushWater(EntityPlayer player)
	{
		this.player = player;
	}

	public EntityPlayer getPlayer()
	{
		return player;
	}

	public void setPlayer(EntityPlayer player)
	{
		this.player = player;
	}
}