package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventDisplayScreen extends EventCancellable
{
	private GuiScreen guiScreen;
	
	public EventDisplayScreen(GuiScreen guiScreen)
	{
		this.guiScreen = guiScreen;
	}

	public GuiScreen getGuiScreen()
	{
		return guiScreen;
	}

	public void setGuiScreen(GuiScreen guiScreen)
	{
		this.guiScreen = guiScreen;
	}
}