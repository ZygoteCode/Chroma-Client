package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;

public class EventUpdate extends EventCancellable
{
	private EventState state;
	private double posX;
	private double posY;
	private double posZ;
	private float yaw;
	private float pitch;
	private boolean onGround;
	private double motionX;
	private double motionY;
	private double motionZ;
	
	public EventUpdate(EventState state, double posX, double posY, double posZ, float yaw, float pitch, boolean onGround, double motionX, double motionY, double motionZ)
	{
		this.state = state;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public EventUpdate(EventState state)
	{
		this.state = state;
	}

	public EventState getState()
	{
		return state;
	}

	public void setState(EventState state)
	{
		this.state = state;
	}

	public double getPosX()
	{
		return posX;
	}

	public void setPosX(double posX)
	{
		this.posX = posX;
	}

	public double getPosY()
	{
		return posY;
	}

	public void setPosY(double posY)
	{
		this.posY = posY;
	}

	public double getPosZ()
	{
		return posZ;
	}

	public void setPosZ(double posZ)
	{
		this.posZ = posZ;
	}

	public float getYaw()
	{
		return yaw;
	}

	public void setYaw(float yaw)
	{
		this.yaw = yaw;
	}

	public float getPitch()
	{
		return pitch;
	}

	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}

	public boolean isOnGround()
	{
		return onGround;
	}

	public void setOnGround(boolean onGround)
	{
		this.onGround = onGround;
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
}