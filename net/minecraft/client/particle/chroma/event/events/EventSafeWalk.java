package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;

public class EventSafeWalk extends EventCancellable
{
	private EventState state;
	private boolean protectGround;
	private boolean protectJump;
	
	public EventSafeWalk(EventState state, boolean protectGround, boolean protectJump)
	{
		this.state = state;
		this.protectGround = protectGround;
		this.protectJump = protectJump;
	}
	
	public EventSafeWalk(EventState state)
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

	public boolean isProtectGround()
	{
		return protectGround;
	}

	public void setProtectGround(boolean protectGround)
	{
		this.protectGround = protectGround;
	}

	public boolean isProtectJump()
	{
		return protectJump;
	}

	public void setProtectJump(boolean protectJump)
	{
		this.protectJump = protectJump;
	}
}