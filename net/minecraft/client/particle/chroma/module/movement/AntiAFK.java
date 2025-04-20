package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.compatibility.WMath;
import net.minecraft.client.particle.chroma.compatibility.WMinecraft;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.Timer;

public class AntiAFK extends Module
{
	private Timer timer;
	
	public AntiAFK()
	{
		super("AntiAFK", 87, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		timer = new Timer();
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		timer.reset();
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (mc.thePlayer.isCollidedHorizontally)
		{
			mc.thePlayer.jump();
		}
		
		if (timer.hasReach(3000D))
		{
			if (timer.hasReach(6000D))
			{
				timer.reset();
				return;
			}
			
			mc.thePlayer.motionX = -0.1;
			mc.thePlayer.motionZ = -0.2;
			mc.thePlayer.setSprinting(true);
		}
		else
		{
			mc.thePlayer.motionX = 0.1;
			mc.thePlayer.motionZ = 0.2;
			mc.thePlayer.setSprinting(true);
		}
		
		if (mc.thePlayer.isCollidedHorizontally)
		{
			mc.thePlayer.jump();
		}
		
		if (event.getState().equals(EventState.PRE))
		{
	        float timer = (float)(WMinecraft.getPlayer().ticksExisted % 20) / 10.0f;
	        float pitch = WMath.sin(timer * 3.1415927f) * 90.0f;
	        event.setPitch(pitch);
		}
	}
}