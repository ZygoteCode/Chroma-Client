package net.minecraft.client.particle.chroma.utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class MiscellaneousUtil {

    public static void addChatMessage(String str) {
        Object chat = new ChatComponentText(str);

        if (str != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent) chat);
        }
    }

    public static double random(double min, double max) {
        Random random = new Random();
        return min + (random.nextDouble() * (max - min));
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873D;

        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
        }

        return baseSpeed;
    }
}