package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class AutoWalk extends Module
{
	public AutoWalk()
	{
		super("AutoWalk", 41, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.POST))
		{
			if (!mc.gameSettings.keyBindForward.pressed)
			{
				mc.gameSettings.keyBindForward.pressed = true;
			}
		}
	}
	
	@Override
	public void onDisable()
	{
		if (mc.gameSettings.keyBindForward.pressed)
		{
			mc.gameSettings.keyBindForward.pressed = false;
		}
		
		super.onDisable();
	}
}