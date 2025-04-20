package net.minecraft.client.particle.chroma.friends;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.module.minigames.Teams;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public class FriendsManager
{
    public static ArrayList<Friend> friends;
    
    static
    {
        FriendsManager.friends = new ArrayList<Friend>();
    }
    
    public static void addFriend(final String name, final String alias)
    {
        FriendsManager.friends.add(new Friend(name, alias));
    }
    
    public static void removeFriend(final String name)
    {
        for (final Friend friend : FriendsManager.friends)
        {
            if (friend.getName().equalsIgnoreCase(name))
            {
                FriendsManager.friends.remove(friend);
                break;
            }
        }
    }
    
    public static boolean isFriend(final String name)
    {
        boolean isFriend = false;
        
        for (final Friend friend : FriendsManager.friends)
        {
            if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name)))
            {
                isFriend = true;
                break;
            }
        }
        
        if (Minecraft.getMinecraft().getChroma().getModuleManager().getModuleByID(65).isToggled())
        {
        	isFriend = false;
        }
        
        if (Minecraft.getMinecraft().getChroma().getModuleManager().getModuleByID(116).isToggled())
        {
        	try
        	{
        		for (Entity entity: Minecraft.getMinecraft().theWorld.loadedEntityList)
            	{
            		if (entity instanceof EntityPlayer)
            		{
            			if (Teams.isInTeam((EntityLivingBase) entity))
            			{
            				isFriend = true;
            			}
            		}
            	}
        	}
        	catch (Exception ex)
        	{
        		
        	}
        }
        
        return isFriend;
    }
}
