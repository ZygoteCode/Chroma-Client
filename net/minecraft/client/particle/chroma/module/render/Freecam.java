package net.minecraft.client.particle.chroma.module.render;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.particle.chroma.compatibility.WConnection;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventBlockBounds;
import net.minecraft.client.particle.chroma.event.events.EventBlockCollide;
import net.minecraft.client.particle.chroma.event.events.EventEntityBoundingBox;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Freecam extends Module
{
    double x;
    double y;
    double z;
    private EntityOtherPlayerMP prayerCopy;
    private double startX;
    private double startY;
    private double startZ;
    private float startYaw;
    private float startPitch;
	
	public Freecam()
	{
		super("Freecam", 108, Category.RENDER);
	}
	
	@Override
	public void onDisable()
	{
        mc.thePlayer.setPosition(this.x, this.y, this.z);
        WConnection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.noClip = false;
		super.onDisable();
	}
	
	@Override
	public void onEnable()
	{
        this.x = mc.thePlayer.posX;
        this.y = mc.thePlayer.posY;
        this.z = mc.thePlayer.posZ;
		super.onEnable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.noClip = true;
        mc.thePlayer.capabilities.setFlySpeed(0.1f);
        event.setCancelled(true);
	}
	
	@EventTarget
	public void onSentPacket(EventSentPacket event)
	{
		if (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition || event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook)
		{
			event.cancel();
		}
	}
	
	@EventTarget
	public void onBoundingBox(EventEntityBoundingBox event)
	{
		event.cancel();
	}
	
	@EventTarget
	public void onBlockCollide(EventBlockBounds event)
	{
		event.cancel();
	}
}