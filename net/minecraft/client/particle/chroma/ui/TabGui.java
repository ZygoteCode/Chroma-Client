package net.minecraft.client.particle.chroma.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.CustomFontRenderer;
import net.minecraft.client.particle.chroma.utils.Wrapper;

public class TabGui
{
	private static int selected, moduleSelected;
	private static boolean isOpen;
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static CustomFontRenderer displayer = new CustomFontRenderer(new Font("Segoe UI", 0, 30), true);
	
	public static void drawTabGui()
	{
		for (Category cat: Category.values())
		{
			if (cat == Category.NONE || cat == Category.GUI)
			{
				continue;
			}
			
			boolean lol = false;
			
			if (mc.getChroma().getModuleManager().getModuleByID(38).isToggled())
			{
				lol = mc.getChroma().getSetManager().getSettingById(123).getValBoolean();
			}
			
			int y = mc.getChroma().getSetManager().getSettingById(266).getValBoolean() ? 36 : 20;
			int i = cat.ordinal();		
			int color = Color.CYAN.darker().darker().getRGB();
			
			if (mc.getChroma().getSetManager().getSettingById(11).getValBoolean())
			{
				color = Wrapper.getRainbow(3000, -15 * 3);
			}
			
			Gui.drawRect(2, y + i * 10, (Chroma.getFirstCategoryLength() * 8) - 3, y + 10 + i * 10, selected == i ? color : 0xcc000000);	
			String name = cat.name().substring(0, 1) + cat.name().substring(1).toLowerCase();	
			
			if (!lol)
			{
				displayer.drawStringWithShadow(name, 4, y + i * 10 + 2, selected == i ? Color.LIGHT_GRAY.getRGB() : 0xffffffff);
			}
			else
			{
				mc.fontRendererObj.drawStringWithShadow(name, 4, y + i * 10 + 2, selected == i ? Color.LIGHT_GRAY.getRGB() : 0xffffffff);
			}
			
			if (isOpen && i == selected)
			{
				ArrayList<Module> modules = mc.getChroma().getModuleManager().sortModules(mc.getChroma().getModuleManager().getModulesForCategory(cat), false, false, displayer);
				
				for (int j = 0; j < modules.size(); j++)
				{
					Module m = modules.get(j);				
					Gui.drawRect(((Chroma.getFirstCategoryLength() * 8) - 3) + 2, y + i * 10 + j * 10, (Chroma.getFirstModuleLength() * 10), y + i * 10 + j * 10 + 10, moduleSelected == j ? color : 0xcc000000);
					
					if (!lol)
					{
						displayer.drawStringWithShadow(m.getName(), ((Chroma.getFirstCategoryLength() * 8) - 3) + 4, y + i * 10 + j * 10 + 2, m.isToggled() ? Color.LIGHT_GRAY.getRGB() : 0xffffffff);
					}
					else
					{
						mc.fontRendererObj.drawStringWithShadow(m.getName(), ((Chroma.getFirstCategoryLength() * 8) - 3) + 4, y + i * 10 + j * 10 + 2, m.isToggled() ? Color.LIGHT_GRAY.getRGB() : 0xffffffff);
					}
				}
			}
		}
	}
	
	public static void onKey(int key)
	{
		ArrayList<Module> modules = mc.getChroma().getModuleManager().sortModules(mc.getChroma().getModuleManager().getModulesForCategory(Category.values()[selected]), false, false, displayer);
		int length = Category.values().length;
		int olength = modules.size();
		
		if (key == Keyboard.KEY_UP)
		{
			if (!isOpen)
			{
				selected--;
				
				if (selected <= -1)
				{
					selected = length - 3;
				}
			}
			else
			{
				moduleSelected--;
				
				if (moduleSelected < 0)
				{
					moduleSelected = olength - 1;
				}
			}
		}
		else if (key == Keyboard.KEY_DOWN)
		{		
			if (!isOpen)
			{
				selected++;
				
				if (selected > length - 3)
				{
					selected = 0;
				}
			}
			else
			{
				moduleSelected++;
				
				if (moduleSelected >= olength)
				{
					moduleSelected = 0;
				}
			}
		}
		else if (key == Keyboard.KEY_LEFT)
		{
			if (!modules.isEmpty())
			{
				isOpen = false;
				moduleSelected = 0;
			}
		}
		else if (key == Keyboard.KEY_RIGHT)
		{
			if (!modules.isEmpty())
			{
				if (!isOpen)
				{
					isOpen = true;	
				}
				else
				{
					modules.get(moduleSelected).toggle();
				}
			}
		}
		else if (key == Keyboard.KEY_RETURN)
		{
			if (isOpen)
			{
				modules.get(moduleSelected).toggle();
			}
		}
	}
}