package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Parkour extends Module
{
	public Parkour()
	{
		super("Parkour", 7, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState() == EventState.POST)
		{
	        if (mc.thePlayer.onGround && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindSneak.pressed && !mc.gameSettings.keyBindJump.pressed && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001)).isEmpty())
	        {
	            mc.thePlayer.jump();
	        }
		}
	}
}