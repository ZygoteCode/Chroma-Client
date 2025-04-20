package net.minecraft.client.particle.chroma.module.world;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSendChatMessage;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class FancyChat extends Module
{
	public FancyChat()
	{
		super("FancyChat", 100, Category.WORLD);
	}
	
	@EventTarget
	public void onSendMessage(EventSendChatMessage event)
	{
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith("."))
        {
            return;
        }
        
        String out = "";
        
        for (char c : event.getMessage().toCharArray())
        {
            out = c >= '!' && c <= '' && !"(){}[]|".contains(Character.toString(c)) ? String.valueOf(out) + new String(Character.toChars(c + 65248)) : String.valueOf(out) + c;
        }
        
        event.setMessage(out);
	}
}