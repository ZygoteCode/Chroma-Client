package net.minecraft.client.particle.chroma.module.gui;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.ui.clickgui.ClickGUI;

public class ClickGui extends Module
{
	public ClickGui()
	{
		super("ClickGui", "Allows to toggle all modules rapidly and setup settings in some clicks.", Keyboard.KEY_RSHIFT, Category.GUI, false, 4);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(0, "Sound", "Sound of the ClickGUI.", this, true));
		this.getSetManager().rSetting(new Setting(1, "ClickGUI red", "The red color of the ClickGUI.", this, Color.CYAN.darker().darker().getRed(), 0, 255, true));
		this.getSetManager().rSetting(new Setting(2, "ClickGUI green", "The green color of the ClickGUI.", this, Color.CYAN.darker().darker().getGreen(), 0, 255, true));
		this.getSetManager().rSetting(new Setting(3, "ClickGUI blue", "The blue color of the ClickGUI.", this, Color.CYAN.darker().darker().getBlue(), 0, 255, true));
		this.getSetManager().rSetting(new Setting(5, "Bold arraylist", "Arraylist bold.", this, false));
		this.getSetManager().rSetting(new Setting(6, "Bold logo", "Logo bold.", this, false));
		this.getSetManager().rSetting(new Setting(8, "Rainbow arraylist", "Arraylist bold.", this, false));
		this.getSetManager().rSetting(new Setting(9, "Rainbow logo", "Arraylist bold.", this, false));
		this.getSetManager().rSetting(new Setting(11, "Rainbow TabGui", "Arraylist bold.", this, false));
		this.getSetManager().rSetting(new Setting(12, "Colorful arraylist", "Colorful arraylist.", this, false));
		this.getSetManager().rSetting(new Setting(13, "Bold suffixes", "Bold suffixes.", this, false));
		this.getSetManager().rSetting(new Setting(266, "Watermark", "", this, false));
		this.getSetManager().rSetting(new Setting(311, "Little rect", "", this, true));
		this.getSetManager().rSetting(new Setting(312, "Lateral rect", "", this, true));
		this.getSetManager().rSetting(new Setting(350, "Entire rect", "", this, true));
		super.setup();
	}
	
	@Override
	public void onEnable()
	{
		mc.displayGuiScreen(mc.getChroma().getClickGUI());
		this.setToggled(false);
		super.onEnable();
	}
}