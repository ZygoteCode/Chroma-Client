package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.BlockPos;

public class EventSetOpaqueCube extends EventCancellable
{
	private BlockPos pos;
	
	public EventSetOpaqueCube(BlockPos pos)
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