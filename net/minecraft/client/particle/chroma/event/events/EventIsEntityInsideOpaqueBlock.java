package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.entity.Entity;

public class EventIsEntityInsideOpaqueBlock extends EventCancellable
{
	private Entity entity;
	
	public EventIsEntityInsideOpaqueBlock(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
}