package net.minecraft.client.particle.chroma.module.fun;

import java.util.Random;

import net.minecraft.client.particle.chroma.compatibility.WMinecraft;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Derp extends Module
{
	private final Random random = new Random();

	public Derp()
	{
		super("Derp", 105, Category.FUN);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        float yaw = WMinecraft.getPlayer().rotationYaw + this.random.nextFloat() * 360.0f - 180.0f;
        float pitch = this.random.nextFloat() * 180.0f - 90.0f;
        event.setYaw(yaw);
		event.setPitch(pitch);
	}
}