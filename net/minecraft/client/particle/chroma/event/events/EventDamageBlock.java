package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class EventDamageBlock extends EventCancellable
{
	private BlockPos pos;
	private EnumFacing facing;
	
	public EventDamageBlock(BlockPos pos, EnumFacing facing)
	{
		this.pos = pos;
		this.facing = facing;
	}

	public BlockPos getPos()
	{
		return pos;
	}

	public void setPos(BlockPos pos)
	{
		this.pos = pos;
	}

	public EnumFacing getFacing()
	{
		return facing;
	}

	public void setFacing(EnumFacing facing)
	{
		this.facing = facing;
	}
}