package net.minecraft.client.particle.chroma.module.other;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class AutoSwitch extends Module
{
	public AutoSwitch()
	{
		super("AutoSwitch", 90, Category.OTHER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		mc.thePlayer.inventory.currentItem = mc.thePlayer.inventory.currentItem == 8 ? 0 : ++mc.thePlayer.inventory.currentItem;
	}
}