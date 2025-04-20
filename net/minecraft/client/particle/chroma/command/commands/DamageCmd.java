package net.minecraft.client.particle.chroma.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.command.Command;
import net.minecraft.client.particle.chroma.module.exploit.Damage;

public class DamageCmd extends Command
{
	@Override
	public String getAlias()
	{
		return "damage";
	}

	@Override
	public String getDescription()
	{
		return "Take damage to the player.";
	}

	@Override
	public String getSyntax()
	{
		return mc.getChroma().getCmdTrigger() + "damage [damage]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception
	{
		try
		{
			int damage = Integer.parseInt(args[0]);
			Damage.doDamage(damage);
		}
		catch (Exception ex)
		{
			Damage.doDamage(mc.getChroma().getSetManager().getSettingById(296).getValueI());
		}
	}
}