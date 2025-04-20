package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.BlockPos;

public class EventRenderBlockModel extends EventCancellable
{
	private IBlockState state;
	private BlockPos pos;
	
	public EventRenderBlockModel(IBlockState state, BlockPos pos)
	{
		this.state = state;
		this.pos = pos;
	}

	public IBlockState getState()
	{
		return state;
	}

	public void setState(IBlockState state) 
	{
		this.state = state;
	}

	public BlockPos getPos() 
	{
		return pos;
	}

	public void setPos(BlockPos pos)
	{
		this.pos = pos;
	}
}