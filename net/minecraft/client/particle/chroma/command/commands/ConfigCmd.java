package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.network.play.client.C03PacketPlayer;

public class ConfigCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "config";
	}

	@Override
	public String getDescription()
	{
		return "Manage the config system.";
	}

	@Override
	public String getSyntax()
	{
		return "config load [name] | config create [name] | config delete [name] | config help";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		if (args[0].equalsIgnoreCase("help"))
		{
			Chroma.message("Here is a list of commands to manage the config system:");
			Chroma.message(mc.getChroma().getCmdTrigger() + "config load [name]");
			Chroma.message(mc.getChroma().getCmdTrigger() + "config create [name]");
			Chroma.message(mc.getChroma().getCmdTrigger() + "config delete [name]");
			Chroma.message(mc.getChroma().getCmdTrigger() + "config help");
		}
		else if (args[0].equalsIgnoreCase("load"))
		{
			String configName = args[1];
			mc.getChroma().loadConfig(configName);
			Chroma.message("Succesfully loaded §a" + configName + " §7config.");
		}
		else if (args[0].equalsIgnoreCase("delete"))
		{
			String configName = args[1];
			mc.getChroma().deleteConfig(configName);
			Chroma.message("Succesfully deleted §a" + configName + " §7config.");
		}
		else if (args[0].equalsIgnoreCase("create"))
		{
			String configName = args[1];
			mc.getChroma().createConfig(configName);
			Chroma.message("Succesfully created §a" + configName + " §7config.");
		}
	}
}