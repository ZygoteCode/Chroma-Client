package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;

public final class WChat {
    public static void clearMessages() {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages();
    }
}