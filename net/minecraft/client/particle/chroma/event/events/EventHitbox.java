package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventHitbox extends EventCancellable
{
	private float hitbox;
	
	public EventHitbox(float hitbox)
	{
		this.hitbox = hitbox;
	}

	public float getHitbox()
	{
		return hitbox;
	}

	public void setHitbox(float hitbox)
	{
		this.hitbox = hitbox;
	}
}