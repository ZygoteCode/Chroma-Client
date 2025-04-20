package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.command.Command;

public class UUIDSpoofCmd extends Command
{
	public static String UUIDSpoofStatus = "";
	public static boolean isPremium = false;
	public static String oldUUID = "";
	
	@Override
	public String getAlias()
	{
		return "uuidspoof";
	}

	@Override
	public String getDescription()
	{
		return "Spoof the UUID of the player to the wanted player.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "uuidspoof";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		String exec = args[0];
		
		if (exec.equalsIgnoreCase("set"))
		{
			String name = args[1];
			String premium = args[2].toLowerCase();
			isPremium = Boolean.parseBoolean(premium);
			UUIDSpoofStatus = name;
			oldUUID = mc.session.getPlayerID();
			Chroma.message("Succesfully set your §cUUID Spoof §7status.");
		}
		else if (exec.equalsIgnoreCase("del"))
		{
			UUIDSpoofStatus = "";
			isPremium = false;
			mc.session.setPlayerID(oldUUID);
			oldUUID = "";
			Chroma.message("Succesfully removed your §cUUID Spoof §7status.");
		}
	}
}