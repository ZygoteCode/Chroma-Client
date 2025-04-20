package net.minecraft.client.particle.chroma.module.fun;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Astronaut extends Module
{
	public Astronaut()
	{
		super("Astronaut", 109, Category.FUN);
	}
	
	@Override
	public void onDisable()
	{
		mc.timer.timerSpeed = 1.0F;
		super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		event.setPitch(mc.thePlayer.ticksExisted % 100);
		mc.timer.timerSpeed = 0.25F;
		
		if (mc.thePlayer.motionY > 0.0)
		{
			mc.timer.timerSpeed = 0.2F;
		}
	}
}