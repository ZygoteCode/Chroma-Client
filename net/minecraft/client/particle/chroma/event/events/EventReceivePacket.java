package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.network.Packet;

public class EventReceivePacket extends EventCancellable
{
	private EventState state;
	private Packet packet;
	
	public EventReceivePacket(EventState state, Packet packet)
	{
		this.state = state;
		this.packet = packet;
	}

	public EventState getState()
	{
		return state;
	}

	public void setState(EventState state)
	{
		this.state = state;
	}

	public Packet getPacket()
	{
		return packet;
	}

	public void setPacket(Packet packet)
	{
		this.packet = packet;
	}
}