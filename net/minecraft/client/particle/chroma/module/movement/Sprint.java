package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSprint;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Sprint extends Module
{
	public Sprint()
	{
		super("Sprint", 5, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isCollidedVertically && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInWeb && !mc.thePlayer.isInLava() && mc.thePlayer.moveForward > 0.0F && !(mc.thePlayer.getFoodStats().getFoodLevel() < 8))
		{
			mc.thePlayer.setSprinting(true);
		}
	}
	
	@EventTarget
	public void onSprint(EventSprint event)
	{
		/*if (!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isCollidedVertically && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInWeb &&!mc.thePlayer.isInLava() && mc.thePlayer.moveForward > 0.0F && !(mc.thePlayer.getFoodStats().getFoodLevel() < 8))
		{
			event.setSprinting(true);
		}*/
	}
}