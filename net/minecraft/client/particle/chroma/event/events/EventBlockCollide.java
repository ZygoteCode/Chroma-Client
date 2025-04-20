package net.minecraft.client.particle.chroma.event.events;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBlockCollide extends EventCancellable
{
	private IBlockState iBlockState;
	private BlockPos.MutableBlockPos mutableBlockPos;
	private List<AxisAlignedBB> list;
	
	public EventBlockCollide(IBlockState iBlockState, BlockPos.MutableBlockPos mutableBlockPos, List<AxisAlignedBB> list)
	{
		this.iBlockState = iBlockState;
		this.mutableBlockPos = mutableBlockPos;
		this.list = list;
	}

	public IBlockState getiBlockState()
	{
		return iBlockState;
	}

	public void setiBlockState(IBlockState iBlockState)
	{
		this.iBlockState = iBlockState;
	}

	public BlockPos.MutableBlockPos getMutableBlockPos()
	{
		return mutableBlockPos;
	}

	public void setMutableBlockPos(BlockPos.MutableBlockPos mutableBlockPos)
	{
		this.mutableBlockPos = mutableBlockPos;
	}

	public List<AxisAlignedBB> getList()
	{
		return list;
	}

	public void setList(List<AxisAlignedBB> list)
	{
		this.list = list;
	}
}