package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.entity.Entity;

public class EventEntityReach extends EventCancellable
{
	private float distance;
	private Entity basicEntity;
	private Entity targetEntity;
	
	public EventEntityReach(float distance, Entity basicEntity, Entity targetEntity)
	{
		this.distance = distance;
		this.basicEntity = basicEntity;
		this.targetEntity = targetEntity;
	}

	public float getDistance()
	{
		return distance;
	}

	public void setDistance(float distance)
	{
		this.distance = distance;
	}

	public Entity getBasicEntity()
	{
		return basicEntity;
	}

	public void setBasicEntity(Entity basicEntity)
	{
		this.basicEntity = basicEntity;
	}

	public Entity getTargetEntity()
	{
		return targetEntity;
	}

	public void setTargetEntity(Entity targetEntity)
	{
		this.targetEntity = targetEntity;
	}
}