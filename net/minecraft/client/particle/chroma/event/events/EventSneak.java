package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventSneak extends EventCancellable
{
	private boolean isSneaking;
	
	public EventSneak(boolean isSneaking)
	{
		this.isSneaking = isSneaking;
	}

	public boolean isSneaking()
	{
		return isSneaking;
	}

	public void setSneaking(boolean isSneaking)
	{
		this.isSneaking = isSneaking;
	}
}