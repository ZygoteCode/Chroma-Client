package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventGuiAction extends EventCancellable
{
	private int mouseX;
	private int mouseY;
	private int mouseButton;
	private GuiButton guiButton;
	private GuiScreen guiScreen;
	
	public EventGuiAction(int mouseX, int mouseY, int mouseButton, GuiButton guiButton, GuiScreen guiScreen)
	{
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.mouseButton = mouseButton;
		this.guiButton = guiButton;
		this.guiScreen = guiScreen;
	}

	public int getMouseX()
	{
		return mouseX;
	}

	public void setMouseX(int mouseX)
	{
		this.mouseX = mouseX;
	}

	public int getMouseY()
	{
		return mouseY;
	}

	public void setMouseY(int mouseY)
	{
		this.mouseY = mouseY;
	}

	public int getMouseButton()
	{
		return mouseButton;
	}

	public void setMouseButton(int mouseButton)
	{
		this.mouseButton = mouseButton;
	}

	public GuiButton getGuiButton()
	{
		return guiButton;
	}

	public void setGuiButton(GuiButton guiButton)
	{
		this.guiButton = guiButton;
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