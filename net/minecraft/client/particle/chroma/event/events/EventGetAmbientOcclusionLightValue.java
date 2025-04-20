package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventGetAmbientOcclusionLightValue extends EventCancellable
{
	private float f;
	private IBlockState state;
	
	public EventGetAmbientOcclusionLightValue(float f, IBlockState state)
	{
		this.f = f;
		this.state = state;
	}

	public float getF()
	{
		return f;
	}

	public void setF(float f)
	{
		this.f = f;
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
