package net.minecraft.client.particle.chroma.module.fun;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Tired extends Module
{
	public Tired()
	{
		super("Tired", 92, Category.FUN);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		event.setPitch(mc.thePlayer.ticksExisted % 100);
		
		if (mc.thePlayer.moveForward != 0.0F)
		{
			mc.thePlayer.motionX = 0.0D;
			mc.thePlayer.motionZ = 0.0D;
		}
	}
}