package net.minecraft.client.particle.chroma.module.minigames;

import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Teams extends Module
{
	public Teams()
	{
		super("Teams", 116, Category.MINIGAMES);
	}
	
	public static boolean isInTeam(EntityLivingBase entity)
	{
		if (entity != null)
		{
			if (entity instanceof EntityPlayer)
			{
				String clientName = mc.thePlayer.getDisplayName().getFormattedText().replace("§r", "");
				String targetName = entity.getDisplayName().getFormattedText().replace("§r", "");
				return targetName.startsWith("§" + clientName.charAt(1));
			}
		}
		
		return false;
	}
}