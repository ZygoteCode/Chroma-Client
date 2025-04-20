package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBlockBounds extends EventCancellable
{
	private BlockPos pos;
	private AxisAlignedBB box;
	
	public EventBlockBounds(BlockPos pos, AxisAlignedBB box)
	{
		this.pos = pos;
		this.box = box;
	}

	public BlockPos getPos()
	{
		return pos;
	}

	public void setPos(BlockPos pos)
	{
		this.pos = pos;
	}

	public AxisAlignedBB getBox()
	{
		return box;
	}

	public void setBox(AxisAlignedBB box)
	{
		this.box = box;
	}
}