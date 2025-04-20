package net.minecraft.client.particle.chroma.command;

import net.minecraft.client.Minecraft;

public abstract class Command
{
	public abstract String getAlias();
	public abstract String getDescription();
	public abstract String getSyntax();
	public abstract void onCommand(String command, String[] args) throws Exception;
	
	public static final Minecraft mc = Minecraft.getMinecraft();
}