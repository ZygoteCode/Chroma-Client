package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class AutoRespawn extends Module
{
	public AutoRespawn()
	{
		super("AutoRespawn", 15, Category.PLAYER);
	}
	
	@EventTarget
	public void onRespawn(EventUpdate event)
	{
		if (event.getState().equals(EventState.POST))
		{
			if (mc.thePlayer.isDead)
			{
				mc.displayGuiScreen(null);
				mc.thePlayer.respawnPlayer();
			}
		}
	}
}