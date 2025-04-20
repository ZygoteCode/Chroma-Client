package net.minecraft.client.particle.chroma.module.player;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.inventory.Slot;

public class ChestStealer extends Module
{
	private Timer timer;
	
	public ChestStealer()
	{
		super("ChestStealer", 31, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		timer = new Timer();
		this.getSetManager().rSetting(new Setting(107, "Delay", "", this, 128, 1, 1000, true));
		this.getSetManager().rSetting(new Setting(108, "No detect", "", this, false));
		this.getSetManager().rSetting(new Setting(204, "Close chest", "", this, false));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		timer.reset();
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate e)
	{
		if (mc.currentScreen instanceof GuiChest && timer.hasReach(this.getSetManager().getSettingById(107).getValueD() + (this.getSetManager().getSettingById(108).getValBoolean() ? ThreadLocalRandom.current().nextDouble(125, 185) : 0.0)))
		{
			GuiChest chest = (GuiChest) mc.currentScreen;
			
			try
			{
				for (int i = 0; i < chest.inventoryRows * 9; i++)
				{
					Slot slot = (Slot) chest.inventorySlots.inventorySlots.get(i + (this.getSetManager().getSettingById(108).getValBoolean() ? ThreadLocalRandom.current().nextInt(10) : 0));
					
					if (slot.getStack() != null)
					{
						chest.handleMouseClick(slot, slot.slotNumber, 0, 1);
						chest.handleMouseClick(slot, slot.slotNumber, 0, 6);
						timer.reset();
						return;
					}
				}
			} 
			catch(Exception ex)
			{
				
			}
			
			if (this.getSetManager().getSettingById(204).getValBoolean())
			{
				Robot r = null;
				
				try
				{
					r = new Robot();
				}
				catch (AWTException e1)
				{
					
				}
				
				r.keyPress(27);
				r.keyRelease(27);
			}
		}
	}
}