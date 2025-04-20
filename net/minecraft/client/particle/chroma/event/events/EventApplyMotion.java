package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventApplyMotion extends EventCancellable
{
	private double motionX;
	private double motionY;
	private double motionZ;
	
	public EventApplyMotion(double motionX, double motionY, double motionZ)
	{
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	public double getMotionX()
	{
		return motionX;
	}

	public void setMotionX(double motionX)
	{
		this.motionX = motionX;
	}

	public double getMotionY()
	{
		return motionY;
	}

	public void setMotionY(double motionY)
	{
		this.motionY = motionY;
	}

	public double getMotionZ()
	{
		return motionZ;
	}

	public void setMotionZ(double motionZ)
	{
		this.motionZ = motionZ;
	}
	
	public double getX()
	{
		return motionX;
	}

	public void setX(double motionX)
	{
		this.motionX = motionX;
	}

	public double getY()
	{
		return motionY;
	}

	public void setY(double motionY)
	{
		this.motionY = motionY;
	}

	public double getZ()
	{
		return motionZ;
	}

	public void setZ(double motionZ)
	{
		this.motionZ = motionZ;
	}
	
	public void zeroXZ()
	{
		this.motionX = 0.0D;
		this.motionZ = 0.0D;
	}
}