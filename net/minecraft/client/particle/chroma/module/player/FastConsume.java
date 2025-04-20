package net.minecraft.client.particle.chroma.module.player;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastConsume extends Module
{
    private Random random = new Random();
    private boolean send;
    private Timer timer;
	
	public FastConsume()
	{
		super("FastConsume", 64, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		timer = new Timer();
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("NCP");
		modes.add("AAC");
		modes.add("Vanilla");
		this.getSetManager().rSetting(new Setting(206, "Mode", "", this, "NCP", modes));
		super.setup();
	}

	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
	        if (this.mc.thePlayer.isEating() && !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow))
	        {
	            if (this.send && this.timer.hasReach(1000 + this.random.nextInt(100)))
	            {
	                for (int i = 0; i < (this.getMode().equals("NCP") ? 17 : (this.getMode().equals("AAC") ? 6 : 50)); ++i)
	                {
	                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	                }
	                
	                this.send = false;
	                this.timer.reset();
	            }
	        }
	        else
	        {
	            this.send = true;
	        }
		}
	}
}