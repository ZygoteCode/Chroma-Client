package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module
{
	private boolean sentPacket = false;
	private Timer timer;
	
	public NoFall()
	{
		super("NoFall", 11, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		timer = new Timer();
		ArrayList<String> options = new ArrayList<String>();
		options.add("Vanilla");
		options.add("SpoofGround");
		options.add("NoGround");
		options.add("AAC 3.3.8");
		options.add("AAC 3.3.11");
		options.add("AAC 3.3.15");
		options.add("AAC 3.4.2");
		this.getSetManager().rSetting(new Setting(14, "Mode", "The working mode of the NoFall module.", this, "Vanilla", options));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		sentPacket = false;
		timer.reset();
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		this.setSuffix(this.getModeColored());
		
		if (e.getState() == EventState.POST)
		{
			if (this.getMode().equalsIgnoreCase("AAC 3.4.2"))
			{
				if (mc.thePlayer.fallDistance > 2.5 && !sentPacket)
				{
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Double.NEGATIVE_INFINITY, mc.thePlayer.posZ, true));
					sentPacket = true;
				}
				else if (mc.thePlayer.onGround)
				{
					sentPacket = false;
				}
			}
			else if (this.getMode().equalsIgnoreCase("Vanilla"))
			{
				if (mc.thePlayer.fallDistance > 2)
				{
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
				}
			}
			else if (this.getMode().equalsIgnoreCase("AAC 3.3.8"))
			{
				if (mc.thePlayer.fallDistance > 0.5)
				{
					mc.thePlayer.motionY -= 256;
				}
			}
			else if (this.getMode().equalsIgnoreCase("AAC 3.3.11"))
			{
				if (mc.thePlayer.fallDistance > 2)
				{
		            this.mc.thePlayer.motionZ = 0.0;
		            this.mc.thePlayer.motionX = 0.0;
		            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.005, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
		            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
				}
			}
			else if (this.getMode().equalsIgnoreCase("AAC 3.3.15"))
			{
				if (mc.thePlayer.fallDistance > 2)
				{
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Double.NaN, mc.thePlayer.posZ, false));
					mc.thePlayer.fallDistance = -9999.0F;
				}
			}
		}
	}
	
	@EventTarget
	public void onPacket(EventSentPacket event)
	{
		if (event.getPacket() instanceof C03PacketPlayer)
		{
			C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
			
			if (this.getMode().equals("NoGround"))
			{
				packet.setOnGround(false);
			}
			
			if (this.getMode().equals("SpoofGround"))
			{
				packet.setOnGround(true);
			}
			
			event.setPacket(packet);
		}
	}
}