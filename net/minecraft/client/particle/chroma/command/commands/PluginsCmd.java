package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.particle.chroma.command.Command;

public class PluginsCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "plugins";
	}

	@Override
	public String getDescription()
	{
		return "Allows you to view servers' plugins.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "plugins";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		mc.getChroma().getModuleManager().getModuleByID(123).toggle();
	}
}