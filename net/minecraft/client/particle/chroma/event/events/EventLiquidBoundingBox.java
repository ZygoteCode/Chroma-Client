package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EventLiquidBoundingBox extends EventCancellable
{
	private World world;
	private BlockPos pos;
	private IBlockState state;
	
	public EventLiquidBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		this.world = world;
		this.pos = pos;
		this.state = state;
	}

	public World getWorld()
	{
		return world;
	}

	public void setWorld(World world)
	{
		this.world = world;
	}

	public BlockPos getPos()
	{
		return pos;
	}

	public void setPos(BlockPos pos)
	{
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
}