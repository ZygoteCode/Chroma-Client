package net.minecraft.client.particle.chroma.module.world;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class Ghost extends Module
{
	public Ghost()
	{
		super("Ghost", "Hides completely the hacked client from Minecraft.", Keyboard.KEY_ADD, Category.WORLD, false, 0);
		this.setInheritance(this.getClass());
	}
	
	@Override
	public void onEnable()
	{
		if (!Keyboard.isKeyDown(Keyboard.KEY_COMMA))
		{
			this.setToggled(false);
			return;
		}
		
		for (Module m: mc.getChroma().getModuleManager().getModules())
		{
			if (!(m.getId() == 0))
			{
				if (m.isToggled())
				{
					m.onToggle();
					m.onDisable();
				}
			}
		}
		
		super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
		if (!Keyboard.isKeyDown(Keyboard.KEY_COMMA))
		{
			return;
		}
		
		for (Module m: mc.getChroma().getModuleManager().getModules())
		{
			if (!(m.getId() == 0))
			{
				if (m.isToggled())
				{
					m.onToggle();
					m.onEnable();
				}
			}
		}
		
		super.onDisable();
	}
}