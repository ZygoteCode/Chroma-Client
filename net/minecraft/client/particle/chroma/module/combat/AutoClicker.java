package net.minecraft.client.particle.chroma.module.combat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.Timer;

public class AutoClicker extends Module
{
	private int tick;
	
	public AutoClicker()
	{
		super("AutoClicker", 78, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Computer");
		modes.add("Client");
		ArrayList<String> types = new ArrayList<String>();
		types.add("Left");
		types.add("Middle");
		types.add("Right");
		this.getSetManager().rSetting(new Setting(252, "Mode", "", this, "Computer", modes));
		this.getSetManager().rSetting(new Setting(253, "Type", "", this, "Left", types));
		this.getSetManager().rSetting(new Setting(256, "CPS", "", this, 16, 1, 20, true));
		this.getSetManager().rSetting(new Setting(257, "Random", "", this, false));
		this.getSetManager().rSetting(new Setting(258, "Min CPS", "", this, 9, 1, 20, true));
		this.getSetManager().rSetting(new Setting(259, "Max CPS", "", this, 16, 1, 20, true));
		this.getSetManager().rSetting(new Setting(254, "Jitter", "", this, false));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		tick = 0;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		String mode = this.getMode();
		String type = this.getSetManager().getSettingById(253).getValString();
		boolean jitter = this.getSetManager().getSettingById(254).getValBoolean();
		int cps = this.getSetManager().getSettingById(256).getValueI();
		boolean random = this.getSetManager().getSettingById(257).getValBoolean();
		int minCPS = this.getSetManager().getSettingById(258).getValueI();
		int maxCPS = this.getSetManager().getSettingById(259).getValueI();
		
		this.setSuffix(" §7" + mode + " | " + type);
		
		if (random)
		{
			cps = ThreadLocalRandom.current().nextInt(minCPS, maxCPS);
		}
		
		if (tick >= (20 - cps))
		{
			int times = 1;
			
			if (jitter)
			{
				times = 2;
			}
			
			for (int i = 0; i < times; i++)
			{
				if (mode == "Client")
				{
					if (type == "Left")
					{
						mc.clickMouse();
					}
					else if (type == "Right")
					{
						mc.rightClickMouse();
					}
					else
					{
						mc.middleClickMouse();
					}
				}
				else
				{
					Robot r = null;
					
					try
					{
						r = new Robot();
					}
					catch (AWTException e)
					{
						
					}
					
					if (type == "Left")
					{
						r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					}
					else if (type == "Right")
					{
						r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
						r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
					}
					else
					{
						r.mousePress(InputEvent.BUTTON2_DOWN_MASK);
						r.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
					}
				}
			}
			
			tick = 0;
		}
		else
		{
			tick++;
		}
	}
}