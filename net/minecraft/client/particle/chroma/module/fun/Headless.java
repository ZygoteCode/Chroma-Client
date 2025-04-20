package net.minecraft.client.particle.chroma.module.fun;

import net.minecraft.client.particle.chroma.compatibility.WMinecraft;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Headless extends Module
{
	public Headless()
	{
		super("Headless", 106, Category.FUN);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		event.setPitch(180.0F);
	}
}