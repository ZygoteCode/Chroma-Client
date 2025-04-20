package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class AutoJump extends Module
{
	public AutoJump()
	{
		super("AutoJump", 40, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.POST))
		{
			if (mc.thePlayer.onGround)
			{
				mc.thePlayer.jump();
			}
		}
	}
}