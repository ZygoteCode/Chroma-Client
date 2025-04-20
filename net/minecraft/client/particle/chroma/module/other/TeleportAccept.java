package net.minecraft.client.particle.chroma.module.other;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventReceiveChatMessage;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class TeleportAccept extends Module
{
	public TeleportAccept()
	{
		super("TeleportAccept", 128, Category.OTHER);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Accept");
		modes.add("Deny");
		this.getSetManager().rSetting(new Setting(308, "Mode", "", this, "Deny", modes));		
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
	}
	
	@EventTarget
	public void onReceiveMessage(EventReceiveChatMessage event)
	{
		if (event.getComponent().getFormattedText().toLowerCase().contains("requested to teleport") || event.getComponent().getFormattedText().toLowerCase().contains("teletrasportarti"))
		{
			if (this.getMode().equals("Accept"))
			{
				mc.thePlayer.sendChatMessage("/tpaccept");
			}
			else
			{
				mc.thePlayer.sendChatMessage("/tpdeny");
			}
		}
	}
}