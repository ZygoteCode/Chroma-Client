package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventEntityBoundingBox extends EventCancellable
{
	private float width;
	private float height;
	
	public EventEntityBoundingBox(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}
}