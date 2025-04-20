package net.minecraft.client.particle.chroma.module.combat;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class AutoLeave extends Module
{
	public AutoLeave()
	{
		super("AutoLeave", 68, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(211, "Health", "", this, 8.0D, 1.0D, 18.0D, true));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
	        if (mc.thePlayer.capabilities.isCreativeMode)
	        {
	            return;
	        }
	        
	        if (mc.isSingleplayer() || mc.thePlayer.sendQueue.getPlayerInfoMap().size() == 1)
	        {
	            return;
	        }
	        
	        if (mc.thePlayer.getHealth() > this.getSetManager().getSettingById(211).getValueD())
	        {
	            return;
	        }
	        
	        mc.theWorld.sendQuittingDisconnectingPacket();
		}
	}
}