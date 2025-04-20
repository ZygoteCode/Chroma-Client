package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.network.play.client.C03PacketPlayer;

public class VClipCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "vclip";
	}

	@Override
	public String getDescription()
	{
		return "Teleport up or down.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "vclip";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + Double.parseDouble(args[0]), mc.thePlayer.posZ);
		
		for (int i = 0; i < 3; i++)
		{
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
		}
	}
}