package net.minecraft.client.particle.chroma.module.render;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSetOpaqueCube;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class CaveFinder extends Module
{
	public CaveFinder()
	{
		super("CaveFinder", 102, Category.RENDER);
	}
	
	@EventTarget
	public void onSetOpaqueCube(EventSetOpaqueCube event)
	{
		event.setCancelled(true);
	}
}