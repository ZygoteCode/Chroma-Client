package net.minecraft.client.particle.chroma.ui.clickgui;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.util.MathHelper;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ElementSlider extends Element {
	public boolean dragging;

	/*
	 * Konstrukor
	 */
	public ElementSlider(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		dragging = false;
		super.setup();
	}

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (set.isVisible())
		{
			String displayval = "" + Math.round(set.getValueD() * 100D)/ 100D;
			boolean hoveredORdragged = isSliderHovered(mouseX, mouseY) || dragging;
			
			Color temp = ColorUtil.getClickGUIColor();
			int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200).getRGB();
			int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230).getRGB();
			
			//selected = iset.getValDouble() / iset.getMax();
			double percentBar = (set.getValueD() - set.getMinD())/(set.getMaxD() - set.getMinD());
			
			/*
			 * Die Box und Umrandung rendern
			 */
			Gui.drawRect((int) x, (int) y, (int) x + (int) width, (int) y + (int) height, 0xff1a1a1a);

			/*
			 * Den Text rendern
			 */
			FontUtil.drawString(setstrg, x + 1, y + 2, 0xffffffff);
			FontUtil.drawString(displayval, x + width - FontUtil.getStringWidth(displayval), y + 2, 0xffffffff);

			/*
			 * Den Slider rendern
			 */
			Gui.drawRect((int) x, (int) y + 12, (int) x + (int) width, (int) y + (int) 13.5, 0xff101010);
			Gui.drawRect((int) x, (int) y + 12, (int) x + (int) (percentBar * width), (int) y + (int) 13.5, color);
			
			if(percentBar > 0 && percentBar < 1)
			Gui.drawRect((int) x + (int) (percentBar * width)-1, (int) y + 12, (int) x + (int) Math.min((percentBar * width), (int) width), (int) y + (int) 13.5, color2);
			

			/*
			 * Neue Value berechnen, wenn dragging
			 */
			if (this.dragging) {
				double diff = set.getMaxD() - set.getMinD();
				double val = set.getMinD() + (MathHelper.clamp_double((mouseX - x) / width, 0, 1)) * diff;
				set.setValueD(val); //Die Value im Setting updaten
			}
		}
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isSliderHovered(mouseX, mouseY)) {
			this.dragging = true;
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Wenn die Maus losgelassen wird soll aufgehört werden die Slidervalue zu verändern
	 */
	public void mouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
	}

	/*
	 * Einfacher HoverCheck, benötigt damit dragging auf true gesetzt werden kann
	 */
	public boolean isSliderHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y + 11 && mouseY <= y + 14;
	}
}