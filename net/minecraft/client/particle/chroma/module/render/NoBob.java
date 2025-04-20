package net.minecraft.client.particle.chroma.module.render;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class NoBob extends Module
{
	public NoBob()
	{
		super("NoBob", 37, Category.RENDER);
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate event)
	{
        this.mc.gameSettings.viewBobbing = true;
        this.mc.thePlayer.distanceWalkedModified = 0.0f;
	}
}