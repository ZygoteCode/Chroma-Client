package net.minecraft.client.particle.chroma.module.other;

import java.util.List;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventReceiveChatMessage;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.MathUtils;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

public class AntiSpam extends Module
{
	public AntiSpam()
	{
		super("AntiSpam", 99, Category.OTHER);
	}
	
	@EventTarget
	public void onReceiveMessage(EventReceiveChatMessage event)
	{
        List<ChatLine> chatLines = mc.ingameGUI.getChatGUI().getChatLines();
        
        if (chatLines.isEmpty())
        {
            return;
        }
        
        GuiNewChat chat = mc.ingameGUI.getChatGUI();
        int maxTextLength = MathUtils.floor((float)chat.getChatWidth() / chat.getChatScale());
        List<IChatComponent> newLines = GuiUtilRenderComponents.func_178908_a(event.getComponent(), maxTextLength, mc.fontRendererObj, false, false);
        int spamCounter = 1;
        int matchingLines = 0;
        
        for (int i = chatLines.size() - 1; i >= 0; --i)
        {
            String oldLine = chatLines.get(i).getChatComponent().getUnformattedText();
            
            if (matchingLines <= newLines.size() - 1)
            {
                String addedText;
                String oldSpamCounter;
                String twoLines;
                String newLine = newLines.get(matchingLines).getUnformattedText();
                
                if (matchingLines < newLines.size() - 1)
                {
                    if (oldLine.equals(newLine))
                    {
                        ++matchingLines;
                        continue;
                    }
                    
                    matchingLines = 0;
                    continue;
                }
                
                if (!oldLine.startsWith(newLine))
                {
                    matchingLines = 0;
                    continue;
                }
                
                if (i > 0 && matchingLines == newLines.size() - 1 && (addedText = (twoLines = String.valueOf(oldLine) + chatLines.get(i - 1).getChatComponent().getUnformattedText()).substring(newLine.length())).startsWith(" [x") && addedText.endsWith("]") && MathUtils.isInteger(oldSpamCounter = addedText.substring(3, addedText.length() - 1)))
                {
                    spamCounter += Integer.parseInt(oldSpamCounter);
                    ++matchingLines;
                    continue;
                }
                
                if (oldLine.length() == newLine.length())
                {
                    ++spamCounter;
                }
                else
                {
                    String addedText2 = oldLine.substring(newLine.length());
                    
                    if (!addedText2.startsWith(" [x") || !addedText2.endsWith("]"))
                    {
                        matchingLines = 0;
                        continue;
                    }
                    
                    String oldSpamCounter2 = addedText2.substring(3, addedText2.length() - 1);
                    
                    if (!MathUtils.isInteger(oldSpamCounter2))
                    {
                        matchingLines = 0;
                        continue;
                    }
                    
                    spamCounter += Integer.parseInt(oldSpamCounter2);
                }
            }
            
            for (int i2 = i + matchingLines; i2 >= i; --i2)
            {
                chatLines.remove(i2);
            }
            
            matchingLines = 0;
        }
        
        if (spamCounter > 1)
        {   	
            event.getComponent().appendText(" [x" + spamCounter + "]");  
        }
	}
}