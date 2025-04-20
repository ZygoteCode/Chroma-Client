package net.minecraft.client.particle.chroma.command.commands;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.client.particle.chroma.friends.Friend;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Module;

public class FriendCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "friend";
	}

	@Override
	public String getDescription()
	{
		return "Manage all friends.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "friend add §7[n] | " + mc.getChroma().getCmdTrigger() + "friend del [n] | " + mc.getChroma().getCmdTrigger() + "friend clear | " + mc.getChroma().getCmdTrigger() + "friend get";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		if (args[0].equalsIgnoreCase("add"))
		{
			String s = args[1];
			
			if (FriendsManager.isFriend(s))
			{
				mc.getChroma().message("§7This player is already your friend.");
			}
			else
			{
				mc.getChroma().message("§7Succesfully added §a" + s + "§7 to your friends.");
				FriendsManager.addFriend(s, "");
			}
		}
		else if (args[0].equalsIgnoreCase("del"))
		{
			String s = args[1];
			
			if (!FriendsManager.isFriend(s))
			{
				mc.getChroma().message("§7This player is not your friend.");
			}
			else
			{
				mc.getChroma().message("§7Succesfully removed §a" + s + " §7from §7your friends.");
				FriendsManager.removeFriend(s);
			}
		}
		else if (args[0].equalsIgnoreCase("clear"))
		{
			if (FriendsManager.friends.isEmpty() || FriendsManager.friends == null)
			{
				mc.getChroma().message("§7There are no friends in your friends list.");
			}
			else		
			{
				FriendsManager.friends.clear();
				mc.getChroma().message("§7All your friends have been removed from your §7friends list.");
			}
		}
		else if (args[0].equalsIgnoreCase("get"))
		{
			if (FriendsManager.friends.isEmpty() || FriendsManager.friends == null)
			{
				mc.getChroma().message("§7There are no friends in your friends list.");
			}
			else		
			{
				mc.getChroma().message("§7Here are your friends:");
				
				for (Friend friend: FriendsManager.friends)
				{
					mc.getChroma().message("§7" + friend.getName());
				}
			}
		}
		else
		{
			Minecraft.getMinecraft().getChroma().message("§7Invalid command syntax. Correct syntax: §7" + getSyntax());
		}
	}
}