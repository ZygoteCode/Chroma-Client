package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventReceivePacket;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotationSet extends Module
{
	public NoRotationSet()
	{
		super("NoRotationSet", 14, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onPacketReceive(EventReceivePacket event)
	{
		if (event.getPacket() instanceof S08PacketPlayerPosLook)
		{
			S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
			
			if (packet.getYaw() != mc.thePlayer.rotationYaw || packet.getPitch() != mc.thePlayer.rotationPitch)
			{
				event.setCancelled(true);
			}
		}
	}
}