package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.entity.Entity;

public class EventEntityVelocity extends EventCancellable
{
	private Entity entity;
	private double x;
	private double y;
	private double z;
	
	public EventEntityVelocity(Entity entity, double x, double y, double z)
	{
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
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