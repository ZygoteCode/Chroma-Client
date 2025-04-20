package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.particle.chroma.command.Command;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.client.particle.chroma.module.Module;

public class HelpCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "help";
	}

	@Override
	public String getDescription()
	{
		return "See the list of all available commands.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "help";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		int commands = 0;
		
		for (Command cmd: mc.getChroma().getCmdManager().getCommands())
		{
			commands++;
		}
		
		mc.getChroma().message("§7Here is the list of all available commands (§e" + Integer.toString(commands) + "§7):");
		
		for (Command cmd: mc.getChroma().getCmdManager().getCommands())
		{
			mc.getChroma().message("§7" + mc.getChroma().getCmdTrigger() + "§a" + cmd.getAlias() + "§7 - §7" + cmd.getDescription());
		}
	}
}