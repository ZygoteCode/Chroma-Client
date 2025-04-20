package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class EventPlaceBlock extends EventCancellable
{
	private BlockPos hitPos;
	private EnumFacing side;
	private Vec3 hitVec;
	private float f;
	private float f1;
	private float f2;
	
	public EventPlaceBlock(BlockPos hitPos, EnumFacing side, Vec3 hitVec, float f, float f1, float f2)
	{
		this.hitPos = hitPos;
		this.hitVec = hitVec;
		this.side = side;
		this.f = f;
		this.f1 = f1;
		this.f2 = f2;
	}

	public BlockPos getHitPos()
	{
		return hitPos;
	}

	public void setHitPos(BlockPos hitPos)
	{
		this.hitPos = hitPos;
	}

	public EnumFacing getSide()
	{
		return side;
	}

	public void setSide(EnumFacing side)
	{
		this.side = side;
	}

	public Vec3 getHitVec()
	{
		return hitVec;
	}

	public void setHitVec(Vec3 hitVec)
	{
		this.hitVec = hitVec;
	}

	public float getF()
	{
		return f;
	}

	public void setF(float f)
	{
		this.f = f;
	}

	public float getF1()
	{
		return f1;
	}

	public void setF1(float f1)
	{
		this.f1 = f1;
	}

	public float getF2()
	{
		return f2;
	}

	public void setF2(float f2)
	{
		this.f2 = f2;
	}
}