package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.entity.Entity;

public class EventEntityMove extends EventCancellable
{
	private EventState state;
	private Entity entity;
	private double x;
	private double y;
	private double z;
	
	public EventEntityMove(EventState state, Entity entity, double x, double y, double z)
	{
		this.state = state;
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public EventState getState()
	{
		return state;
	}

	public void setState(EventState state)
	{
		this.state = state;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getZ()
	{
		return z;
	}

	public void setZ(double z)
	{
		this.z = z;
	}
}
