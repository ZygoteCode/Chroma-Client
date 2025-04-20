package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class StaffCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "staff";
	}

	@Override
	public String getDescription()
	{
		return "See the staff of the current server.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "staff";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		String helper = "";
		String trainee = "";
		String mod = "";
		String srMod = "";
		String admin = "";
		String founder = "";
		String owner = "";
		String builder = "";
		
		for (Entity entity: mc.theWorld.loadedEntityList)
		{	
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;
				String formattedText = player.getDisplayName().getFormattedText().toLowerCase();
				
				String[] splitted = formattedText.split("\\s+");
				String prefix = splitted[0];
				
				if (prefix.startsWith("§"))
				{
					prefix = prefix.substring(2, prefix.length() - 2);
				}
				
				prefix = prefix.replace("[", "").replace("]", "");
		
				if (prefix == "helper")
				{
					if (helper == "")
					{
						helper = player.getName();
					}
					else
					{
						helper += ", " + player.getName();
					}
				}
				else if (prefix == "trainee")
				{
					if (trainee == "")
					{
						trainee = player.getName();
					}
					else
					{
						trainee += ", " + player.getName();
					}
				}
				else if (prefix == "mod" || prefix == "moderator")
				{
					if (mod == "")
					{
						mod = player.getName();
					}
					else
					{
						mod += ", " + player.getName();
					}
				}
				else if (prefix == "sr. mod")
				{
					if (srMod == "")
					{
						srMod = player.getName();
					}
					else
					{
						srMod += ", " + player.getName();
					}
				}
				else if (prefix == "admin")
				{
					if (admin == "")
					{
						admin = player.getName();
					}
					else
					{
						admin += ", " + player.getName();
					}
				}
				else if (prefix == "founder")
				{
					if (founder == "")
					{
						founder = player.getName();
					}
					else
					{
						founder += ", " + player.getName();
					}
				}
				else if (prefix == "owner")
				{
					if (owner == "")
					{
						owner = player.getName();
					}
					else
					{
						owner += ", " + player.getName();
					}
				}
				else if (prefix == "builder")
				{
					if (builder == "")
					{
						builder = player.getName();
					}
					else
					{
						builder += ", " + player.getName();
					}
				}
			}
		}
		
		if (helper == "" && trainee == "" && mod == "" && srMod == "" && admin == "" && founder == "" && owner == "" && builder == "")
		{
			Chroma.message("No staff has been found.");
		}
		else
		{
			if (helper == "")
			{
				helper = "No helper found.";
			}
			
			if (trainee == "")
			{
				trainee = "No trainee found.";
			}
			
			if (mod == "")
			{
				mod = "No moderator found.";
			}
			
			if (srMod == "")
			{
				srMod = "No sr. mod found.";
			}
			
			if (admin == "")
			{
				admin = "No admin found.";
			}
			
			if (founder == "")
			{
				founder = "No founder found.";
			}
			
			if (owner == "")
			{
				owner = "No owner found.";
			}
			
			if (builder == "")
			{
				builder = "No builder found.";
			}
			
			Chroma.message("Staff found on this server: ");
			Chroma.message("Helper: " + helper);
			Chroma.message("Trainee: " + trainee);
			Chroma.message("Moderator: " + mod);
			Chroma.message("Builder: " + builder);
			Chroma.message("Sr. Mod: " + srMod);
			Chroma.message("Admin: " + admin);
			Chroma.message("Founder: " + founder);
			Chroma.message("Owner: " + owner);
		}
	}
}