package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventSprint extends EventCancellable
{
	private boolean isSprinting;
	
	public EventSprint(boolean isSprinting)
	{
		this.isSprinting = isSprinting;
	}

	public boolean isSprinting()
	{
		return isSprinting;
	}

	public void setSprinting(boolean isSprinting)
	{
		this.isSprinting = isSprinting;
	}
}