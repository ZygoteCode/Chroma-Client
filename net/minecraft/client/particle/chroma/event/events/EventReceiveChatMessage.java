package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.IChatComponent;

public class EventReceiveChatMessage extends EventCancellable
{
	private IChatComponent component;
	
	public EventReceiveChatMessage(IChatComponent component)
	{
		this.component = component;
	}

	public IChatComponent getComponent()
	{
		return component;
	}

	public void setComponent(IChatComponent component)
	{
		this.component = component;
	}
}