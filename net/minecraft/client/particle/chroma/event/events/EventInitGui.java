package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventInitGui extends EventCancellable
{
	private GuiScreen guiScreen;
	private int width;
	private int height;
	
	public EventInitGui(GuiScreen guiScreen, int width, int height)
	{
		this.guiScreen = guiScreen;
		this.width = width;
		this.height = height;
	}

	public GuiScreen getGuiScreen()
	{
		return guiScreen;
	}

	public void setGuiScreen(GuiScreen guiScreen)
	{
		this.guiScreen = guiScreen;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}