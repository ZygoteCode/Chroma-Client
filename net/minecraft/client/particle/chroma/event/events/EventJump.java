package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventJump extends EventCancellable
{
	private double motionUpward;
	private double motionPotion;
	private float f;
	private double motionX;
	private double motionZ;
	
	public EventJump(double motionUpward, double motionPotion, float f, double motionX, double motionZ)
	{
		this.motionUpward = motionUpward;
		this.motionPotion = motionPotion;
		this.f = f;
		this.motionX = motionX;
		this.motionZ = motionZ;
	}

	public double getMotionUpward()
	{
		return motionUpward;
	}

	public void setMotionUpward(double motionUpward)
	{
		this.motionUpward = motionUpward;
	}

	public double getMotionPotion()
	{
		return motionPotion;
	}

	public void setMotionPotion(double motionPotion)
	{
		this.motionPotion = motionPotion;
	}

	public float getF()
	{
		return f;
	}

	public void setF(float f)
	{
		this.f = f;
	}

	public double getMotionX()
	{
		return motionX;
	}

	public void setMotionX(double motionX)
	{
		this.motionX = motionX;
	}

	public double getMotionZ()
	{
		return motionZ;
	}

	public void setMotionZ(double motionZ)
	{
		this.motionZ = motionZ;
	}
}