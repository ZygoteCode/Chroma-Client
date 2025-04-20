package net.minecraft.client.particle.chroma.module.other;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class FastBreak extends Module
{
	public FastBreak()
	{
		super("FastBreak", 69, Category.OTHER);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Normal");
		modes.add("Instant");
		this.getSetManager().rSetting(new Setting(207, "Mode", "", this, "Normal", modes));
		this.getSetManager().rSetting(new Setting(208, "Speed", "", this, 2.0, 1.0, 5.0, false));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
	}
}