package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.tileentity.TileEntity;

public class EventRenderTileEntity extends EventCancellable
{
	private TileEntity tileEntity;
	private float partialTicks;
	private int destroyStage;
	
	public EventRenderTileEntity(TileEntity tileEntity, float partialTicks, int destroyStage)
	{
		this.tileEntity = tileEntity;
		this.partialTicks = partialTicks;
		this.destroyStage = destroyStage;
	}

	public TileEntity getTileEntity()
	{
		return tileEntity;
	}

	public void setTileEntity(TileEntity tileEntity)
	{
		this.tileEntity = tileEntity;
	}

	public float getPartialTicks()
	{
		return partialTicks;
	}

	public void setPartialTicks(float partialTicks)
	{
		this.partialTicks = partialTicks;
	}

	public int getDestroyStage()
	{
		return destroyStage;
	}

	public void setDestroyStage(int destroyStage)
	{
		this.destroyStage = destroyStage;
	}
}