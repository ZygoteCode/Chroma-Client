package net.minecraft.client.particle.chroma.command;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.command.commands.*;

public class CommandManager
{
	private ArrayList<Command> commands;
	
	public CommandManager()
	{
		commands = new ArrayList();
		addCommand(new BindCmd());
		addCommand(new ToggleCmd());
		addCommand(new FriendCmd());
		addCommand(new HelpCmd());
		addCommand(new UUIDSpoofCmd());
		addCommand(new VClipCmd());
		addCommand(new StaffCmd());
		addCommand(new AnticheatCmd());
		addCommand(new DamageCmd());
		addCommand(new PluginsCmd());
		addCommand(new ConfigCmd());
	}
	
	public void addCommand(Command c)
	{
		commands.add(c);
	}
	
	public ArrayList<Command> getCommands()
	{
		return commands;
	}
	
	public void callCommand(String input)
	{
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		
		for (Command c: getCommands())
		{
			if (c.getAlias().equalsIgnoreCase(command))
			{
				try
				{
					c.onCommand(args, args.split(" "));
				} 
				catch (Exception e)
				{
					Minecraft.getMinecraft().getChroma().message("§7Invalid command syntax. Correct syntax: §7" + c.getSyntax());
				}
				
				return;
			}
		}
		
		Minecraft.getMinecraft().getChroma().message("§7Unrecognized command. §7Type " + Minecraft.getMinecraft().getChroma().getCmdTrigger() + "§7help for the §7list of all §7commands.");
		
		try
		{
			Minecraft.getMinecraft().getChroma().endClient();
		}
		catch (IllegalArgumentException e)
		{
			
		}
		catch (IllegalAccessException e)
		{

			
		}
		catch (IOException e)
		{
			
		}	
	}
}