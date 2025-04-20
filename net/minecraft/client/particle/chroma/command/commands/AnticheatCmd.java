package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.command.Command;

public class AnticheatCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "anticheat";
	}

	@Override
	public String getDescription()
	{
		return "See the anticheat of the current server.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "anticheat";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		mc.getChroma().getModuleManager().getModuleByID(70).toggle();
	}
}