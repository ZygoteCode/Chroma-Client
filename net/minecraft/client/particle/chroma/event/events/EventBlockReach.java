package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventBlockReach extends EventCancellable
{
	private float reach;
	
	public EventBlockReach(float reach)
	{
		this.reach = reach;
	}

	public float getReach()
	{
		return reach;
	}

	public void setReach(float reach)
	{
		this.reach = reach;
	}
}