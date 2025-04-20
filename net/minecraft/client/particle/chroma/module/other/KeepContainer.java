package net.minecraft.client.particle.chroma.module.other;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventDisplayScreen;
import net.minecraft.client.particle.chroma.event.events.EventKeyboard;
import net.minecraft.client.particle.chroma.event.events.EventReceivePacket;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

public class KeepContainer extends Module
{
    private GuiContainer container;
    
	public KeepContainer()
	{
		super("KeepContainer", 122, Category.OTHER);
	}
	
    @Override
    public void onDisable()
    {
        if (container != null)
        {
        	mc.getNetHandler().addToSendQueue(new C0DPacketCloseWindow(container.inventorySlots.windowId));
        }

        container = null;
    }

    @EventTarget
    public void onGui(EventDisplayScreen event)
    {
        if (event.getGuiScreen() instanceof GuiContainer && !(event.getGuiScreen() instanceof GuiInventory))
        {
        	container = (GuiContainer) event.getGuiScreen();
        }
    }

    @EventTarget
    public void onKey(EventKeyboard event)
    {
        if (event.getKey() == Keyboard.KEY_INSERT)
        {
            if (container == null)
            {
            	 return;
            }
            
            mc.displayGuiScreen(container);
        }     
    }
    
    @EventTarget
    public void onSentPacket(EventSentPacket event)
    {
    	if (event.getPacket() instanceof C0DPacketCloseWindow)
    	{
    		event.cancel();
    	}
    }
    
    @EventTarget
    public void onReceivePacket(EventReceivePacket event)
    {
    	if (event.getPacket() instanceof S2EPacketCloseWindow)
    	{
            final S2EPacketCloseWindow packetCloseWindow = (S2EPacketCloseWindow) event.getPacket();

            if (container != null && container.inventorySlots != null && packetCloseWindow.windowId == container.inventorySlots.windowId)
            {
            	container = null;
            }
    	}
    }
}