package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.block.BlockCactus;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventBlockBounds;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.util.AxisAlignedBB;

public class AntiCactus extends Module
{
	public AntiCactus()
	{
		super("AntiCactus", 33, Category.PLAYER);
	}
	
	@EventTarget
	public void onBoundingBox(EventBlockBounds event)
	{
		if (mc.theWorld.getBlockState(event.getPos()).getBlock() instanceof BlockCactus)
		{
			event.setBox(new AxisAlignedBB(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getPos().getX() + 1.0, event.getPos().getY() + 1.0, event.getPos().getZ() + 1.0));
		}
	}
}