package net.minecraft.client.particle.chroma.module.fun;

import net.minecraft.client.particle.chroma.compatibility.WMath;
import net.minecraft.client.particle.chroma.compatibility.WMinecraft;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class HeadRoll extends Module
{
	public HeadRoll()
	{
		super("HeadRoll", 107, Category.FUN);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        float timer = (float)(WMinecraft.getPlayer().ticksExisted % 20) / 10.0f;
        float pitch = WMath.sin(timer * 3.1415927f) * 90.0f;
        event.setPitch(pitch);
	}
}