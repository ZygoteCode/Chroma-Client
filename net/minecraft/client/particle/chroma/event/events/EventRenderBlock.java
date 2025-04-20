package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.BlockPos;

public class EventRenderBlock extends EventCancellable
{
	private BlockPos pos;
	
	public EventRenderBlock(BlockPos pos)
	{
		this.pos = pos;
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