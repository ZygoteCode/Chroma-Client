package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;

public class EventStep extends EventCancellable
{
	private EventState state;
	private float stepHeight;
	
	public EventStep(EventState state, float stepHeight)
	{
		this.state = state;
		this.stepHeight = stepHeight;
	}

	public EventState getState()
	{
		return state;
	}

	public void setState(EventState state)
	{
		this.state = state;
	}

	public float getStepHeight()
	{
		return stepHeight;
	}

	public void setStepHeight(float stepHeight)
	{
		this.stepHeight = stepHeight;
	}
}