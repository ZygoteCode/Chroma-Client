package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventShouldSideBeRendered extends EventCancellable
{
	private boolean b;
	private IBlockState state;
	
	public EventShouldSideBeRendered(boolean b, IBlockState state)
	{
		this.b = b;
		this.state = state;
	}

	public boolean isB()
	{
		return b;
	}

	public void setB(boolean b)
	{
		this.b = b;
	}

	public IBlockState getState()
	{
		return state;
	}

	public void setState(IBlockState state)
	{
		this.state = state;
	}
}