package net.minecraft.client.particle.chroma.module.render;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class NoScoreboard extends Module
{
	public NoScoreboard()
	{
		super("NoScoreboard", 17, Category.RENDER);
	}
}