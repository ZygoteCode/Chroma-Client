package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;

public class EventRender3D extends EventCancellable
{
	private EventState state;
	private float partialTicks;
	
	public EventRender3D(EventState state, float partialTicks)
	{
		this.state = state;
		this.partialTicks = partialTicks;
	}

	public EventState getState()
	{
		return state;
	}

	public void setState(EventState state)
	{
		this.state = state;
	}

	public float getPartialTicks()
	{
		return partialTicks;
	}

	public void setPartialTicks(float partialTicks)
	{
		this.partialTicks = partialTicks;
	}
}