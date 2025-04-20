package net.minecraft.client.particle.chroma.ui.clickgui;

import java.awt.Color;

import net.minecraft.client.Minecraft;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int) Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(1).getValueD(), (int) Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(2).getValueD(), (int) Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(3).getValueD());
	}
}
