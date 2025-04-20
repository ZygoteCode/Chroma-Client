package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.entity.Entity;

public class EventUpdateWater extends EventCancellable
{
	private Entity entity;
	private boolean isInWater;
	
	public EventUpdateWater(Entity entity, boolean isInWater)
	{
		this.entity = entity;
		this.isInWater = isInWater;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
	public boolean isInWater()
	{
		return isInWater;
	}
	public void setInWater(boolean isInWater)
	{
		this.isInWater = isInWater;
	}
}