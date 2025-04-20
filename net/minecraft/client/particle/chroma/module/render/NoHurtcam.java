package net.minecraft.client.particle.chroma.module.render;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventHurtcam;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class NoHurtcam extends Module
{
	public NoHurtcam()
	{
		super("NoHurtcam", 10, Category.RENDER);
	}
	
	@EventTarget
	public void onHurtcam(EventHurtcam event)
	{
		event.setCancelled(true);
	}
}