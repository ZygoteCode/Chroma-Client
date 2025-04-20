package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.client.particle.chroma.module.Module;

public class ToggleCmd extends Command
{
	@Override
	public String getAlias() 
	{
		return "toggle";
	}

	@Override
	public String getDescription()
	{
		return "Toggle a module.";
	}

	@Override
	public String getSyntax() 
	{
		return mc.getChroma().getCmdTrigger() + "toggle [m]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		boolean found = false;
		
		for (Module m: Minecraft.getMinecraft().getChroma().getModuleManager().getModules())
		{
			if (args[0].equalsIgnoreCase(m.getName()))
			{
				m.toggle();
				found = true;
				Minecraft.getMinecraft().getChroma().message("§a" + m.getName() + " §7was toggled.");
			}
		}
		
		if (found == false)
		{
			Minecraft.getMinecraft().getChroma().message("§7This module was not found.");
		}
	}
}