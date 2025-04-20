package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class NoPitchLimit extends Module
{
	public NoPitchLimit()
	{
		super("NoPitchLimit", 36, Category.PLAYER);
	}
	
    @Override
    public void onDisable()
    {
        if (this.mc.thePlayer.rotationPitch >= 90.0f)
        {
            this.mc.thePlayer.rotationPitch = 90.0f;
        }
        else if (this.mc.thePlayer.rotationPitch <= -90.0f)
        {
            this.mc.thePlayer.rotationPitch = -90.0f;
        }
        
        super.onDisable();
    }

    @EventTarget
    public void onPre(EventUpdate event)
    {
    	if (event.getState().equals(EventState.PRE))
    	{
            if (this.mc.thePlayer.rotationPitch >= 90.0f)
            {
                event.setPitch(90.0F);
            }
            else if (this.mc.thePlayer.rotationPitch <= -90.0f)
            {
            	event.setPitch(-90.0F);
            }
    	}
    }
}