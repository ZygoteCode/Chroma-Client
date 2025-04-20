package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.Timer;

public class AutoUse extends Module
{
	private Timer timer;
	
	public AutoUse()
	{
		super("AutoUse", 71, Category.PLAYER);
		timer = new Timer();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
			mc.gameSettings.keyBindUseItem.pressed = false;
			
			if (timer.hasReach(150D))
			{			
				mc.rightClickMouse();
				timer.reset();
				
				if (mc.getChroma().getModuleManager().getModuleByID(66).isToggled())
				{
					for (int i = 0; i < 2; i++)
					{
						mc.rightClickMouse();
					}
				}
			}
		}
	}
}