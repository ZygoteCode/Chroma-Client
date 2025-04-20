package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.utils.ClickType;

public class EventClickMouse extends EventCancellable
{
	private ClickType clickType;
	private EventState state;
	
	public EventClickMouse(EventState state, ClickType clickType)
	{
		this.clickType = clickType;
		this.state = state;
	}

	public ClickType getClickType()
	{
		return clickType;
	}

	public void setClickType(ClickType clickType)
	{
		this.clickType = clickType;
	}

	public EventState getState()
	{
		return state;
	}

	public void setState(EventState state)
	{
		this.state = state;
	}
}