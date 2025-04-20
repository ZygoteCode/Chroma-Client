package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;

public class EventRenderChat extends EventCancellable
{
	private String chatText;
	
	public EventRenderChat(String chatText)
	{
		this.chatText = chatText;
	}

	public String getChatText()
	{
		return chatText;
	}

	public void setChatText(String chatText)
	{
		this.chatText = chatText;
	}
}