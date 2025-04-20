package net.minecraft.client.particle.chroma.module.world;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class Panic extends Module
{
	public Panic()
	{
		super("Panic", "Disable all modules.", Keyboard.KEY_BACKSLASH, Category.WORLD, false, 1);
		this.setInheritance(this.getClass());
	}
	
	@Override
	public void onEnable()
	{
		this.setToggled(false);
		
		for (Module m: mc.getChroma().getModuleManager().getModules())
		{
			if (m.isToggled())
			{
				m.toggle();
			}
		}
		
		super.onEnable();
	}
}