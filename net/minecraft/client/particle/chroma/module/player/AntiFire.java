package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiFire extends Module
{
	public AntiFire()
	{
		super("AntiFire", 88, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        if (mc.thePlayer.capabilities.isCreativeMode)
        {
            return;
        }
        
        if (!mc.thePlayer.onGround)
        {
            return;
        }
        
        if (!mc.thePlayer.isBurning())
        {
            return;
        }
        
        for (int i = 0; i < 3; i++)
        {
        	mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
        }
	}
}