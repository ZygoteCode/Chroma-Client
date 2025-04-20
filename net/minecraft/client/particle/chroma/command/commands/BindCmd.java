package net.minecraft.client.particle.chroma.command.commands;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.client.particle.chroma.module.Module;

public class BindCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "bind";
	}

	@Override
	public String getDescription()
	{
		return "Change the keybind of a specific module.";
	}

	@Override
	public String getSyntax()
	{
		return "bind set [m] §7[k] | bind del [m] | bind clear";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		if (args[0].equalsIgnoreCase("set"))
		{
			args[2] = args[2].toUpperCase();
			int key = Keyboard.getKeyIndex(args[2]);
			
			for (Module m: Minecraft.getMinecraft().getChroma().getModuleManager().getModules())
			{
				if (args[1].equalsIgnoreCase(m.getName()))
				{
					m.setKey(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
					Minecraft.getMinecraft().getChroma().message("§7The module §a" + args[1] + " §7has been binded to the key §e" + args[2] + "§7.");
				}
			}		
		}
		else if (args[0].equalsIgnoreCase("del"))
		{
			for (Module m: Minecraft.getMinecraft().getChroma().getModuleManager().getModules())
			{
				if (m.getName().equalsIgnoreCase(args[1]))
				{
					m.setKey(0);
					Minecraft.getMinecraft().getChroma().message("§7The module §a" + args[1] + " §7has been unbinded.");
				}
			}
		}
		else if (args[0].equalsIgnoreCase("clear"))
		{
			for (Module m: Minecraft.getMinecraft().getChroma().getModuleManager().getModules())
			{
				m.setKey(0);
			}
			
			Minecraft.getMinecraft().getChroma().message("§7All keys have been cleared.");
		}
		else
		{
			Minecraft.getMinecraft().getChroma().message("§7Invalid command syntax. Correct syntax: §7" + getSyntax());
		}
	}
}