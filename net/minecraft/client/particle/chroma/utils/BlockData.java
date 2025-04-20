package net.minecraft.client.particle.chroma.utils;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockData {

	public EnumFacing facing;
	public BlockPos blockpos;

	public BlockData(EnumFacing facing, BlockPos blockpos)
	{
		this.facing = facing;
		this.blockpos = blockpos;
	}
}