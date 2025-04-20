package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventKeyboard extends EventCancellable
{
	private int key;
	
	public EventKeyboard(int key)
	{
		this.key = key;
	}

	public int getKey()
	{
		return key;
	}

	public void setKey(int key)
	{
		this.key = key;
	}
}