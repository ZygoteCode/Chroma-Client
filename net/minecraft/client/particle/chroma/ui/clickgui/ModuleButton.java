package net.minecraft.client.particle.chroma.ui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.ui.Hud;

import org.lwjgl.input.Keyboard;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ModuleButton {
	public Module mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean extended = false;
	public boolean listening = false;

	/*
	 * Konstrukor
	 */
	public ModuleButton(Module imod, Panel pl) {
		mod = imod;
		height = 12;
		parent = pl;
		menuelements = new ArrayList<Element>();
		/*
		 * Settings wurden zuvor in eine ArrayList eingetragen
		 * dieses SettingSystem hat 3 Konstruktoren je nach
		 *  verwendetem Konstruktor ändert sich die Value
		 *  bei .isCheck() usw. so kann man ganz einfach ohne
		 *  irgendeinen Aufwand bestimmen welches Element
		 *  für ein Setting benötigt wird :>
		 */
		if (Minecraft.getMinecraft().getChroma().getSetManager().getSettingsByMod(imod) != null)
			for (Setting s : Minecraft.getMinecraft().getChroma().getSetManager().getSettingsByMod(imod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}

	}

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		
		/*
		 * Ist das Module an, wenn ja dann soll
		 *  #ein neues Rechteck in Größe des Buttons den Knopf als Toggled kennzeichnen
		 *  #sich der Text anders färben
		 */
		int textcolor = 0xffafafaf;
		if (mod.isToggled()) {
			//Gui.drawRect((int) x - 2, (int) y, (int) x + (int) width + 2, (int) y + (int) height + 1, color);
			textcolor = 0xffefefef;
		}
		
		/*
		 * Ist die Maus über dem Element, wenn ja dann soll der Button sich anders färben
		 */
		if (isHovered(mouseX, mouseY)) {
			//Gui.drawRect((int) x - 2, (int) y, (int) x + (int) width + 2, (int) y + (int) height + 1, 0x80000000);
		}
		
		/*
		 * Den Namen des Modules in die Mitte (x und y) rendern
		 */
		
		if (isHovered(mouseX, mouseY))
		{
			if (mod.isToggled())
			{
				FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, Color.CYAN.darker().darker().darker().getRGB());	
			}
			else
			{
				FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, Color.CYAN.darker().darker().getRGB());
			}
		}
		else
		{
			if (mod.isToggled())
			{
				FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, Color.LIGHT_GRAY.darker().getRGB());
			}
			else
			{
				FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, 0xFFFFFF);
			}
		}
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!isHovered(mouseX, mouseY))
			return false;

		/*
		 * Rechtsklick, wenn ja dann Module togglen, 
		 */
		if (mouseButton == 0) {
			mod.toggle();
			
			if(Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(0).getValBoolean())
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5f, 0.5f);
		} else if (mouseButton == 1) {
			/*
			 * Wenn ein Settingsmenu existiert dann sollen alle Settingsmenus 
			 * geschlossen werden und dieses geöffnet/geschlossen werden
			 */
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Minecraft.getMinecraft().getChroma().getClickGUI().closeAllSettings();
				this.extended = b;
				
				if(Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(0).getValBoolean())
				if(extended)Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1f, 1f);else Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1f, 1f);
			}
		} else if (mouseButton == 2) {
			/*
			 * MidClick => Set keybind (wait for next key)
			 */
			listening = true;
		}
		return true;
	}

	public boolean keyTyped(char typedChar, int keyCode) throws IOException {
		/*
		 * Wenn listening, dann soll der nächster Key (abgesehen 'ESCAPE') als Keybind für mod
		 * danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				mod.setKey(keyCode);
			} else {
				mod.setKey(Keyboard.KEY_NONE);
			}
			listening = false;
			return true;
		}
		return false;
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

}
