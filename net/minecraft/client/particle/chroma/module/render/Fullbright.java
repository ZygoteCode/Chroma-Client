package net.minecraft.client.particle.chroma.module.render;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Fullbright extends Module
{
	public Fullbright()
	{
		super("Fullbright", 12, Category.RENDER);
	}
	
	@Override
	public void onEnable()
	{
		mc.renderGlobal.loadRenderers();
		super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
		mc.renderGlobal.loadRenderers();
		mc.gameSettings.gammaSetting = 0.5f;
		super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		if (e.getState() == EventState.POST)
		{
            if (mc.gameSettings.gammaSetting < 16.0f)
            {
                mc.gameSettings.gammaSetting += 0.5f;
            }
            else if (mc.gameSettings.gammaSetting > 0.5f)
            {
            	mc.gameSettings.gammaSetting = mc.gameSettings.gammaSetting < 1.0f ? 0.5f : (mc.gameSettings.gammaSetting -= 0.5f);
            } 
		}
	}
}